package org.techtown.location

import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import com.pedro.library.AutoPermissions
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var locationClient:FusedLocationProviderClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startButton.setOnClickListener {
            requestLocation()
        }

        AutoPermissions.Companion.loadAllPermissions(this, 101);
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
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

            val locationCallback = object: LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult?) {
                    locationResult?.let {
                        for((i, location) in it.locations.withIndex()) {
                            output1.setText("내 위치 #$i : ${location.latitude} , ${location.longitude}")
                        }
                    }
                }
            }
            locationClient?.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper())

        } catch(e: SecurityException) {
            e.printStackTrace()
        }

    }

}