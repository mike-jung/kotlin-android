package org.techtown.movie

import android.view.View

interface OnMovieReservationItemClickListener {

    fun onItemClick(holder: MovieReservationListAdapter.ViewHolder?, view: View?, position: Int)

}