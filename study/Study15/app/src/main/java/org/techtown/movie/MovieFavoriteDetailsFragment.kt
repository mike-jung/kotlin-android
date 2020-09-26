package org.techtown.movie

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlinx.android.synthetic.main.fragment_movie_details.*
import kotlinx.android.synthetic.main.fragment_movie_details.view.*
import org.techtown.movie.data.MovieFavoriteList
import org.techtown.movie.data.MovieList

/**
 * 즐겨찾기 영화 상세 프래그먼트
 *
 * 즐겨찾기 아이콘과 한줄평 영역이 삭제되었음
 */
class MovieFavoriteDetailsFragment : Fragment() {
    var index:Int = 0
    var listType:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bundle = arguments
        if (bundle != null) {
            index = bundle.getInt("index")
            listType = bundle.getString("listType", "boxOffice")
        }

        println("MovieFavoriteDetails:create called : ${index}, ${listType}")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_movie_favorite_details, container, false)

        setData(rootView)

        return rootView
    }

    fun setData(rootView:View) {
        Log.d(TAG, "setData called : ${index}, ${listType}")

        var imageUrl:String? = ""
        var movieTitle:String? = ""
        var gradeTitle:String? = ""
        var releaseDate:String? = ""
        var genre:String? = ""
        var runningTime:String? = ""
        var subValue1:String? = ""
        var subValue2:String? = ""
        var subValue3:String? = ""
        var director:String? = ""
        var actor:String? = ""
        var company:String? = ""
        var synopsis:String? = ""

        when(listType) {
            "boxOffice" -> {
                rootView.subTitleTextView1.text = "순위 / 관객수"
                rootView.subTitleTextView2.text = "순위 증감"
                rootView.subTitleTextView3.text = "누적 관객수"

                // RatingBar를 안보이도록 설정
                rootView.gradeRatingBar.visibility = View.GONE

                // data 리스트에서 몇 번째 인덱스인지 확인
                val outIndex = Utils.getTypeIndex("boxOffice", index)

                val movieData = MovieFavoriteList.data[outIndex]
                Log.d(TAG, "MovieData -> ${movieData}")

                imageUrl = movieData.tmdbMovieResult?.poster_path
                movieTitle = movieData.movieInfo?.movieNm
                gradeTitle = movieData.movieDetails?.audits?.get(0)?.watchGradeNm
                releaseDate = movieData.movieInfo?.openDt
                genre = movieData.movieDetails?.genres?.get(0)?.genreNm
                runningTime = movieData.movieDetails?.showTm

                val subValue1Str = movieData.movieInfo?.audiCnt
                subValue1 = Utils.addComma(subValue1Str?.toInt()) + "명"

                subValue2 = movieData.movieInfo?.rankInten

                val subValue3Str = movieData.movieInfo?.audiAcc
                subValue3 = Utils.addComma(subValue3Str?.toInt()) + "명"

                director = movieData.movieDetails?.directors?.get(0)?.peopleNm
                actor = movieData.movieDetails?.actors?.get(0)?.peopleNm +
                        "(" + movieData.movieDetails?.actors?.get(0)?.cast + ")"
                company = movieData.movieDetails?.companys?.get(0)?.companyNm
                synopsis = movieData.tmdbMovieResult?.overview
            }
            "reservation" -> {
                rootView.subTitleTextView1.text = "순위 / 네티즌 참여"
                rootView.subTitleTextView2.text = "네티즌 평점"
                rootView.subTitleTextView3.text = "예매율"

                // RatingBar를 보이도록 설정
                rootView.gradeRatingBar.visibility = View.VISIBLE

                // data 리스트에서 몇 번째 인덱스인지 확인
                val outIndex = Utils.getTypeIndex("reservation", index)

                val movieSearchData = MovieFavoriteList.reservationData[outIndex]
                Log.d(TAG, "MovieSearchData -> ${movieSearchData}")

                imageUrl = movieSearchData.tmdbMovieResult?.poster_path
                movieTitle = movieSearchData.movieDetails?.movieNm
                gradeTitle = movieSearchData.movieDetails?.audits?.get(0)?.watchGradeNm

                val releaseDateStr = movieSearchData.movieDetails?.openDt
                releaseDate = Utils.dateFormat2.format(Utils.dateFormat.parse(releaseDateStr))

                genre = movieSearchData.movieDetails?.genres?.get(0)?.genreNm
                runningTime = movieSearchData.movieDetails?.showTm
                subValue1 = MovieFavoriteList.reservation[outIndex].score2
                subValue2 = MovieFavoriteList.reservation[outIndex].score1
                subValue3 = MovieFavoriteList.reservation[outIndex].reserve + "%"
                director = movieSearchData.movieDetails?.directors?.get(0)?.peopleNm
                actor = movieSearchData.movieDetails?.actors?.get(0)?.peopleNm +
                        "(" + movieSearchData.movieDetails?.actors?.get(0)?.cast + ")"
                company = movieSearchData.movieDetails?.companys?.get(0)?.companyNm
                synopsis = movieSearchData.tmdbMovieResult?.overview
            }
        }

        // 포스터 이미지
        if (imageUrl != null && imageUrl.isNotEmpty()) {
            val url = "http://image.tmdb.org/t/p/w200${imageUrl}"

            Glide.with(this)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .dontAnimate()
                .into(rootView.thumbnailView)
        }

        // 타이틀
        rootView.movieTitleView.text = movieTitle

        // 관람등급
        var grade = 0
        if (gradeTitle != null) {
            if (gradeTitle.indexOf("전체") > -1) {
                grade = 0
                rootView.ratingView.setImageResource(R.drawable.grade_all)
            } else if (gradeTitle.indexOf("12") > -1) {
                grade = 1
                rootView.ratingView.setImageResource(R.drawable.grade_12)
            } else if (gradeTitle.indexOf("15") > -1) {
                grade = 2
                rootView.ratingView.setImageResource(R.drawable.grade_15)
            } else if (gradeTitle.indexOf("19") > -1) {
                grade = 3
                rootView.ratingView.setImageResource(R.drawable.grade_19)
            }
        }

        // 개봉일
        rootView.releaseDateView.text = releaseDate

        // 장르
        rootView.genreView.text = genre

        // 상영시간
        rootView.runningTimeView.text = runningTime

        // 순위
        rootView.rankingView.text = ""

        // 관객수 / 참여
        rootView.subValueTextView1.text = subValue1

        // 평점 / 평점
        rootView.subValueTextView2.text = subValue2

        val rating = subValue2?.toFloat()
        if (rating != null) {
            rootView.gradeRatingBar.rating = rating / 2.0F
        }

        // 점유율 / 예매율
        rootView.subValueTextView3.text = subValue3

        // 감독
        rootView.directorView.text = director

        // 배우
        rootView.actorView.text = actor

        // 영화사
        rootView.companyView.text = company

        // 줄거리
        rootView.synopsisView.text = synopsis

    }


    fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    companion object {
        private const val TAG = "MovieFavoriteDetails"

        fun newInstance(index:Int?, listType:String?): MovieFavoriteDetailsFragment {
            val fragment = MovieFavoriteDetailsFragment()

            val bundle = Bundle()
            bundle.putInt("index", index!!)
            bundle.putString("listType", listType)
            fragment.arguments = bundle

            return fragment
        }
    }

}