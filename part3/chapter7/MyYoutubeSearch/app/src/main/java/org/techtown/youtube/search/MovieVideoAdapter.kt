package org.techtown.youtube.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlinx.android.synthetic.main.movie_video_item.view.*

class MovieVideoAdapter : RecyclerView.Adapter<MovieVideoAdapter.ViewHolder>() {
    var items = ArrayList<MovieVideo>()

    lateinit var listener: OnMovieVideoClickListener

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.movie_video_item, parent, false)

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

        fun setItem(item: MovieVideo) {
            itemView.dateTextView.text = item.date
            itemView.titleTextView.text = item.title
            itemView.contentsTextView.text = item.contents

            if (item.thumbnail != null && item.thumbnail.isNotEmpty()) {
                Glide.with(itemView.context)
                    .load(item.thumbnail)
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .dontAnimate()
                    .into(itemView.thumbnailImageView)
            }

        }

    }

}