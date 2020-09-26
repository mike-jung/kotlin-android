package org.techtown.movie

import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_movie_list.view.*
import org.jsoup.Jsoup
import org.jsoup.select.Elements
import org.techtown.movie.data.*

class MovieReservationListFragment : Fragment() {
    val TAG = "MovieReservationList"

    val url = "https://movie.naver.com/movie/running/current.nhn?order=reserve"

    lateinit var adapter:MovieReservationListAdapter

    var callback: FragmentCallback? = null

    // 요청에 대한 응답을 모두 받았을 때
    var done = false

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is FragmentCallback) {
            callback = context
        } else {
            Log.d(TAG, "Activity is not FragmentCallback.")
        }
    }

    override fun onDetach() {
        super.onDetach()

        if (callback != null) {
            callback = null
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.movie_reservation_list_fragment, container, false)

        showList(rootView as ViewGroup)

        if (MovieList.reservation.size < 1) {
            MyAsyncTask().execute(url)
        }

        return rootView
    }

    // RecyclerView를 이용한 영화 목록 표시
    fun showList(rootView: ViewGroup) {

        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rootView.recyclerView.layoutManager = layoutManager

        adapter = MovieReservationListAdapter()

        rootView.recyclerView.adapter = adapter

        adapter.listener = object : OnMovieReservationItemClickListener {
            override fun onItemClick(holder: MovieReservationListAdapter.ViewHolder?, view: View?, position: Int) {
                val movieReservation = MovieList.reservation[position]
                showToast("아이템 선택됨 : ${movieReservation?.title}")

                if (!done) {
                    showToast("영화 상세 정보를 가져오는 중입니다. 잠시 후 다시 시도해주세요.")
                    return
                }

                if (callback != null) {
                    val bundle = Bundle()
                    bundle.putInt("index", position)
                    bundle.putString("listType", "reservation")

                    callback!!.onFragmentSelected(FragmentCallback.FragmentItem.ITEM_DETAILS, bundle)
                }
            }
        }

    }


    inner class MyAsyncTask: AsyncTask<String, String, String>() {

        override fun onPreExecute() {
            super.onPreExecute()

            Log.d("MovieReservation", "요청 url -> ${url}")
        }

        override fun doInBackground(vararg params: String?): String {
            try {
                val doc = Jsoup.connect("${params[0]}").get()

                val elems: Elements = doc.select("ul.lst_detail_t1 li")
                run elemsLoop@{
                    elems.forEachIndexed { index, elem ->
                        val title = elem.select("dt.tit a").text()
                        val score1 = elem.select("dl.info_star div.star_t1 span.num").text()
                        val score2 = elem.select("span.num2").text()
                        val reserve = elem.select("dl.info_exp div.star_t1 span.num").text()

                        val thumbnailUrl = elem.select("div.thumb a img").attr("src")

                        MovieList.reservation.add(MovieReservation(title, score1, score2, reserve, thumbnailUrl))

                        if (index == 9) {
                            return@elemsLoop
                        }
                    }
                }

                return doc.title()

            } catch (e: Exception) {
                e.printStackTrace()
                return ""
            }
        }

        override fun onPostExecute(result: String?) {
            Log.d("MovieReservation", MovieList.reservation.joinToString())

            // 각 영화별로 상세정보 확인 (한 번이라도 받아온 적이 없다면)
            if (MovieList.reservationData.size < 1) {
                for ((index, reservationInfo) in MovieList.reservation.withIndex()) {
                    MovieList.reservationData.add(MovieSearchData())
                    requestSearch(index, reservationInfo.title)
                }
            }

            adapter.notifyDataSetChanged()
        }
    }

    fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }


    fun requestSearch(index:Int, title:String?) {
        val apiKey = "430156241533f1d058c603178cc3ca0e"
        val url = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/movie/searchMovieList.json?key=${apiKey}&movieNm=${title}"

        val request = object: StringRequest(
            Request.Method.GET,
            url,
            {
                println("\n응답 -> ${it}")

                // 키 사용 초과 여부 확인
                if (it.indexOf("faultInfo") > -1) {
                    println("키 사용량이 초과되었다면 아래 사이트에 가입 후 키를 발급받아 그 키로 사용하세요.")
                    println("http://kobis.or.kr/kobisopenapi")
                } else {
                    processResponse(index, it)
                }

            },
            {
                println("\n에러 -> ${it.message}")
            }
        ) {
            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params["userid"] = "john"

                return params
            }
        }

        request.setShouldCache(false)
        MainActivity.requestQueue?.add(request)
        println("\n영화 목록(검색) 요청함")
    }

    fun processResponse(index:Int, response:String) {
        val gson = Gson()
        val searchMovieList = gson.fromJson(response, SearchMovieList::class.java)
        val movieSearchInfo = searchMovieList.movieListResult.movieList[0]
        println("\n영화 코드와 이름 : ${movieSearchInfo.movieCd}, ${movieSearchInfo.movieNm}")

        MovieList.reservationData[index].movieInfo
        requestDetails(index, movieSearchInfo)
    }

    fun requestDetails(index:Int, movieSearchInfo:MovieSearchInfo) {
        if (movieSearchInfo.movieCd != null) {
            val apiKey = "430156241533f1d058c603178cc3ca0e"
            val url = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/movie/searchMovieInfo.json?key=${apiKey}&movieCd=${movieSearchInfo.movieCd}"

            val request = object: StringRequest(
                Request.Method.GET,
                url,
                {
                    println("\n응답 -> ${it}")

                    processDetailsResponse(index, it)
                },
                {
                    println("\n에러 -> ${it.message}")
                }
            ) {}

            request.setShouldCache(false)
            MainActivity.requestQueue?.add(request)
            println("\n영화 상세정보 요청함")
        }
    }

    fun processDetailsResponse(index:Int, response:String) {
        val gson = Gson()
        val movieInfoDetails = gson.fromJson(response, MovieInfoDetails::class.java)
        val movieDetails = movieInfoDetails.movieInfoResult.movieInfo

        println("\n영화 제목과 제작국가 : ${movieDetails.movieNm}, ${movieDetails.movieNmEn}, ${movieDetails.nations[0].nationNm}")

        MovieList.reservationData[index].movieDetails = movieDetails
        requestTMDBSearch(index, movieDetails)
    }

    fun requestTMDBSearch(index:Int, movieDetails: MovieDetails) {
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
                    println("\n응답 -> ${it}")

                    processTMDBSearchResponse(index, it)
                },
                {
                    println("\n에러 -> ${it.message}")
                }
            ) {}

            request.setShouldCache(false)
            MainActivity.requestQueue?.add(request)
            println("\nTMDB 영화 검색 요청함")
        }
    }

    fun processTMDBSearchResponse(index:Int, response:String) {
        val gson = Gson()
        val tmdbMovieDetails = gson.fromJson(response, TmdbMovieDetails::class.java)
        val movieResult = tmdbMovieDetails.results[0]

        println("\n영화 id, 포스터, 줄거리 : ${movieResult.id}, ${movieResult.poster_path}, ${movieResult.overview}")
        MovieList.reservationData[index].tmdbMovieResult = movieResult

        // 모두 완료인지 확인
        if (index >= 9) {
            done = true
        }
    }

}