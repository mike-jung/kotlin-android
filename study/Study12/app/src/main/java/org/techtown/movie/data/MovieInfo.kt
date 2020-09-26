package org.techtown.movie.data

data class MovieInfo(
    val rank:String?,           // 순위
    val rankInten:String?,      // 전일대비 순위 증감분
    val rankOldAndNew:String?,  // 랭킹 신규진입여부 (“OLD” : 기존, “NEW” : 신규)
    val movieCd:String?,        // 영화 코드
    val movieNm:String?,        // 영화명 (국문)
    val openDt:String?,         // 영화 개봉일
    val audiCnt:String?,        // 해당일의 관객수
    val audiAcc:String?,        // 누적 관객수
    val scrnCnt:String?,        // 해당일의 상영 스크린수
    val showCnt:String?         // 해당일의 상영 횟수
)