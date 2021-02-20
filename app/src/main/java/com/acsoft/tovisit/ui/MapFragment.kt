package com.acsoft.tovisit.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import com.acsoft.tovisit.R
import com.acsoft.tovisit.databinding.FragmentMapBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_map.*


class MapFragment : Fragment(), OnMapReadyCallback {

    private lateinit var binding : FragmentMapBinding
    private lateinit var googleMap:GoogleMap

    private val args by navArgs<MapFragmentArgs>()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        map_view.onCreate(savedInstanceState)
        map_view.onResume()

        map_view.getMapAsync(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMapBinding.bind(view)

        binding.textView.setOnClickListener {
            NavHostFragment.findNavController(this).navigateUp()
        }

    }

    override fun onMapReady(map: GoogleMap?) {
        map?.let {
            val location = LatLng(args.latitude.toDouble(),args.latitude.toDouble())
            Log.d("TAG",args.latitude)
            Log.d("TAG",args.longitude)
            googleMap = it
            googleMap.addMarker(
                MarkerOptions()
                    .position(location)
                    .title(args.streetName)
            )

            map.moveCamera(CameraUpdateFactory.newLatLng(location))
        }
    }


}