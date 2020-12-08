package org.techtown.receiver

import android.content.BroadcastReceiver
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager.CONNECTIVITY_ACTION
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var receiver: BroadcastReceiver? = null
    var connReceiver: BroadcastReceiver? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        configureReceiver()

        sendButton.setOnClickListener {
            val username = input1.text.toString()

            val intent = Intent()
            intent.action = "org.techtown.send"
            intent.putExtra("username", username)
            sendBroadcast(intent)
        }

    }

    fun configureReceiver() {
        val filter = IntentFilter()
        filter.addAction("org.techtown.send")
        receiver = MyReceiver()
        registerReceiver(receiver, filter)

        connReceiver = ConnectivityReceiver()
        registerReceiver(connReceiver, IntentFilter(CONNECTIVITY_ACTION))
    }

    override fun onDestroy() {
        super.onDestroy()

        unregisterReceiver(receiver)
        unregisterReceiver(connReceiver)
    }

}