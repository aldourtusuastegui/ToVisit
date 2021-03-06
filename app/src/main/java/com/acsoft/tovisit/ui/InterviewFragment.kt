package com.acsoft.tovisit.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.acsoft.tovisit.R
import com.acsoft.tovisit.data.local.AppDatabase
import com.acsoft.tovisit.data.local.LocalInterviewDataSource
import com.acsoft.tovisit.data.model.InterviewItemEntity
import com.acsoft.tovisit.data.remote.RemoteInterviewDataSource
import com.acsoft.tovisit.data.remote.RetrofitClient
import com.acsoft.tovisit.databinding.FragmentInterviewBinding
import com.acsoft.tovisit.presentation.InterviewModelFactory
import com.acsoft.tovisit.presentation.InterviewViewModel
import com.acsoft.tovisit.repository.InterviewRepositoryImpl
import com.acsoft.tovisit.ui.adapters.InterviewAdapter
import kotlinx.coroutines.launch

class InterviewFragment : Fragment(),InterviewAdapter.OnInterviewClickListener {

    private lateinit var binding : FragmentInterviewBinding

    private lateinit var adapter : InterviewAdapter

    private val viewModel by viewModels<InterviewViewModel> {
        InterviewModelFactory(InterviewRepositoryImpl(
                requireContext(),
                RemoteInterviewDataSource(RetrofitClient.webService),
                LocalInterviewDataSource(AppDatabase.getDatabase(requireContext()).interviewDao())
        ))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = InterviewAdapter(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_interview, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentInterviewBinding.bind(view)

        showVisitsToDo()
        showInterviews()
        searchInterview()
    }

    private fun showAdvice(visit: Int) {
        if (visit==0) {
            binding.tvVisitsToDo.visibility = View.GONE
            binding.rvInterview.visibility = View.GONE
            binding.cvAdvice.visibility = View.VISIBLE
        } else {
            binding.tvVisitsToDo.visibility = View.VISIBLE
            binding.rvInterview.visibility = View.VISIBLE
            binding.cvAdvice.visibility = View.GONE
        }

    }

    private fun showVisitsToDo() {
        viewModel.getVisitsToDo().observe(requireActivity(), { number ->
            showAdvice(number)
            binding.tvVisitsToDo.text = "Tienes ${number} visitas por hacer"
        })
    }

    private fun showInterviews() {
        binding.rvInterview.layoutManager = LinearLayoutManager(requireContext())
        binding.rvInterview.adapter = adapter

        if (viewModel.getUpdated()) {

            viewModel.getInterviewsListLocal().observe(requireActivity(), { list ->
                list.let {
                    if(list.isNotEmpty()) {
                        adapter.setInterviewList(list)
                    }
                }
            })

        } else {
            lifecycleScope.launch {
                viewModel.getInterviewsListApi().observe(requireActivity(),{ list->
                    list.let {
                        if(list.isNotEmpty()) {
                            adapter.setInterviewList(list)
                            viewModel.saveUpdated(true)
                        }
                    }
                })
            }
        }


    }

    private fun searchInterview() {
        binding.searchInterview.setOnQueryTextListener(object :  SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {

                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.searchInterview(newText!!).observe(viewLifecycleOwner, {
                    adapter.setInterviewList(it)
                })
                return false
            }
        })
    }

    override fun onInterviewClick(interview: InterviewItemEntity) {
        val action = InterviewFragmentDirections.actionInterviewFragmentToMapFragment(
            interview.streetName,
            interview.suburb,
            interview.visited,
            interview.location.latitude.toString(),
            interview.location.longitude.toString()
        )
        findNavController().navigate(action)
    }


}