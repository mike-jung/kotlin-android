package org.techtown.fragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        onFragmentChanged(0)
    }

    fun onFragmentChanged(index: Int) {
        when (index) {
            0 -> supportFragmentManager.beginTransaction().replace(R.id.container, LoginFragment()).commit()
            1 -> supportFragmentManager.beginTransaction().replace(R.id.container, MenuFragment()).commit()
        }
    }

}