package org.techtown.request

data class BoxOfficeResult(
    val dailyBoxOfficeList:ArrayList<MovieInfo> = ArrayList<MovieInfo>()
)