package org.techtown.push

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.iid.FirebaseInstanceId
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var regId:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FirebaseInstanceId.getInstance().instanceId.addOnCompleteListener {
            try {
                regId = it.result?.token
                output1.append("등록 ID : \n${regId}\n")
                Log.i("MainActivity", "등록 ID : \n${regId}\n")
            } catch(e: Exception) {
                e.printStackTrace()
            }
        }

        regButton.setOnClickListener {
            output1.append("버튼 클릭 시 확인한 등록 ID : \n${regId}\n")
        }

        processIntent(getIntent())
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        processIntent(intent)
    }

    fun processIntent(intent:Intent?) {
        val bundle = intent?.extras
        val from = bundle?.get("from")
        val title = bundle?.get("title")
        val contents = bundle?.get("contents")

        if (title != null) {
            output1.append("from : ${from}\n")
            output1.append("title : ${title}\n")
            output1.append("contents : ${contents}\n")
        }
    }

}