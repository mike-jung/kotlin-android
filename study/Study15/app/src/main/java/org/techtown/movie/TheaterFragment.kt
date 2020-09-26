package org.techtown.movie

import android.content.Context
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_movie_list.view.*
import kotlinx.android.synthetic.main.fragment_theater.*
import org.techtown.movie.data.*
import java.util.*
import kotlin.collections.HashMap

class TheaterFragment : Fragment() {
    val TAG = "TheaterFragment"

    var locationClient: FusedLocationProviderClient? = null
    var locationCallback: LocationCallback? = null

    var initialized = false
    lateinit var map: GoogleMap


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_theater, container, false)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync {
            initialized = true
            map = it

            try {
                map.isMyLocationEnabled = true
                map.uiSettings.isZoomControlsEnabled = true
                map.uiSettings.isZoomControlsEnabled = true
                requestLocation()
            } catch(e: SecurityException) {}
        }


        return rootView
    }

    private fun requestLocation() {
        locationClient = LocationServices.getFusedLocationProviderClient(context!!)

        try {
            locationClient?.lastLocation
                ?.addOnSuccessListener { location ->
                    if (location == null) {
                        println("최근 위치 확인 실패")
                    } else {
                        println("최근 위치 : ${location.latitude}, ${location.longitude}")

                        showCurrentLocation(location)
                    }
                }
                ?.addOnFailureListener {
                    println("최근 위치 확인 시 에러 : ${it.message}")
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
                            println("내 위치 #$i : ${location.latitude} , ${location.longitude}")
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


        map.clear()

        showMarker(curPoint)
        requestTheater(location)
    }

    fun showMarker(curPoint: LatLng) {
        val myMarker = MarkerOptions()
        myMarker.position(curPoint)
        myMarker.title("● 내 위치\n")
        myMarker.snippet("● GPS로 확인한 위치")
        myMarker.icon(BitmapDescriptorFactory.fromResource(R.drawable.location1))
        map.addMarker(myMarker)
    }


    /**
     * Kakao API로 영화관 정보 요청
     */
    fun requestTheater(location:Location) {
        val apiKey = "3be9efc5f298b6ecb4ee1f775980224c"
        val url = "https://dapi.kakao.com/v2/local/search/category.json?category_group_code=CT1&page=1&size=15&sort=accuracy&x=${location.longitude}&y=${location.latitude}&radius=2000"

        val request = object: StringRequest(
            Request.Method.GET,
            url,
            {
                println("\n응답 -> ${it}")
                processResponse(it)
            },
            {
                println("\n에러 -> ${it.message}")
            }
        ) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Authorization"] = "KakaoAK ${apiKey}"

                return headers
            }
        }

        request.setShouldCache(false)
        MainActivity.requestQueue?.add(request)
        println("\n영화관 요청함")
    }

    fun processResponse(response: String) {
        val gson = Gson()
        val theaterResponse = gson.fromJson(response, TheaterResponse::class.java)
        println("\n문화시설의 수: " + theaterResponse.documents.size)

        val theaterList = filterTheater(theaterResponse.documents)
        println("\n영화관의 수: " + theaterList.size)

        showTheaterMarker(theaterList)

    }

    fun filterTheater(documents:ArrayList<TheaterInfo>):ArrayList<TheaterInfo> {
        val theaterList = arrayListOf<TheaterInfo>()
        for (item in documents) {
            val index = item.category_name?.indexOf("영화관")
            if (index != null && index > 0) {
                // 영화관 찾음
                theaterList.add(item)
            }
        }

        return theaterList
    }

    fun showTheaterMarker(theaterList:ArrayList<TheaterInfo>) {

        for (item in theaterList) {
            val curPoint = LatLng(item.y?.toDouble()!!, item.x?.toDouble()!!)

            val myMarker = MarkerOptions()
            myMarker.position(curPoint)
            myMarker.title("● ${item.place_name}")
            myMarker.snippet("● ${item.road_address_name}")
            myMarker.icon(BitmapDescriptorFactory.fromResource(R.drawable.theater_64))
            map.addMarker(myMarker)
        }

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
            locationClient?.removeLocationUpdates(locationCallback)
            map?.isMyLocationEnabled = false
        } catch(e: SecurityException) {}
    }

    fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

}