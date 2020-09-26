package org.techtown.movie

data class MovieFavoriteListItem(
    val imageId: Int = 0,
    val title: String?,
    val details1: String?,
    val details2: String?,
    val imageUrl: String? = null
)