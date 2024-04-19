package com.amora.googlemaps

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.amora.googlemaps.adapter.MapInfoAdapter
import com.amora.googlemaps.data.Place
import com.amora.googlemaps.databinding.ActivityMainBinding
import com.amora.googlemaps.util.PlaceRenderer
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.Circle
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.ktx.awaitMap
import com.google.maps.android.ktx.awaitMapLoad
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var mainBinding: ActivityMainBinding

    private var circle: Circle? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        val mapFragment =
            supportFragmentManager.findFragmentById(R.id.customMap) as SupportMapFragment
        lifecycleScope.launch {
            val googleMap = mapFragment.awaitMap()
            googleMap.awaitMapLoad()
            googleMap.enabledToolbarMap(true)
            googleMap.addClusteredMarkers()
        }
    }

    private fun addCircle(googleMap: GoogleMap, item: Place) {
        circle?.remove()
        circle = googleMap.addCircle(
            CircleOptions()
                .center(item.latLng)
                .radius(500.0)
                .fillColor(ContextCompat.getColor(this, R.color.colorPrimaryTranslucent))
                .strokeColor(ContextCompat.getColor(this, R.color.red))
        )
    }


    private fun GoogleMap.enabledToolbarMap(toggle: Boolean) {
        uiSettings.apply {
            isMyLocationButtonEnabled = toggle
            isZoomControlsEnabled = toggle
            isMapToolbarEnabled = toggle
        }
    }

    /**
     * Adds markers to the map with clustering support.
     */
    private fun GoogleMap.addClusteredMarkers() {
        val places = listOf(
            Place(
                name = "Menara Batavia",
                latLng = LatLng(-6.2104075494499575, 106.81552859590758),
                address = "Jl. K.H. Mas Mansyur No.Kav. 126"
            ),
            Place(
                name = "Tower 88",
                latLng = LatLng(-6.2243361305945575, 106.84186764253121),
                address = "Jalan Casablanca Raya Kav.88"
            )
        )
        // Create the ClusterManager class and set the custom renderer
        val clusterManager = ClusterManager<Place>(this@MainActivity, this)
        clusterManager.renderer =
            PlaceRenderer(
                this@MainActivity,
                this,
                clusterManager
            )

        // Set custom info window adapter
        clusterManager.markerCollection.setInfoWindowAdapter(MapInfoAdapter(this@MainActivity))

        // Add the places to the ClusterManager
        clusterManager.addItems(places)
        clusterManager.cluster()

        // Show polygon
        clusterManager.setOnClusterItemClickListener { item ->
            addCircle(this, item)
            return@setOnClusterItemClickListener false
        }

        // When the camera starts moving, change the alpha value of the marker to translucent
        setOnCameraMoveStartedListener {
            clusterManager.markerCollection.markers.forEach { it.alpha = 0.3f }
            clusterManager.clusterMarkerCollection.markers.forEach { it.alpha = 0.3f }
        }

        setOnCameraIdleListener {
            // When the camera stops moving, change the alpha value back to opaque
            clusterManager.markerCollection.markers.forEach { it.alpha = 1.0f }
            clusterManager.clusterMarkerCollection.markers.forEach { it.alpha = 1.0f }

            // Call clusterManager.onCameraIdle() when the camera stops moving so that re-clustering
            // can be performed when the camera stops moving
            clusterManager.onCameraIdle()
        }

        initialCameraUpdate(places)
    }


    private fun GoogleMap.initialCameraUpdate(places: List<Place>) {
        val boundsBuilder = LatLngBounds.builder()
        for (marker in places) {
            boundsBuilder.include(marker.latLng)
        }
        val latLngBounds = boundsBuilder.build()
        val padding = 300
        val cameraUpdate = CameraUpdateFactory.newLatLngBounds(
            latLngBounds, padding
        )
        moveCamera(cameraUpdate)
    }
}