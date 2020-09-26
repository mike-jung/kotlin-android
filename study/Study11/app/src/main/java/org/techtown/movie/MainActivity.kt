package org.techtown.movie

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import org.techtown.movie.data.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class MainActivity : AppCompatActivity(), FragmentCallback {

    companion object {
        var requestQueue: RequestQueue? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        requestQueue =  Volley.newRequestQueue(applicationContext)

        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.drawer_open,
                R.string.drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navigationView.setNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.item1 -> {
                    showToast("영화 목록 선택됨.")
                    onFragmentSelected(FragmentCallback.FragmentItem.ITEM_LIST, null)
                }
                R.id.item2 -> {
                    showToast("예매순 선택됨.")
                    onFragmentSelected(FragmentCallback.FragmentItem.ITEM2, null)
                }
                R.id.item3 -> {
                    showToast("영화관 선택됨.")
                    onFragmentSelected(FragmentCallback.FragmentItem.ITEM3, null)
                }
                R.id.item4 -> {
                    showToast("즐겨찾기 선택됨.")
                    onFragmentSelected(FragmentCallback.FragmentItem.ITEM4, null)
                }
            }

            drawerLayout.closeDrawer(GravityCompat.START)
            return@setNavigationItemSelectedListener true
        }


        bottom_navigation.selectedItemId = R.id.tab1
        bottom_navigation.setOnNavigationItemSelectedListener {

            when (it.itemId) {
                R.id.tab1 -> {
                    showToast("영화 목록 탭 선택됨")
                    onFragmentSelected(FragmentCallback.FragmentItem.ITEM_LIST, null)

                    return@setOnNavigationItemSelectedListener true
                }
                R.id.tab2 -> {
                    showToast("예매순 탭 선택됨")
                    onFragmentSelected(FragmentCallback.FragmentItem.ITEM2, null)

                    return@setOnNavigationItemSelectedListener true
                }
                R.id.tab3 -> {
                    showToast("영화관 탭 선택됨")
                    onFragmentSelected(FragmentCallback.FragmentItem.ITEM3, null)

                    return@setOnNavigationItemSelectedListener true
                }
                R.id.tab4 -> {
                    showToast("즐겨찾기 탭 선택됨")
                    onFragmentSelected(FragmentCallback.FragmentItem.ITEM4, null)

                    return@setOnNavigationItemSelectedListener true
                }
            }

            return@setOnNavigationItemSelectedListener false

        }

        // 박스오피스 요청
        requestBoxOffice()

    }


    /**
     * 박스오피스 데이터 요청
     */
    fun requestBoxOffice() {
        // 어제 날짜 생성
        val cal = Calendar.getInstance()
        cal.add(Calendar.DATE, -1)
        val targetDate = Utils.dateFormat.format(cal.time)

        val apiKey = "430156241533f1d058c603178cc3ca0e"
        val url = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json?key=${apiKey}&targetDt=${targetDate}"

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
                    processResponse(it)
                }

                processResponse(it)
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
        requestQueue?.add(request)
        println("\n영화 일별 박스오피스 요청함")
    }

    fun processResponse(response:String) {
        val gson = Gson()
        val boxOffice = gson.fromJson(response, BoxOffice::class.java)
        println("\n영화 정보의 수: " + boxOffice.boxOfficeResult.dailyBoxOfficeList.size)

        requestDetails(boxOffice.boxOfficeResult.dailyBoxOfficeList)
    }

    fun requestDetails(dailyBoxOfficeList:ArrayList<MovieInfo>) {
        // 이전 데이터 삭제
        MovieList.data.clear()

        // 5개 데이터 요청하고 MovieList에 MovieData 추가
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
                    println("\n응답 -> ${it}")

                    processDetailsResponse(index, it)
                },
                {
                    println("\n에러 -> ${it.message}")
                }
            ) {}

            request.setShouldCache(false)
            requestQueue?.add(request)
            println("\n영화 상세정보 요청함")
        }
    }

    fun processDetailsResponse(index:Int, response:String) {
        val gson = Gson()
        val movieInfoDetails = gson.fromJson(response, MovieInfoDetails::class.java)
        val movieDetails = movieInfoDetails.movieInfoResult.movieInfo

        println("\n영화 제목과 제작국가 : ${movieDetails.movieNm}, ${movieDetails.movieNmEn}, ${movieDetails.nations[0].nationNm}")

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
                    println("\n응답 -> ${it}")

                    processTMDBSearchResponse(index, it)
                },
                {
                    println("\n에러 -> ${it.message}")
                }
            ) {}

            request.setShouldCache(false)
            requestQueue?.add(request)
            println("\nTMDB 영화 검색 요청함")
        }
    }

    fun processTMDBSearchResponse(index:Int, response:String) {
        val gson = Gson()
        val tmdbMovieDetails = gson.fromJson(response, TmdbMovieDetails::class.java)
        val movieResult = tmdbMovieDetails.results[0]

        println("\n영화 id, 포스터, 줄거리 : ${movieResult.id}, ${movieResult.poster_path}, ${movieResult.overview}")
        MovieList.data[index].tmdbMovieResult = movieResult

        // 화면에 프래그먼트 표시
        supportFragmentManager.beginTransaction().add(R.id.container, MovieListFragment()).commit()
        toolbar.title = "박스 오피스"
    }



    override fun onFragmentSelected(item: FragmentCallback.FragmentItem, bundle: Bundle?) {
        val index = bundle?.getInt("index", 0)
        val listType = bundle?.getString("listType")

        var fragment: Fragment
        when(item) {
            FragmentCallback.FragmentItem.ITEM_LIST -> {
                toolbar.title = "박스 오피스"
                fragment = MovieListFragment()
            }
            FragmentCallback.FragmentItem.ITEM_DETAILS -> {
                toolbar.title = "영화 상세"
                fragment = MovieDetailsFragment.newInstance(index, listType)
            }
            FragmentCallback.FragmentItem.ITEM2 -> {
                toolbar.title = "예매순"
                fragment = MovieReservationListFragment()
            }
            FragmentCallback.FragmentItem.ITEM3 -> {
                toolbar.title = "영화관"
                fragment = Fragment3()
            }
            FragmentCallback.FragmentItem.ITEM4 -> {
                toolbar.title = "즐겨찾기"
                fragment = Fragment4()
            }
        }

        supportFragmentManager.beginTransaction().replace(R.id.container, fragment!!).commit()
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    fun showToast(message:String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

}
