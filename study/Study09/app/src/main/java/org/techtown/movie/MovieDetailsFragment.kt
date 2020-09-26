package org.techtown.movie

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_movie_details.view.*

class MovieDetailsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_movie_details, container, false)

        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rootView.recyclerView.layoutManager = layoutManager

        val adapter = MovieCommentAdapter()

        adapter.items.add(MovieComment("sky2****", "1분전", "5.0", "정말 스릴넘치는 영화였어요. 한 번 더 보고 싶은 영화!!!", "5"))
        adapter.items.add(MovieComment("john****", "3분전", "4.5", "재미있어요.", "3"))
        adapter.items.add(MovieComment("acou****", "12분전", "4.8", "실화라고 생각하기에는 너무 영화같은...", "13"))

        rootView.recyclerView.adapter = adapter

        adapter.listener = object : OnMovieCommentClickListener {
            override fun onItemClick(holder: MovieCommentAdapter.ViewHolder?, view: View?, position: Int) {
                val name = adapter.items[position]
                showToast("아이템 선택됨 : $name")
            }
        }

        return rootView
    }

    fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

}