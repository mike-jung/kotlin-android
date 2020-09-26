package org.techtown.movie

import android.view.View

interface OnMovieListItemClickListener {

    fun onItemClick(holder: MovieListAdapter.ViewHolder?, view: View?, position: Int)

}