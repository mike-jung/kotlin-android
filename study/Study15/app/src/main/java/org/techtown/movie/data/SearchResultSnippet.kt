package org.techtown.movie.data

data class SearchResultSnippet(
    val publishedAt:String?,
    val title:String?,
    val description:String?,
    val thumbnails:Thumbnails
)