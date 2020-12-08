package org.techtown.request

data class TmdbMovieDetails(
    val results:ArrayList<MovieResult> = ArrayList<MovieResult>()
)