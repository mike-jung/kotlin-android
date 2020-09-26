package org.techtown.study08

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    enum class FragmentItem {
        ITEM1, ITEM2, ITEM3
    }

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

        // 바로가기 메뉴 화면 안에서 메뉴 선택했을 때
        navigationView.setNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.item1 -> {
                    showToast("첫번째 선택됨.")
                    onFragmentSelected(FragmentItem.ITEM1, null)
                }
                R.id.item2 -> {
                    showToast("두번째 선택됨.")
                    onFragmentSelected(FragmentItem.ITEM2, null)
                }
                R.id.item3 -> {
                    showToast("세번째 선택됨.")
                    onFragmentSelected(FragmentItem.ITEM3, null)
                }
            }

            drawerLayout.closeDrawer(GravityCompat.START)
            return@setNavigationItemSelectedListener true
        }

        // 하단 탭 버튼 선택했을 때
        bottom_navigation.setOnNavigationItemSelectedListener {

            when (it.itemId) {
                R.id.tab1 -> {
                    showToast("첫번째 탭 선택됨")
                    onFragmentSelected(FragmentItem.ITEM1, null)

                    return@setOnNavigationItemSelectedListener true
                }
                R.id.tab2 -> {
                    showToast("두번째 탭 선택됨")
                    onFragmentSelected(FragmentItem.ITEM2, null)

                    return@setOnNavigationItemSelectedListener true
                }
                R.id.tab3 -> {
                    showToast("세번째 탭 선택됨")
                    onFragmentSelected(FragmentItem.ITEM3, null)

                    return@setOnNavigationItemSelectedListener true
                }
            }

            return@setOnNavigationItemSelectedListener false

        }

        // 첫번째 탭을 선택된 상태로 만듬
        bottom_navigation.selectedItemId = R.id.tab1

        // 첫번째 프래그먼트를 선택된 프래그먼트로 보여줌
        supportFragmentManager.beginTransaction().add(R.id.container, Fragment1()).commit()
    }

    fun onFragmentSelected(item:FragmentItem, bundle: Bundle?) {
        var fragment: Fragment
        when(item) {
            FragmentItem.ITEM1 -> {
                toolbar.title = "첫번째 화면"
                fragment = Fragment1()
            }
            FragmentItem.ITEM2 -> {
                toolbar.title = "두번째 화면"
                fragment = Fragment2()
            }
            FragmentItem.ITEM3 -> {
                toolbar.title = "세번째 화면"
                fragment = Fragment3()
            }
        }

        supportFragmentManager.beginTransaction().replace(R.id.container, fragment).commit()
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    fun showToast(message:String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
    }

}
