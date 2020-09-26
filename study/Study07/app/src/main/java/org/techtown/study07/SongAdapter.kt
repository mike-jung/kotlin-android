package org.techtown.study07

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.song.view.*

class SongAdapter : RecyclerView.Adapter<SongAdapter.ViewHolder>() {
    var items = ArrayList<Song>()

    lateinit var listener: OnSongItemClickListener

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.song, parent, false)

        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.setItem(item)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.setOnClickListener {
                listener?.onItemClick(this, itemView, adapterPosition)
            }
        }

        fun setItem(item: Song) {
            itemView.titleTextView.text = item.title
            itemView.singerTextView.text = item.singer
        }

    }

}