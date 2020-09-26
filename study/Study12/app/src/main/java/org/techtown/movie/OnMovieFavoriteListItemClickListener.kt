package org.techtown.movie

import android.view.View

interface OnMovieFavoriteListItemClickListener {

    fun onItemClick(holder: MovieFavoriteListAdapter.ViewHolder?, view: View?, position: Int)

}