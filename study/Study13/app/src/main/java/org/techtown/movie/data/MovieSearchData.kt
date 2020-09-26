package org.techtown.movie.data

data class MovieSearchData(

    //===== 영화 목록 (검색) =====//
    var movieInfo: MovieSearchInfo? = null,

    //===== 영화 상세 =====//
    var movieDetails: MovieDetails? = null,

    //===== TMDB 영화 상세 =====//
    var tmdbMovieResult: MovieResult? = null

)