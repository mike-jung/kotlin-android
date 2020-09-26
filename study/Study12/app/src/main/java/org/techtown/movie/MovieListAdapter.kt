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

class MovieListAdapter : RecyclerView.Adapter<MovieListAdapter.ViewHolder>() {

    lateinit var listener: OnMovieListItemClickListener

    override fun getItemCount() = MovieList.data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.movie_list_item, parent, false)

        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = MovieList.data[position]
        holder.setItem(item)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.setOnClickListener {
                listener?.onItemClick(this, itemView, adapterPosition)
            }
        }

        fun setItem(item: MovieData) {
            val imageId = item.tmdbMovieResult?.poster_path ?:""
            val title = item.movieInfo?.movieNm ?:""
            val details1 = item.movieInfo?.audiCnt ?:""
            val details2 = item.movieDetails?.audits?.get(0)?.watchGradeNm ?:""

            if (imageId != null && imageId.isNotEmpty()) {
                val url = "http://image.tmdb.org/t/p/w200${imageId}"

                Glide.with(itemView.context)
                    .load(url)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .dontAnimate()
                    .into(itemView.posterImageView)
            }

            itemView.titleTextView.text = title
            itemView.details1TextView.text = Utils.addComma(details1?.toInt()) + "명"
            itemView.details2TextView.text = details2
        }

    }

}