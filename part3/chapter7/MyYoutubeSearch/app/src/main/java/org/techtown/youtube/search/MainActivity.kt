package org.techtown.youtube.search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import org.techtown.movie.data.SearchListResponse
import java.net.URLEncoder

class MainActivity : AppCompatActivity() {

    companion object {
        var requestQueue: RequestQueue? = null
    }

    var adapter:MovieVideoAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        requestQueue =  Volley.newRequestQueue(applicationContext)


        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = layoutManager

        adapter = MovieVideoAdapter()
        recyclerView.adapter = adapter

        adapter?.listener = object : OnMovieVideoClickListener {
            override fun onItemClick(holder: MovieVideoAdapter.ViewHolder?, view: View?, position: Int) {
                val name = adapter?.items?.get(position)
                showToast("아이템 선택됨 : $name")
            }
        }


        searchButton.setOnClickListener {
            requestSearch()
        }

    }

    fun requestSearch() {
        val input = input1.text.toString()
        val queryString = URLEncoder.encode(input)

        val apiKey = "AIzaSyDsfwl9qBPQOtf9nHzEK7pKNn5Z8AWLw08"
        val url = "https://www.googleapis.com/youtube/v3/search?key=${apiKey}&q=${queryString}&type=video&regionCode=KR&videoDuration=short&part=snippet"

        val request = StringRequest(
            Request.Method.GET,
            url,
            {
                println("\n응답 -> ${it}")

                processResponse(it)
            },
            {
                println("\n에러 -> ${it.message}")
            }
        )

        request.setShouldCache(false)
        requestQueue?.add(request)
        println("\n유투브 검색 요청함")
    }

    fun processResponse(response:String) {
        val gson = Gson()
        val searchList = gson.fromJson(response, SearchListResponse::class.java)
        showToast("검색 갯수 : ${searchList.items.size}")

        adapter?.items?.clear()
        searchList.items.forEach {
            val movieVideo = MovieVideo(it.id?.videoId,
                it.snippet?.publishedAt, it.snippet?.title,
                it.snippet?.description, it.snippet?.thumbnails?.medium?.url)
            adapter?.items?.add(movieVideo)
        }
        adapter?.notifyDataSetChanged()

    }

    fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

}