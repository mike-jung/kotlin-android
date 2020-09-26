package org.techtown.movie.data

data class MovieSearchInfo(
    val movieCd:String?,        // 영화 코드
    val movieNm:String?,        // 영화명 (국문)
    val movieNmEn:String?,      // 영화명 (영문)
    val prdtYear:String?,       // 제작연도
    val openDt:String?,         // 영화 개봉일
    val typeNm:String?,         // 영화 유형
    val prdtStatNm:String?,     // 제작 상태
    val nationAlt:String?,      // 제작국가(전체)
    val genreAlt:String?,       // 영화장르(전체)
    val repNationNm:String?,    // 대표 제작국가명
    val repGenreNm:String?,     // 대표 장르명
    val directors:ArrayList<DirectorInfo> = ArrayList<DirectorInfo>(), // 배열 내 객체 안의 peopleNm : 감독명
    val companys:ArrayList<CompanyInfo> = ArrayList<CompanyInfo>() // 배열 내 객체 안의 peopleNm : 감독명
)