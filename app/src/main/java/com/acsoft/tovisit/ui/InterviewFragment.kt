package com.acsoft.tovisit.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
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

        showInterviews()
    }

    private fun showInterviews() {
        binding.rvInterview.layoutManager = LinearLayoutManager(requireContext())
        binding.rvInterview.adapter = adapter

        lifecycleScope.launch {
            viewModel.getlist().observe(requireActivity(),{ list->
                list.let {
                    if(list.isNotEmpty()) {
                        adapter.setInterviewList(list)
                    } else {
                        Log.d("TAG","vacio")
                    }

                }
            })
        }
    }

    override fun onInterviewClick(account: InterviewItemEntity) {
        TODO("Not yet implemented")
    }
}