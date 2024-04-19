package com.amora.googlemaps.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import com.amora.googlemaps.data.Place
import com.amora.googlemaps.databinding.LayoutInfoContentBinding
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker

class MapInfoAdapter(
    private val context: Context
): GoogleMap.InfoWindowAdapter {
    override fun getInfoContents(marker: Marker): View? {
        val binding = LayoutInfoContentBinding.inflate(LayoutInflater.from(context))
        val place = marker.tag as? Place ?: return null
        binding.apply {
            textViewAddress.text = place.address
            textViewTitle.text = place.name
        }
        return binding.root
    }

    override fun getInfoWindow(p0: Marker): View? {
        // Return null to indicate that the
        // default window (white bubble) should be used
        return null
    }
}