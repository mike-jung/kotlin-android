package org.techtown.lifecycle

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        showToast(" onCreate 호출됨")

        button.setOnClickListener {
            finish()
        }

        saveButton.setOnClickListener {
            saveData()
        }

        loadButton.setOnClickListener {
            loadData()
        }
    }

    fun saveData() {
        val userName = input1.text.toString()

        val pref = getSharedPreferences("pref", Activity.MODE_PRIVATE)
        pref.edit().putString("userName", userName).commit()
    }

    fun loadData() {
        val pref = getSharedPreferences("pref", Activity.MODE_PRIVATE)
        val userName = pref.getString("userName", "")
        input1.setText(userName)
    }

    override fun onPause() {
        super.onPause()

        showToast(" onPause 호출됨")
    }

    override fun onResume() {
        super.onResume()

        showToast(" onResume 호출됨")
    }

    override fun onStart() {
        super.onStart()

        showToast(" onStart 호출됨")
    }

    override fun onStop() {
        super.onStop()

        showToast(" onStop 호출됨")
    }

    override fun onDestroy() {
        super.onDestroy()

        showToast(" onDestroy 호출됨")
    }

    fun showToast(message:String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
        textView.append(message)
    }

}