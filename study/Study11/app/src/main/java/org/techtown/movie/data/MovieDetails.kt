package org.techtown.movie.data

data class MovieDetails(
    val movieCd:String?,             // 영화 코드
    val movieNm:String?,             // 영화명 (국문)
    val movieNmEn:String?,           // 영화명 (영문)
    val prdtYear:String?,         // 제작연도
    val showTm:String?,         // 상영시간
    val openDt:String?,         // 개봉연도
    val typeNm:String?,         // 영화유형명
    val nations:ArrayList<NationInfo> = ArrayList<NationInfo>(),     // 배열 내 객체 안의 nationNm : 제작국가명
    val genres:ArrayList<GenreInfo> = ArrayList<GenreInfo>(),        // 배열 내 객체 안의 genreNm : 장르명
    val directors:ArrayList<DirectorInfo> = ArrayList<DirectorInfo>(), // 배열 내 객체 안의 peopleNm : 감독명
    val actors:ArrayList<ActorInfo> = ArrayList<ActorInfo>(),        // 배열 내 객체 안의 peopleNm : 배우명, cast : 역할명
    val audits:ArrayList<AuditInfo> = ArrayList<AuditInfo>(),        // 배열 내 객체 안의 watchGradeNm : 관람등급명
    val companys:ArrayList<CompanyInfo> = ArrayList<CompanyInfo>()  // 배열 내 객체 안의 companyNm : 제작사명
)