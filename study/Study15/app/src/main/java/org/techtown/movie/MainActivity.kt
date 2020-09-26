package org.techtown.movie

import android.database.sqlite.SQLiteDatabase
import android.graphics.Movie
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
import com.pedro.library.AutoPermissions
import kotlinx.android.synthetic.main.activity_main.*
import org.techtown.movie.data.*
import java.util.*
import kotlin.collections.HashMap


class MainActivity : AppCompatActivity(), FragmentCallback {

    companion object {
        var requestQueue: RequestQueue? = null
    }

    val databaseName = "movie"
    var database: SQLiteDatabase? = null

    val tableMovieInfo = "movie_info"
    val tableMovieDetails = "movie_details"
    val tableTmdbMovieResult = "movie_tmdb_movie_result"

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
                    onFragmentSelected(FragmentCallback.FragmentItem.ITEM_RESERVATION, null)
                }
                R.id.item3 -> {
                    showToast("영화관 선택됨.")
                    onFragmentSelected(FragmentCallback.FragmentItem.ITEM_PLACE, null)
                }
                R.id.item4 -> {
                    showToast("즐겨찾기 선택됨.")
                    onFragmentSelected(FragmentCallback.FragmentItem.ITEM_FAVORITE, null)
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
                    onFragmentSelected(FragmentCallback.FragmentItem.ITEM_RESERVATION, null)

                    return@setOnNavigationItemSelectedListener true
                }
                R.id.tab3 -> {
                    showToast("영화관 탭 선택됨")
                    onFragmentSelected(FragmentCallback.FragmentItem.ITEM_PLACE, null)

                    return@setOnNavigationItemSelectedListener true
                }
                R.id.tab4 -> {
                    showToast("즐겨찾기 탭 선택됨")
                    onFragmentSelected(FragmentCallback.FragmentItem.ITEM_FAVORITE, null)

                    return@setOnNavigationItemSelectedListener true
                }
            }

            return@setOnNavigationItemSelectedListener false

        }

        // 박스오피스 요청
        requestBoxOffice()

        // 데이터베이스 오픈
        createDatabase()
        createTable()


        AutoPermissions.Companion.loadAllPermissions(this, 101);
    }


    fun createDatabase() {
        database = openOrCreateDatabase(databaseName, MODE_PRIVATE, null)
        println("데이터베이스 생성 또는 오픈함\n")
    }

    fun createTable() {

        if (database == null) {
            println("데이터베이스를 먼저 오픈하세요.\n")
            return
        }

        val sql = "create table if not exists ${tableMovieInfo}" +
                "(_id integer PRIMARY KEY autoincrement, " +
                " movie_id text, " +    // 각 테이블의 레코드를 연결하기 위한 id
                " type text, " +
                " rank text, " +    // type = boxOffice 인 경우
                " rankInten text, " +
                " rankOldAndNew text, " +
                " movieCd text, " +
                " movieNm text, " +
                " openDt text, " +
                " audiCnt text, " +
                " audiAcc text, " +
                " scrnCnt text, " +
                " showCnt text, " +
                " title text, " +     // type = reservation 인 경우
                " score1 text, " +
                " score2 text, " +
                " reserve text, " +
                " thumbnailUrl text, " +
                " movieNmEn text, " +
                " prdtYear text, " +
                " typeNm text, " +
                " prdtStatNm text, " +
                " nationAlt text, " +
                " genreAlt text, " +
                " repNationNm text, " +
                " repGenreNm text, " +
                " directors text, " +
                " companys text)"

        database?.execSQL(sql)
        println("${tableMovieInfo} 테이블 생성함\n")

        val sql2 = "create table if not exists ${tableMovieDetails}" +
                "(_id integer PRIMARY KEY autoincrement, " +
                " movie_id text, " +    // 각 테이블의 레코드를 연결하기 위한 id
                " movieCd text, " +
                " movieNm text, " +
                " movieNmEn text, " +
                " prdtYear text, " +
                " showTm text, " +
                " openDt text, " +
                " typeNm text, " +
                " nations text, " +
                " genres text, " +
                " directors text, " +
                " actors text, " +
                " audits text, " +
                " companys text)"

        database?.execSQL(sql2)
        println("${tableMovieDetails} 테이블 생성함\n")


        val sql3 = "create table if not exists ${tableTmdbMovieResult}" +
                "(_id integer PRIMARY KEY autoincrement, " +
                " movie_id text, " +    // 각 테이블의 레코드를 연결하기 위한 id
                " id text, " +
                " title text, " +
                " original_title text, " +
                " overview text, " +
                " poster_path text)"

        database?.execSQL(sql3)
        println("${tableTmdbMovieResult} 테이블 생성함\n")
    }

    /**
     * Movie 레코드 추가
     */
    fun insertMovie(movieId:String, type:String, index:Int) {
        println("insertMovie called : ${movieId}, ${type}, ${index}")

        if (database == null) {
            println("데이터베이스를 먼저 오픈하세요.\n")
            return
        }

        var data:Array<String?>? = null
        when(type) {
            "boxOffice" -> {
                data = arrayOf(
                    movieId,
                    "boxOffice",
                    MovieList.data[index].movieInfo?.rank,
                    MovieList.data[index].movieInfo?.rankInten,
                    MovieList.data[index].movieInfo?.rankOldAndNew,
                    MovieList.data[index].movieInfo?.movieCd,
                    MovieList.data[index].movieInfo?.movieNm,
                    MovieList.data[index].movieInfo?.openDt,
                    MovieList.data[index].movieInfo?.audiCnt,
                    MovieList.data[index].movieInfo?.audiAcc,
                    MovieList.data[index].movieInfo?.scrnCnt,
                    MovieList.data[index].movieInfo?.showCnt,
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    ""
                )

            }
            "reservation" -> {
                println("reservation movieInfo -> ${MovieList.reservationData[index].movieInfo}")

                data = arrayOf(
                    movieId,
                    "reservation",
                    "",
                    "",
                    "",
                    MovieList.reservationData[index].movieInfo?.movieCd,
                    MovieList.reservationData[index].movieInfo?.movieNm,
                    MovieList.reservationData[index].movieInfo?.openDt,
                    "",
                    "",
                    "",
                    "",
                    MovieList.reservation[index].title,
                    MovieList.reservation[index].score1,
                    MovieList.reservation[index].score2,
                    MovieList.reservation[index].reserve,
                    MovieList.reservation[index].thumbnailUrl,
                    MovieList.reservationData[index].movieInfo?.movieNmEn,
                    MovieList.reservationData[index].movieInfo?.prdtYear,
                    MovieList.reservationData[index].movieInfo?.typeNm,
                    MovieList.reservationData[index].movieInfo?.prdtStatNm,
                    MovieList.reservationData[index].movieInfo?.nationAlt,
                    MovieList.reservationData[index].movieInfo?.genreAlt,
                    MovieList.reservationData[index].movieInfo?.repNationNm,
                    MovieList.reservationData[index].movieInfo?.repGenreNm,
                    MovieList.reservationData[index].movieInfo?.directors?.get(0)?.peopleNm,
                    MovieList.reservationData[index].movieInfo?.companys?.get(0)?.companyNm
                )

            }
        }

        val sql = "insert into ${tableMovieInfo} " +
                "    (movie_id, " +
                "     type, " +
                "     rank, " +
                "     rankInten, " +
                "     rankOldAndNew, " +
                "     movieCd, " +
                "     movieNm, " +
                "     openDt, " +
                "     audiCnt, " +
                "     audiAcc, " +
                "     scrnCnt, " +
                "     showCnt, " +
                "     title, " +
                "     score1, " +
                "     score2, " +
                "     reserve, " +
                "     thumbnailUrl, " +
                "     movieNmEn, " +
                "     prdtYear, " +
                "     typeNm, " +
                "     prdtStatNm, " +
                "     nationAlt, " +
                "     genreAlt, " +
                "     repNationNm, " +
                "     repGenreNm, " +
                "     directors, " +
                "     companys) " +
                " values " +
                "    (?, ?, ?, ?, ?, ?, ?, ?, ?, ?," +
                "     ?, ?, ?, ?, ?, ?, ?, ?, ?, ?," +
                "     ?, ?, ?, ?, ?, ?, ?)"

        database?.execSQL(sql, data)
        println("데이터 추가함\n")


        // MovieDetails
        var data2:Array<String?>? = null
        when(type) {
            "boxOffice" -> {
                data2 = arrayOf(
                    movieId,
                    MovieList.data[index].movieDetails?.movieCd,
                    MovieList.data[index].movieDetails?.movieNm,
                    MovieList.data[index].movieDetails?.movieNmEn,
                    MovieList.data[index].movieDetails?.prdtYear,
                    MovieList.data[index].movieDetails?.showTm,
                    MovieList.data[index].movieDetails?.openDt,
                    MovieList.data[index].movieDetails?.typeNm,
                    MovieList.data[index].movieDetails?.nations?.get(0)?.nationNm,
                    MovieList.data[index].movieDetails?.genres?.get(0)?.genreNm,
                    MovieList.data[index].movieDetails?.directors?.get(0)?.peopleNm,
                    MovieList.data[index].movieDetails?.actors?.get(0)?.peopleNm,
                    MovieList.data[index].movieDetails?.audits?.get(0)?.watchGradeNm,
                    MovieList.data[index].movieDetails?.companys?.get(0)?.companyNm
                )
            }
            "reservation" -> {
                data2 = arrayOf(
                    movieId,
                    MovieList.reservationData[index].movieDetails?.movieCd,
                    MovieList.reservationData[index].movieDetails?.movieNm,
                    MovieList.reservationData[index].movieDetails?.movieNmEn,
                    MovieList.reservationData[index].movieDetails?.prdtYear,
                    MovieList.reservationData[index].movieDetails?.showTm,
                    MovieList.reservationData[index].movieDetails?.openDt,
                    MovieList.reservationData[index].movieDetails?.typeNm,
                    MovieList.reservationData[index].movieDetails?.nations?.get(0)?.nationNm,
                    MovieList.reservationData[index].movieDetails?.genres?.get(0)?.genreNm,
                    MovieList.reservationData[index].movieDetails?.directors?.get(0)?.peopleNm,
                    MovieList.reservationData[index].movieDetails?.actors?.get(0)?.peopleNm,
                    MovieList.reservationData[index].movieDetails?.audits?.get(0)?.watchGradeNm,
                    MovieList.reservationData[index].movieDetails?.companys?.get(0)?.companyNm
                )
            }
        }

        val sql2 = "insert into ${tableMovieDetails} " +
                "    (movie_id, " +
                "     movieCd, " +
                "     movieNm, " +
                "     movieNmEn, " +
                "     prdtYear, " +
                "     showTm, " +
                "     openDt, " +
                "     typeNm, " +
                "     nations, " +
                "     genres, " +
                "     directors, " +
                "     actors, " +
                "     audits, " +
                "     companys) " +
                " values " +
                "    (?, ?, ?, ?, ?, ?, ?, ?, ?, ?," +
                "     ?, ?, ?, ?)"

        database?.execSQL(sql2, data2)
        println("데이터 추가함\n")


        // TmdbMovieResult 추가
        var data3:Array<String?>? = null
        when(type) {
            "boxOffice" -> {
                data3 = arrayOf(
                    movieId,
                    MovieList.data[index].tmdbMovieResult?.id.toString(),
                    MovieList.data[index].tmdbMovieResult?.title,
                    MovieList.data[index].tmdbMovieResult?.original_title,
                    MovieList.data[index].tmdbMovieResult?.overview,
                    MovieList.data[index].tmdbMovieResult?.poster_path
                )
            }
            "reservation" -> {
                data3 = arrayOf(
                    movieId,
                    MovieList.reservationData[index].tmdbMovieResult?.id.toString(),
                    MovieList.reservationData[index].tmdbMovieResult?.title,
                    MovieList.reservationData[index].tmdbMovieResult?.original_title,
                    MovieList.reservationData[index].tmdbMovieResult?.overview,
                    MovieList.reservationData[index].tmdbMovieResult?.poster_path
                )
            }
        }

        val sql3 = "insert into ${tableTmdbMovieResult} " +
                "    (movie_id, " +
                "     id, " +
                "     title, " +
                "     original_title, " +
                "     overview, " +
                "     poster_path) " +
                " values " +
                "    (?, ?, ?, ?, ?, ?)"

        database?.execSQL(sql3, data3)
        println("데이터 추가함\n")
    }

    /**
     * movieId 조회 : MovieDetails 테이블에서 movieCd 조회
     */
    fun queryMovieId(movieCd: String?):String {
        if (database == null) {
            println("데이터베이스를 먼저 오픈하세요.\n")
            return ""
        }

        var movieId = ""

        // 조회
        val sql = "select " +
                "  _id, " +
                "  movie_id, " +
                "  movieCd, " +
                "  movieNm, " +
                "  movieNmEn " +
                " from ${tableMovieDetails} " +
                " where movieCd = '${movieCd}'"

        val cursor = database?.rawQuery(sql, null)
        if (cursor != null) {
            cursor.moveToNext()

            movieId = cursor.getString(1)
        }

        return movieId
    }

    /**
     * 이름으로 즐겨찾기된 영화가 있는지 확인
     */
    fun queryMovieExists(movieTitle: String?):Boolean {
        println("queryMovieExists called : ${movieTitle}")

        if (database == null) {
            println("데이터베이스를 먼저 오픈하세요.\n")
            return false
        }

        // 조회
        val sql = "select " +
                "  _id, " +
                "  movie_id, " +
                "  movieCd, " +
                "  movieNm, " +
                "  movieNmEn " +
                " from ${tableMovieDetails} " +
                " where movieNm = '${movieTitle}'"

        println("SQL -> ${sql}")
        val cursor = database?.rawQuery(sql, null)
        if (cursor != null && cursor.count > 0) {
            return true
        }

        return false
    }

    /**
     * Movie 삭제
     */
    fun deleteMovie(movieId:String) {
        println("deleteMovie called : ${movieId}")

        if (database == null) {
            println("데이터베이스를 먼저 오픈하세요.\n")
            return
        }

        val sql = "delete from ${tableMovieInfo} " +
                "  where movie_id = '${movieId}'"

        database?.execSQL(sql)
        println("데이터 삭제함\n")

        val sql2 = "delete from ${tableMovieDetails} " +
                "  where movie_id = '${movieId}'"

        database?.execSQL(sql2)
        println("데이터 삭제함\n")

        val sql3 = "delete from ${tableTmdbMovieResult} " +
                "  where movie_id = '${movieId}'"

        database?.execSQL(sql3)
        println("데이터 삭제함\n")
    }


    /**
     * Movie 테이블 조회
     */
    fun queryMovie() {
        if (database == null) {
            println("데이터베이스를 먼저 오픈하세요.\n")
            return
        }

        // MovieInfo 테이블 조회
        val sql = "select " +
                "  _id, " +
                "  movie_id, " +
                "  type, " +
                "  rank, " +
                "  rankInten, " +
                "  rankOldAndNew, " +
                "  movieCd, " +
                "  movieNm, " +
                "  openDt, " +
                "  audiCnt, " +
                "  audiAcc, " +
                "  scrnCnt, " +
                "  showCnt, " +
                "  title, " +
                "  score1, " +
                "  score2, " +
                "  reserve, " +
                "  thumbnailUrl, " +
                "  movieNmEn, " +
                "  prdtYear, " +
                "  typeNm, " +
                "  prdtStatNm, " +
                "  nationAlt, " +
                "  genreAlt, " +
                "  repNationNm, " +
                "  repGenreNm, " +
                "  directors, " +
                "  companys " +
                "from ${tableMovieInfo}"

        println("SQL -> ${sql}")
        val cursor = database?.rawQuery(sql, null)
        if (cursor != null) {
            // 이전 데이터 삭제
            MovieFavoriteList.type.clear()
            MovieFavoriteList.data.clear()
            MovieFavoriteList.reservation.clear()
            MovieFavoriteList.reservationData.clear()

            for (index in 0 until cursor.count) {
                cursor.moveToNext()
                val _id = cursor.getInt(0)
                val movie_id = cursor.getString(1)
                val type = cursor.getString(2)
                println("즐겨찾기 데이터 #${index} : ${_id}, ${movie_id}, ${type}")

                when(type) {
                    "boxOffice" -> {
                        val rank = cursor.getString(3)
                        val rankInten = cursor.getString(4)
                        val rankOldAndNew = cursor.getString(5)
                        val movieCd = cursor.getString(6)
                        val movieNm = cursor.getString(7)
                        val openDt = cursor.getString(8)
                        val audiCnt = cursor.getString(9)
                        val audiAcc = cursor.getString(10)
                        val scrnCnt = cursor.getString(11)
                        val showCnt = cursor.getString(12)

                        val movieInfo = MovieInfo(
                            rank, rankInten, rankOldAndNew, movieCd, movieNm,
                            openDt, audiCnt, audiAcc, scrnCnt, showCnt
                        )
                        var movieData = MovieData(movieInfo, null, null)
                        MovieFavoriteList.data.add(movieData)

                        // 즐겨찾기를 위한 type 구분
                        MovieFavoriteList.type.add("boxOffice")

                    }
                    "reservation" -> {
                        val title = cursor.getString(13)
                        val score1 = cursor.getString(14)
                        val score2 = cursor.getString(15)
                        val reserve = cursor.getString(16)
                        val thumbnailUrl = cursor.getString(17)

                        val movieReservation = MovieReservation(
                            title, score1, score2, reserve, thumbnailUrl
                        )
                        MovieFavoriteList.reservation.add(movieReservation)

                        val movieCd = cursor.getString(6)
                        val movieNm = cursor.getString(7)
                        val movieNmEn = cursor.getString(18)
                        val prdtYear = cursor.getString(19)
                        val openDt = cursor.getString(8)
                        val typeNm = cursor.getString(20)
                        val prdtStatNm = cursor.getString(21)
                        val nationAlt = cursor.getString(22)
                        val genreAlt = cursor.getString(23)
                        val repNationNm = cursor.getString(24)
                        val repGenreNm = cursor.getString(25)
                        val directors = cursor.getString(26)
                        val companys = cursor.getString(27)

                        val movieSearchInfo = MovieSearchInfo(
                            movieCd, movieNm, movieNmEn, prdtYear, openDt,
                            typeNm, prdtStatNm, nationAlt, genreAlt, repNationNm,
                            repGenreNm, arrayListOf(DirectorInfo(directors, "")),
                            arrayListOf(CompanyInfo(companys, ""))
                        )
                        var movieSearchData = MovieSearchData(movieSearchInfo, null, null)
                        MovieFavoriteList.reservationData.add(movieSearchData)

                        // 즐겨찾기를 위한 type 구분
                        MovieFavoriteList.type.add("reservation")

                    }
                }


                // MovieDetails 테이블 조회
                val sql2 = "select " +
                        "  _id, " +
                        "  movie_id, " +
                        "  movieCd, " +
                        "  movieNm, " +
                        "  movieNmEn, " +
                        "  prdtYear, " +
                        "  showTm, " +
                        "  openDt, " +
                        "  typeNm, " +
                        "  nations, " +
                        "  genres, " +
                        "  directors, " +
                        "  actors, " +
                        "  audits, " +
                        "  companys " +
                        " from ${tableMovieDetails} " +
                        " where movie_id = '${movie_id}'"

                println("SQL -> ${sql2}")
                val cursor2 = database?.rawQuery(sql2, null)
                if (cursor2 != null) {
                    cursor2.moveToNext()

                    val movieCd = cursor2.getString(2)
                    val movieNm = cursor2.getString(3)
                    val movieNmEn = cursor2.getString(4)
                    val prdtYear = cursor2.getString(5)
                    val showTm = cursor2.getString(6)
                    val openDt = cursor2.getString(7)
                    val typeNm = cursor2.getString(8)
                    val nations = cursor2.getString(9)
                    val genres = cursor2.getString(10)
                    val directors = cursor2.getString(11)
                    val actors = cursor2.getString(12)
                    val audits = cursor2.getString(13)
                    val companys = cursor2.getString(14)

                    val movieDetails = MovieDetails(
                        movieCd, movieNm, movieNmEn, prdtYear, showTm,
                        openDt, typeNm, arrayListOf(NationInfo(nations)),
                        arrayListOf(GenreInfo(genres)),
                        arrayListOf(DirectorInfo(directors, "")),
                        arrayListOf(ActorInfo(actors, "")),
                        arrayListOf(AuditInfo(audits)),
                        arrayListOf(CompanyInfo(companys, ""))
                    )

                    when(type) {
                        "boxOffice" -> {
                            MovieFavoriteList.data[MovieFavoriteList.data.size-1].movieDetails = movieDetails
                        }
                        "reservation" -> {
                            MovieFavoriteList.reservationData[MovieFavoriteList.reservationData.size-1].movieDetails = movieDetails
                        }
                    }

                }

                // TmdbMovieResult 테이블 조회
                val sql3 = "select " +
                        "  _id, " +
                        "  movie_id, " +
                        "  id, " +
                        "  title, " +
                        "  original_title, " +
                        "  overview, " +
                        "  poster_path " +
                        " from ${tableTmdbMovieResult} " +
                        " where movie_id = '${movie_id}'"

                println("SQL -> ${sql3}")
                val cursor3 = database?.rawQuery(sql3, null)
                if (cursor3 != null) {
                    cursor3.moveToNext()

                    val curId = cursor3.getString(2)
                    val title = cursor3.getString(3)
                    val original_title = cursor3.getString(4)
                    val overview = cursor3.getString(5)
                    val poster_path = cursor3.getString(6)

                    val tmdbMovieResult = MovieResult(
                        curId.toInt(), title, original_title, overview, poster_path
                    )

                    when(type) {
                        "boxOffice" -> {
                            MovieFavoriteList.data[MovieFavoriteList.data.size - 1].tmdbMovieResult = tmdbMovieResult
                        }
                        "reservation" -> {
                            MovieFavoriteList.reservationData[MovieFavoriteList.reservationData.size - 1].tmdbMovieResult = tmdbMovieResult
                        }
                    }

                }

            }
            cursor.close()
        }
        println("데이터 조회함\n")
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

    fun processResponse(response: String) {
        val gson = Gson()
        val boxOffice = gson.fromJson(response, BoxOffice::class.java)
        println("\n영화 정보의 수: " + boxOffice.boxOfficeResult.dailyBoxOfficeList.size)

        requestDetails(boxOffice.boxOfficeResult.dailyBoxOfficeList)
    }

    fun requestDetails(dailyBoxOfficeList: ArrayList<MovieInfo>) {
        // 이전 데이터 삭제
        MovieList.data.clear()

        // 5개 데이터 요청하고 MovieList에 MovieData 추가
        for (index in 0..4) {
            var movieData = MovieData(dailyBoxOfficeList[index], null, null)
            MovieList.data.add(movieData)

            sendDetails(index, dailyBoxOfficeList[index].movieCd)
        }
    }

    fun sendDetails(index: Int, movieCd: String?) {
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

    fun processDetailsResponse(index: Int, response: String) {
        val gson = Gson()
        val movieInfoDetails = gson.fromJson(response, MovieInfoDetails::class.java)
        val movieDetails = movieInfoDetails.movieInfoResult.movieInfo

        println("\n영화 제목과 제작국가 : ${movieDetails.movieNm}, ${movieDetails.movieNmEn}, ${movieDetails.nations[0].nationNm}")

        MovieList.data[index].movieDetails = movieDetails
        requestTMDBSearch(index, movieDetails)
    }

    fun requestTMDBSearch(index: Int, movieDetails: MovieDetails) {
        var movieName = movieDetails.movieNm
        if (movieDetails.nations[0].nationNm != "한국") {
            movieName = movieDetails.movieNmEn
        }

        sendTMDBSearch(index, movieName)
    }

    fun sendTMDBSearch(index: Int, movieName: String?) {
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

    fun processTMDBSearchResponse(index: Int, response: String) {
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
            FragmentCallback.FragmentItem.ITEM_RESERVATION -> {
                toolbar.title = "예매순"
                fragment = MovieReservationListFragment()
            }
            FragmentCallback.FragmentItem.ITEM_PLACE -> {
                toolbar.title = "영화관"
                fragment = TheaterFragment()
            }
            FragmentCallback.FragmentItem.ITEM_FAVORITE -> {
                toolbar.title = "즐겨찾기"
                fragment = MovieFavoriteListFragment()
            }
            FragmentCallback.FragmentItem.ITEM_FAVORITE_DETAILS -> {
                toolbar.title = "즐겨찾기의 영화 상세"
                fragment = MovieFavoriteDetailsFragment.newInstance(index, listType)
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

    fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

}
