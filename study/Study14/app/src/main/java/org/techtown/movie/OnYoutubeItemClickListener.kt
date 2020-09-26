package org.techtown.movie

import android.view.View

interface OnYoutubeItemClickListener {

    fun onItemClick(holder: YoutubeMovieAdapter.ViewHolder?, view: View?, position: Int)

}