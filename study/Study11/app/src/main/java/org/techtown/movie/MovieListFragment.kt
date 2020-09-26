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
import org.techtown.movie.data.MovieList

class MovieListFragment : Fragment() {
    val TAG = "MovieList"

    var callback: FragmentCallback? = null

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
        val rootView = inflater.inflate(R.layout.fragment_movie_list, container, false)

        // 칩 선택 시 토글되도록 함
        rootView.chip.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                rootView.chip2.isChecked = false

                rootView.viewContainer.visibility = View.VISIBLE
                rootView.viewContainer2.visibility = View.GONE

                showPager(rootView as ViewGroup)
            }
        }

        rootView.chip2.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                rootView.chip.isChecked = false

                rootView.viewContainer.visibility = View.GONE
                rootView.viewContainer2.visibility = View.VISIBLE

                showList(rootView as ViewGroup)
            }
        }

        showPager(rootView as ViewGroup)

        return rootView
    }

    // ViewPager를 이용한 영화 목록 표시
    fun showPager(rootView: ViewGroup) {

        val adapter = PagerAdapter(activity!!.supportFragmentManager, activity!!.lifecycle)
        rootView.pager.adapter = adapter
        rootView.pager.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        rootView.pager.clipToPadding = false
        rootView.pager.setPadding(150, 0, 150, 0)
        rootView.pager.offscreenPageLimit = 3

        rootView.indicator.setViewPager(rootView.pager);
        rootView.indicator.createIndicators(adapter.itemCount,0);

        rootView.pager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                println("${position+1} 페이지 선택됨")

                rootView.indicator.animatePageSelected(position)
            }
        })

    }

    // RecyclerView를 이용한 영화 목록 표시
    fun showList(rootView: ViewGroup) {

        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rootView.recyclerView.layoutManager = layoutManager

        val adapter = MovieListAdapter()

        rootView.recyclerView.adapter = adapter

        adapter.listener = object : OnMovieListItemClickListener {
            override fun onItemClick(holder: MovieListAdapter.ViewHolder?, view: View?, position: Int) {
                val movieData = MovieList.data[position]
                showToast("아이템 선택됨 : ${movieData.movieInfo?.movieNm}")

                if (callback != null) {
                    val bundle = Bundle()
                    bundle.putInt("index", position)
                    bundle.putString("listType", "boxOffice")

                    callback!!.onFragmentSelected(FragmentCallback.FragmentItem.ITEM_DETAILS, bundle)
                }
            }
        }

    }

    fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    inner class PagerAdapter : FragmentStateAdapter {
        constructor(fm: FragmentManager, lc: Lifecycle) : super(fm, lc)

        override fun getItemCount() = MovieList.data.size

        override fun createFragment(position: Int): Fragment {
            val movieData = MovieList.data[position]
            val imageId = movieData.tmdbMovieResult?.poster_path ?:""
            val title = movieData.movieInfo?.movieNm ?:""
            val details1 = movieData.movieInfo?.audiCnt ?:""
            val details2 = movieData.movieDetails?.audits?.get(0)?.watchGradeNm ?:""
            val fragment = PageFragment.newInstance(position, imageId, title, details1, details2)

            return fragment
        }

    }

}