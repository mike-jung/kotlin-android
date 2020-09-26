package org.techtown.movie

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlinx.android.synthetic.main.fragment_page.view.*
import org.techtown.movie.data.MovieData
import org.techtown.movie.data.MovieList
import org.techtown.movie.data.MovieReservation

class MovieReservationListAdapter : RecyclerView.Adapter<MovieReservationListAdapter.ViewHolder>() {

    lateinit var listener: OnMovieReservationItemClickListener

    override fun getItemCount() = MovieList.reservation.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.movie_list_item, parent, false)

        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = MovieList.reservation[position]
        holder.setItem(item)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.setOnClickListener {
                listener?.onItemClick(this, itemView, adapterPosition)
            }
        }

        fun setItem(item: MovieReservation) {
            val url = item.thumbnailUrl ?:""
            val title = item.title ?:""
            val details1 = item.score1 ?:""
            val details2 = item.reserve ?:""

            Glide.with(itemView.context)
                .load(url)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .dontAnimate()
                .into(itemView.posterImageView)

            itemView.titleTextView.text = title
            itemView.details1TextView.text = "네티즌 평점  ${details1}"
            itemView.details2TextView.text = "예매율  ${details2}%"
        }

    }

}