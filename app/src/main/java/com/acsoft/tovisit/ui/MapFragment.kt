package com.acsoft.tovisit.ui

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import com.acsoft.tovisit.R
import com.acsoft.tovisit.data.local.AppDatabase
import com.acsoft.tovisit.data.local.LocalInterviewDataSource
import com.acsoft.tovisit.data.remote.RemoteInterviewDataSource
import com.acsoft.tovisit.data.remote.RetrofitClient
import com.acsoft.tovisit.databinding.FragmentMapBinding
import com.acsoft.tovisit.presentation.InterviewModelFactory
import com.acsoft.tovisit.presentation.InterviewViewModel
import com.acsoft.tovisit.repository.InterviewRepositoryImpl
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.*
import kotlinx.android.synthetic.main.fragment_map.*


class MapFragment : Fragment(), OnMapReadyCallback {

    private lateinit var binding : FragmentMapBinding
    private lateinit var googleMap:GoogleMap
    private lateinit var marker: Marker
    private val args by navArgs<MapFragmentArgs>()

    private val viewModel by viewModels<InterviewViewModel> {
        InterviewModelFactory(
            InterviewRepositoryImpl(
            requireContext(),
            RemoteInterviewDataSource(RetrofitClient.webService),
            LocalInterviewDataSource(AppDatabase.getDatabase(requireContext()).interviewDao())
        )
        )
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        map_view.onCreate(savedInstanceState)
        map_view.onResume()

        map_view.getMapAsync(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMapBinding.bind(view)

        binding.ivBack.setOnClickListener {
            NavHostFragment.findNavController(this).navigateUp()
        }

        binding.btnDoVisit.setOnClickListener {
           viewModel.updateAccount(args.streetName,true)
           marker.setIcon(bitmapDescriptorFromVector(requireContext(),R.drawable.ic_visited_marker))

           binding.ivVisited.backgroundTintList = ContextCompat
                .getColorStateList(requireContext(),R.color.visited_color)

           binding.tvVisited.setTextColor(ContextCompat.getColor(requireContext(),R.color.visited_color))

           binding.tvVisited.text = context?.getString(R.string.visited)

        }

        showInterviewData()

    }

    private fun showInterviewData() {

        binding.ivVisited.backgroundTintList = ContextCompat
            .getColorStateList(requireContext(), if(args.visited) R.color.visited_color else R.color.pending_color)

        binding.tvVisited.setTextColor(ContextCompat.getColor(requireContext(),
            if(args.visited) R.color.visited_color else R.color.pending_color))

        binding.tvVisited.text = if (args.visited) context?.getString(R.string.visited) else context?.getString(R.string.pending)

        binding.tvStreetName.text = args.streetName
        binding.tvSuburb.text = args.suburb
    }


    override fun onMapReady(map: GoogleMap?) {
        map?.let {
            googleMap = map
            val myLocation = LatLng(args.latitude.toDouble(), args.longitude.toDouble())
            marker = googleMap.addMarker(
                MarkerOptions()
                    .position(myLocation)
                    .title(args.streetName)
                    .icon(bitmapDescriptorFromVector(requireContext(),if (args.visited) R.drawable.ic_visited_marker else R.drawable.ic_marker))
            )
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 17.0f))
            googleMap.uiSettings.isZoomControlsEnabled = false
        }
    }


    private fun bitmapDescriptorFromVector(
        context: Context,
        @DrawableRes vectorResId: Int
    ): BitmapDescriptor? {
        val vectorDrawable = ContextCompat.getDrawable(context, vectorResId)
        vectorDrawable!!.setBounds(
            0,
            0,
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight
        )
        val bitmap = Bitmap.createBitmap(
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        vectorDrawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }
}