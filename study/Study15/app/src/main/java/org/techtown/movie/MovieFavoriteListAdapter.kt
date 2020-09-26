package org.techtown.movie

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlinx.android.synthetic.main.fragment_page.view.*
import org.techtown.movie.data.MovieData
import org.techtown.movie.data.MovieFavoriteList
import org.techtown.movie.data.MovieList

class MovieFavoriteListAdapter : RecyclerView.Adapter<MovieFavoriteListAdapter.ViewHolder>() {

    lateinit var listener: OnMovieFavoriteListItemClickListener

    override fun getItemCount() = MovieFavoriteList.type.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        println("onCreateViewHolder called.")

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.movie_favorite_list_item, parent, false)

        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        println("onBindViewHolder called : ${position}")

        holder.setItem(position)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.setOnClickListener {
                listener?.onItemClick(this, itemView, adapterPosition)
            }
        }

        fun setItem(position: Int) {
            println("setItem called : ${position}, ${MovieFavoriteList.type[position]}")

            when (MovieFavoriteList.type[position]) {
                "boxOffice" -> {
                    // data 리스트에서 몇 번째 인덱스인지 확인
                    val index = Utils.getTypeIndex("boxOffice", position)

                    val item = MovieFavoriteList.data[index]

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
                "reservation" -> {
                    // reservation 리스트에서 몇 번째 인덱스인지 확인
                    val index = Utils.getTypeIndex("reservation", position)

                    val reservationItem = MovieFavoriteList.reservation[index]
                    val item = MovieFavoriteList.reservationData[index]

                    val imageId = item.tmdbMovieResult?.poster_path ?:""
                    val title = item.movieInfo?.movieNm ?:""
                    val details1 = "네티즌 평점  ${reservationItem.score1}"
                    val details2 = "예매율  ${reservationItem.reserve}%"

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
                    itemView.details1TextView.text = details1
                    itemView.details2TextView.text = details2
                }
            }

        }

    }

}