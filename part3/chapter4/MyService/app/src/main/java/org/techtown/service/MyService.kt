package org.techtown.service

import android.app.Service
import android.content.Intent
import android.os.IBinder

class MyService : Service() {

    override fun onCreate() {
        super.onCreate()

        println("onCreate 호출됨")
    }

    override fun onDestroy() {
        super.onDestroy()

        println("onDestroy 호출됨")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        println("onStartCommand 호출됨")

        intent?.apply { processIntent(intent) } ?: return Service.START_STICKY

        return super.onStartCommand(intent, flags, startId)
    }

    fun processIntent(intent:Intent) {
        intent.extras?.apply {
            val username = this.getString("username")
            println("전달된 username : ${username}")

            val showIntent = Intent(applicationContext, MainActivity::class.java)
            showIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK +
                                Intent.FLAG_ACTIVITY_SINGLE_TOP +
                                Intent.FLAG_ACTIVITY_CLEAR_TOP)
            showIntent.putExtra("command", "show")
            showIntent.putExtra("username", username)
            startActivity(showIntent);
        }
    }

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }
}
