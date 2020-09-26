package org.techtown.movie

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), FragmentCallback {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.drawer_open,
                R.string.drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navigationView.setNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.item1 -> {
                    showToast("영화 목록 선택됨.")
                    onFragmentSelected(FragmentCallback.FragmentItem.ITEM_LIST, null)
                }
                R.id.item2 -> {
                    showToast("예매순 선택됨.")
                    onFragmentSelected(FragmentCallback.FragmentItem.ITEM2, null)
                }
                R.id.item3 -> {
                    showToast("영화관 선택됨.")
                    onFragmentSelected(FragmentCallback.FragmentItem.ITEM3, null)
                }
                R.id.item4 -> {
                    showToast("즐겨찾기 선택됨.")
                    onFragmentSelected(FragmentCallback.FragmentItem.ITEM4, null)
                }
            }

            drawerLayout.closeDrawer(GravityCompat.START)
            return@setNavigationItemSelectedListener true
        }


        bottom_navigation.selectedItemId = R.id.tab1
        bottom_navigation.setOnNavigationItemSelectedListener {

            when (it.itemId) {
                R.id.tab1 -> {
                    showToast("영화 목록 탭 선택됨")
                    onFragmentSelected(FragmentCallback.FragmentItem.ITEM_LIST, null)

                    return@setOnNavigationItemSelectedListener true
                }
                R.id.tab2 -> {
                    showToast("예매순 탭 선택됨")
                    onFragmentSelected(FragmentCallback.FragmentItem.ITEM2, null)

                    return@setOnNavigationItemSelectedListener true
                }
                R.id.tab3 -> {
                    showToast("영화관 탭 선택됨")
                    onFragmentSelected(FragmentCallback.FragmentItem.ITEM3, null)

                    return@setOnNavigationItemSelectedListener true
                }
                R.id.tab4 -> {
                    showToast("즐겨찾기 탭 선택됨")
                    onFragmentSelected(FragmentCallback.FragmentItem.ITEM4, null)

                    return@setOnNavigationItemSelectedListener true
                }
            }

            return@setOnNavigationItemSelectedListener false

        }

        // 첫번째 프래그먼트를 첫 화면으로 보여줌
        supportFragmentManager.beginTransaction().add(R.id.container, MovieListFragment()).commit()
    }

    override fun onFragmentSelected(item: FragmentCallback.FragmentItem, bundle: Bundle?) {
        var fragment: Fragment? = null
        when(item) {
            FragmentCallback.FragmentItem.ITEM_LIST -> {
                toolbar.title = "영화 목록"
                fragment = MovieListFragment()
            }
            FragmentCallback.FragmentItem.ITEM_DETAILS -> {
                toolbar.title = "영화 상세"
                fragment = MovieDetailsFragment()
            }
            FragmentCallback.FragmentItem.ITEM2 -> {
                toolbar.title = "예매순"
                fragment = Fragment2()
            }
            FragmentCallback.FragmentItem.ITEM3 -> {
                toolbar.title = "영화관"
                fragment = Fragment3()
            }
            FragmentCallback.FragmentItem.ITEM4 -> {
                toolbar.title = "즐겨찾기"
                fragment = Fragment4()
            }
        }

        supportFragmentManager.beginTransaction().replace(R.id.container, fragment!!).commit()
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    fun showToast(message:String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

}
