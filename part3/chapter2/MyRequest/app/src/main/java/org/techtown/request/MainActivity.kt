package org.techtown.request

import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    companion object {
        var requestQueue:RequestQueue? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        requestQueue =  Volley.newRequestQueue(applicationContext)

        requestButton.setOnClickListener {
            send()
        }
    }

    fun send() {
        val url = input1.text.toString()

        val request = object: StringRequest(
            Request.Method.GET,
            url,
            {
                //output1.append("\n응답 -> ${it}")

                processResponse(it)
            },
            {
                output1.append("\n에러 -> ${it.message}")
            }
        ) {
            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params["userid"] = "john"

                return params
            }
        }

        request.setShouldCache(false)
        requestQueue?.add(request)
        output1.append("\n영화 일별 박스오피스 요청함")
    }

    fun processResponse(response:String) {
        val gson = Gson()
        val boxOffice = gson.fromJson(response, BoxOffice::class.java)
        output1.append("\n영화 정보의 수: " + boxOffice.boxOfficeResult.dailyBoxOfficeList.size)

        requestDetails(boxOffice.boxOfficeResult.dailyBoxOfficeList)
    }

    fun requestDetails(dailyBoxOfficeList:ArrayList<MovieInfo>) {
        for (index in 0..4) {
            var movieData = MovieData(dailyBoxOfficeList[index], null, null)
            MovieList.data.add(movieData)

            sendDetails(index, dailyBoxOfficeList[index].movieCd)
        }
    }

    fun sendDetails(index:Int, movieCd:String?) {
        if (movieCd != null) {
            val apiKey = "430156241533f1d058c603178cc3ca0e"
            val url = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/movie/searchMovieInfo.json?key=${apiKey}&movieCd=${movieCd}"

            val request = object: StringRequest(
                Request.Method.GET,
                url,
                {
                    output1.append("\n응답 -> ${it}")

                    processDetailsResponse(index, it)
                },
                {
                    output1.append("\n에러 -> ${it.message}")
                }
            ) {}

            request.setShouldCache(false)
            requestQueue?.add(request)
            output1.append("\n영화 상세정보 요청함")
        }
    }

    fun processDetailsResponse(index:Int, response:String) {
        val gson = Gson()
        val movieInfoDetails = gson.fromJson(response, MovieInfoDetails::class.java)
        val movieDetails = movieInfoDetails.movieInfoResult.movieInfo

        output1.append("\n영화 제목과 제작국가 : ${movieDetails.movieNm}, ${movieDetails.movieNmEn}, ${movieDetails.nations[0].nationNm}")

        MovieList.data[index].movieDetails = movieDetails
        requestTMDBSearch(index, movieDetails)
    }

    fun requestTMDBSearch(index:Int, movieDetails:MovieDetails) {
        var movieName = movieDetails.movieNm
        if (movieDetails.nations[0].nationNm != "한국") {
            movieName = movieDetails.movieNmEn
        }

        sendTMDBSearch(index, movieName)
    }

    fun sendTMDBSearch(index:Int, movieName:String?) {
        if (movieName != null) {
            val apiKey = "ce151c672a74a33a36268882685ed88f"
            val url = "https://api.themoviedb.org/3/search/movie?api_key=${apiKey}&query=${movieName}&language=ko-KR&page=1"

            val request = object: StringRequest(
                Request.Method.GET,
                url,
                {
                    output1.append("\n응답 -> ${it}")

                    processTMDBSearchResponse(index, it)
                },
                {
                    output1.append("\n에러 -> ${it.message}")
                }
            ) {}

            request.setShouldCache(false)
            requestQueue?.add(request)
            output1.append("\nTMDB 영화 검색 요청함")
        }
    }

    fun processTMDBSearchResponse(index:Int, response:String) {
        val gson = Gson()
        val tmdbMovieDetails = gson.fromJson(response, TmdbMovieDetails::class.java)
        val movieResult = tmdbMovieDetails.results[0]

        output1.append("\n영화 id, 포스터, 줄거리 : ${movieResult.id}, ${movieResult.poster_path}, ${movieResult.overview}")
        MovieList.data[index].tmdbMovieResult = movieResult

        setPosterImage(index, movieResult.poster_path)
    }

    fun setPosterImage(index:Int, posterPath:String?) {
        if (posterPath != null && posterPath.isNotEmpty()) {
            val url = "http://image.tmdb.org/t/p/w200${posterPath}"

            var targetImageView = poster1
            when(index) {
                0 -> {
                    targetImageView = poster1
                }
                1 -> {
                    targetImageView = poster2
                }
                2 -> {
                    targetImageView = poster3
                }
                3 -> {
                    targetImageView = poster4
                }
                4 -> {
                    targetImageView = poster5
                }
            }

            Glide.with(this)
                .load(url)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .dontAnimate()
                .into(targetImageView)

        }
    }

}