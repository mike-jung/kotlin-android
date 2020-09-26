package org.techtown.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import kotlinx.android.synthetic.main.fragment_movie_list.view.*

class MovieListFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_movie_list, container, false)

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

        return rootView
    }

    inner class PagerAdapter : FragmentStateAdapter {
        constructor(fm: FragmentManager, lc: Lifecycle) : super(fm, lc)

        override fun getItemCount() = 3

        override fun createFragment(position: Int): Fragment {
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