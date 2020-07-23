package org.techtown.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_menu.*

class MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        backButton.setOnClickListener {
            val intent = Intent()
            intent.putExtra("data2", "김하늘")
            setResult(Activity.RESULT_OK, intent)

            finish()
        }

        if (intent != null) {
            processIntent(intent)
        }

    }

    fun processIntent(data: Intent) {
        val data1 = data.getStringExtra("data1")
        if (data1 != null) {
            showToast("data1 : ${data1}")
        }
    }

    fun showToast(message:String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}