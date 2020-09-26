package org.techtown.movie

import android.os.Bundle
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

class MovieListFragment : Fragment() {

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
        rootView.indicator.createIndicators(3,0);

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

        // 샘플 아이템 추가
        adapter.items.add(MovieListItem(R.drawable.poster1, "1. 결백", "관객수 312,745", "15세이상 관람가"))
        adapter.items.add(MovieListItem(R.drawable.poster2, "2. 침입자", "관객수 166,604", "15세이상 관람가"))
        adapter.items.add(MovieListItem(R.drawable.poster3, "3. 에어로너츠", "관객수 51,608", "12세이상 관람가"))

        rootView.recyclerView.adapter = adapter

        adapter.listener = object : OnMovieListItemClickListener {
            override fun onItemClick(holder: MovieListAdapter.ViewHolder?, view: View?, position: Int) {
                val name = adapter.items[position]
                showToast("아이템 선택됨 : $name")
            }
        }

    }

    fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    inner class PagerAdapter : FragmentStateAdapter {
        constructor(fm: FragmentManager, lc: Lifecycle) : super(fm, lc)

        override fun getItemCount() = 3

        override fun createFragment(position: Int): Fragment {

            // 샘플 아이템 추가
            var imageId:Int = 0
            var title:String = ""
            var details1:String = ""
            var details2:String = ""
            var fragment:PageFragment? = null

            when(position) {
                0 -> {
                    imageId = R.drawable.poster1
                    title = "${position+1}. 결백"
                    details1 = "관객수 312,745"
                    details2 = "15세이상 관람가"

                    fragment = PageFragment.newInstance(imageId, title, details1, details2)
                }
                1 -> {
                    imageId = R.drawable.poster2
                    title = "${position+1}. 침입자"
                    details1 = "관객수 166,604"
                    details2 = "15세이상 관람가"

                    fragment = PageFragment.newInstance(imageId, title, details1, details2)
                }
                2 -> {
                    imageId = R.drawable.poster3
                    title = "${position+1}. 에어로너츠"
                    details1 = "관객수 51,608"
                    details2 = "12세이상 관람가"

                    fragment = PageFragment.newInstance(imageId, title, details1, details2)
                }
                else -> {
                    fragment = PageFragment.newInstance(0, "", "", "")
                }
            }

            return fragment
        }

    }

}