package org.techtown.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class MyReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        println("onReceive 호출됨")

        val username = intent.extras?.getString("username")
        showToast(context, "전달받은 username : ${username}")
    }

    fun showToast(context:Context, message:String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

}
