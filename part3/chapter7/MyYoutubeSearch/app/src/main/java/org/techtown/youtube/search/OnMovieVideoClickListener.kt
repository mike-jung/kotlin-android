package org.techtown.youtube.search

import android.view.View

interface OnMovieVideoClickListener {

    fun onItemClick(holder: MovieVideoAdapter.ViewHolder?, view: View?, position: Int)

}