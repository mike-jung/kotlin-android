package org.techtown.map

import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.yanzhenjie.permission.AndPermission
import com.yanzhenjie.permission.runtime.Permission
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var locationClient:FusedLocationProviderClient? = null
    var locationCallback:LocationCallback? = null

    var initialized = false
    lateinit var map: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync {
            initialized = true
            map = it

            try {
                map?.isMyLocationEnabled = true
            } catch(e: SecurityException) {}
        }

        startButton.setOnClickListener {
            requestLocation()
        }


        AndPermission.with(this)
            .runtime()
            .permission(Permission.Group.LOCATION)
            .onGranted { permissions ->
                Log.d("Main", "허용된 권한 갯수 : ${permissions.size}")
            }
            .onDenied { permissions ->
                Log.d("Main", "거부된 권한 갯수 : ${permissions.size}")
            }
            .start()
    }

    private fun requestLocation() {
        locationClient = LocationServices.getFusedLocationProviderClient(this)

        try {
            locationClient?.lastLocation
                ?.addOnSuccessListener { location ->
                    if (location == null) {
                        output1.setText("최근 위치 확인 실패")
                    } else {
                        output1.setText("최근 위치 : ${location.latitude}, ${location.longitude}")

                        showCurrentLocation(location)
                    }
                }
                ?.addOnFailureListener {
                    output1.setText("최근 위치 확인 시 에러 : ${it.message}")
                    it.printStackTrace()
                }

            val locationRequest = LocationRequest.create()
            locationRequest.run {
                priority = LocationRequest.PRIORITY_HIGH_ACCURACY
                interval = 60 * 1000
            }

            locationCallback = object: LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult?) {
                    locationResult?.let {
                        for((i, location) in it.locations.withIndex()) {
                            output1.setText("내 위치 #$i : ${location.latitude} , ${location.longitude}")
                        }

                        showCurrentLocation(it.locations[0])
                    }
                }
            }
            locationClient?.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper())

        } catch(e: SecurityException) {
            e.printStackTrace()
        }

    }

    fun showCurrentLocation(location: Location) {
        val curPoint = LatLng(location.getLatitude(), location.getLongitude())
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(curPoint, 15.0f))

        showMarker(curPoint)
    }

    fun showMarker(curPoint: LatLng) {
        val myMarker = MarkerOptions()
        myMarker.position(curPoint)
        myMarker.title("● 내 위치\n")
        myMarker.snippet("● GPS로 확인한 위치")
        myMarker.icon(BitmapDescriptorFactory.fromResource(R.drawable.location1))
        map.addMarker(myMarker)
    }

    override fun onResume() {
        super.onResume()

        try {
            if (initialized) {
                map?.isMyLocationEnabled = true
            }
        } catch(e: SecurityException) {}
    }

    override fun onPause() {
        super.onPause()

        try {
            if (initialized) {
                locationClient?.removeLocationUpdates(locationCallback)
                map?.isMyLocationEnabled = false
            }
        } catch(e: SecurityException) {}
    }

}