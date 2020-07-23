package org.techtown.activity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        showButton.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            intent.putExtra("data1", "김준수")
            startActivityForResult(intent, 101)
        }

        callButton.setOnClickListener {
            val mobile = input1.text.toString()

            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("tel:${mobile}"))
            startActivity(intent)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            101 -> {
                showToast("메뉴 액티비티로부터의 응답, ${requestCode}, ${resultCode}")

                if (data != null) {
                    processIntent(data)
                }
            }
        }

    }

    fun processIntent(data: Intent) {
        val data2 = data.getStringExtra("data2")
        if (data2 != null) {
            showToast("data2 : ${data2}")
        }
    }

    fun showToast(message:String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}