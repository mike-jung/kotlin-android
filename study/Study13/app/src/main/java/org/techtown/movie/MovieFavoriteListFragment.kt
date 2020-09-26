package org.techtown.movie

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import kotlinx.android.synthetic.main.fragment_movie_list.view.*
import org.techtown.movie.data.MovieFavoriteList
import org.techtown.movie.data.MovieList

class MovieFavoriteListFragment : Fragment() {
    val TAG = "MovieFavoriteList"

    var callback: FragmentCallback? = null

    lateinit var adapter: MovieFavoriteListAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is FragmentCallback) {
            callback = context
        } else {
            Log.d(TAG, "Activity is not FragmentCallback.")
        }
    }

    override fun onDetach() {
        super.onDetach()

        if (callback != null) {
            callback = null
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_movie_favorite_list, container, false)

        // 리스트 표시
        showList(rootView as ViewGroup)

        // 즐겨찾기 데이터 로딩
        (activity as MainActivity).queryMovie()

        // 리스트의 데이터 보여주기
        println("리스트의 아이템 수 : ${adapter.itemCount}")
        var index = 0
        for (type in MovieFavoriteList.type) {
            println("#${index} -> ${type}")
            index += 1
        }

        adapter.notifyDataSetChanged()


        return rootView
    }

    // RecyclerView를 이용한 영화 목록 표시
    fun showList(rootView: ViewGroup) {

        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rootView.recyclerView.layoutManager = layoutManager

        adapter = MovieFavoriteListAdapter()

        rootView.recyclerView.adapter = adapter

        adapter.listener = object : OnMovieFavoriteListItemClickListener {
            override fun onItemClick(holder: MovieFavoriteListAdapter.ViewHolder?, view: View?, position: Int) {
                showToast("아이템 선택됨 : ${position}")

                if (callback != null) {
                    val bundle = Bundle()
                    bundle.putInt("index", position)
                    bundle.putString("listType", MovieFavoriteList.type[position])

                    callback!!.onFragmentSelected(FragmentCallback.FragmentItem.ITEM_FAVORITE_DETAILS, bundle)
                }
            }
        }

    }

    fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

}