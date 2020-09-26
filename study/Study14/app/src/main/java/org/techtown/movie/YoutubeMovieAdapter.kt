package org.techtown.movie

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlinx.android.synthetic.main.youtube_item.view.*

class YoutubeMovieAdapter : RecyclerView.Adapter<YoutubeMovieAdapter.ViewHolder>() {
    var items = ArrayList<YoutubeItem>()

    lateinit var listener: OnYoutubeItemClickListener

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.youtube_item, parent, false)

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

        fun setItem(item: YoutubeItem) {
            itemView.titleTextView.text = item.title
            println("title : ${item.title}")

            if (item.thumbnail != null && item.thumbnail.isNotEmpty()) {
                Glide.with(itemView.context)
                    .load(item.thumbnail)
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .dontAnimate()
                    .into(itemView.thumbnailImageView)
            }
        }

    }

}