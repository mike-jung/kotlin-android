package org.techtown.movie

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.movie_comment_item.view.*

class MovieCommentAdapter : RecyclerView.Adapter<MovieCommentAdapter.ViewHolder>() {
    var items = ArrayList<MovieComment>()

    lateinit var listener: OnMovieCommentClickListener

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.movie_comment_item, parent, false)

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

        fun setItem(item: MovieComment) {
            itemView.idTextView.text = item.id
            itemView.dateTextView.text = item.date
            itemView.ratingBar.rating = item.rating?.toFloat()?:0.0f
            itemView.contentsTextView.text = item.contents
            itemView.recommendCountTextView.text = item.recommendCount
        }

    }

}