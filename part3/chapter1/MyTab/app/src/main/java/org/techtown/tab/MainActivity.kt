package org.techtown.tab

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        with(supportFragmentManager.beginTransaction()) {
            val fragment1 = Fragment1()
            replace(R.id.container, fragment1)
            commit()
        }

        bottom_navigation.setOnNavigationItemSelectedListener {

            when (it.itemId) {
                R.id.tab1 -> {
                    showToast("첫번째 탭 선택됨")

                    with(supportFragmentManager.beginTransaction()) {
                        val fragment1 = Fragment1()
                        replace(R.id.container, fragment1)
                        commit()
                    }

                    return@setOnNavigationItemSelectedListener true
                }
                R.id.tab2 -> {
                    showToast("두번째 탭 선택됨")

                    with(supportFragmentManager.beginTransaction()) {
                        val fragment2 = Fragment2()
                        replace(R.id.container, fragment2)
                        commit()
                    }

                    return@setOnNavigationItemSelectedListener true
                }
                R.id.tab3 -> {
                    showToast("세번째 탭 선택됨")

                    with(supportFragmentManager.beginTransaction()) {
                        val fragment3 = Fragment3()
                        replace(R.id.container, fragment3)
                        commit()
                    }

                    return@setOnNavigationItemSelectedListener true
                }
            }

            return@setOnNavigationItemSelectedListener false

        }

    }

    fun showToast(message:String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
    }

}