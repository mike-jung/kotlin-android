package org.techtown.request

import android.graphics.Movie

data class MovieData(

    //===== 박스오피스 =====//
    var movieInfo:MovieInfo?,

    //===== 영화 상세 =====//
    var movieDetails:MovieDetails?,

    //===== TMDB 영화 상세 =====//
    var tmdbMovieResult:MovieResult?

)