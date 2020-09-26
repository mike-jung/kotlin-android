package org.techtown.study07

import android.view.View

interface OnSongItemClickListener {

    fun onItemClick(holder: SongAdapter.ViewHolder?, view: View?, position: Int)

}