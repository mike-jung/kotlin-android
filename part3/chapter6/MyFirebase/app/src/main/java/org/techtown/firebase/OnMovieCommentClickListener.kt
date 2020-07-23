package org.techtown.firebase

import android.view.View

interface OnMovieCommentClickListener {

    fun onItemClick(holder: MovieCommentAdapter.ViewHolder?, view: View?, position: Int)

}