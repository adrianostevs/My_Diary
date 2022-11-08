package com.learn.mydiary.ui.maps

import android.Manifest
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.learn.mydiary.R
import com.learn.mydiary.base.BaseActivity
import com.learn.mydiary.data.remote.model.request.StoryRequest
import com.learn.mydiary.databinding.ActivityMapsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import java.io.IOException
import java.util.*

@AndroidEntryPoint
class MapsActivity : BaseActivity <ActivityMapsBinding>(), OnMapReadyCallback{

    private lateinit var map: GoogleMap

    private val boundsBuilder = LatLngBounds.Builder()

    private val mViewModel : MapsViewModel by viewModels()

    override fun onViewBinding() =  ActivityMapsBinding.inflate(layoutInflater)

    override fun onCreated(savedInstanceState: Bundle?) {
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.maps) as SupportMapFragment
        mapFragment.getMapAsync(this)

        mViewModel.getAllLocation(StoryRequest(location = 1, storySize = 30))
        observeData()

    }

    private fun observeData() {
        lifecycleScope.launchWhenStarted {
            mViewModel.mapsEvent.collectLatest {
                when (it) {
                    is MapsEvent.GetLocationLoading -> {}
                    is MapsEvent.GetLocationFailed -> {
                        Toast.makeText(this@MapsActivity,it.message, Toast.LENGTH_SHORT).show()
                    }
                    is MapsEvent.GetLocationSuccess -> {
                        it.data?.listStory?.forEach{ data ->
                            val latLng = LatLng(data.lat!!, data.lon!!)
                            val name = getName(data.lat, data.lon)
                            map.addMarker(MarkerOptions().position(latLng).title(data.description). snippet(name))
                            boundsBuilder.include(latLng)
                        }
                        val bounds: LatLngBounds = boundsBuilder.build()
                        map.animateCamera(
                            CameraUpdateFactory.newLatLngBounds(
                                bounds,
                                resources.displayMetrics.widthPixels,
                                resources.displayMetrics.heightPixels,
                                300
                            )
                        )
                    }
                }
            }
        }
    }

    private fun getName(lat: Double, lon: Double): String? {
        var name: String? = null
        val geocoder = Geocoder(this, Locale.getDefault())
        try {
            val list = geocoder.getFromLocation(lat, lon, 1)
            if (list != null && list.size != 0) {
                name = list[0].getAddressLine(0)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return name
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        map.uiSettings.isZoomControlsEnabled = true
        map.uiSettings.isIndoorLevelPickerEnabled = true
        map.uiSettings.isCompassEnabled = true
        map.uiSettings.isMapToolbarEnabled = true

        observeData()
        getMyLocation()
    }

    private fun getMyLocation() {
        if (ContextCompat.checkSelfPermission(
                this.applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            map.isMyLocationEnabled = true
        } else {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                getMyLocation()
            }
        }
}