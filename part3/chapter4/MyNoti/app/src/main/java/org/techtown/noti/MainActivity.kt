package org.techtown.noti

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val channelId = "noti"
    val channelName = "noti"
    val description = "noti test"

    var notiManager:NotificationManagerCompat? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        notiManager = NotificationManagerCompat.from(applicationContext)
        createNotificationChannel(channelId, channelName, description);

        showButton.setOnClickListener {
            val resultIntent = Intent(applicationContext, MainActivity::class.java)
            showNotification("테스트", "테스트 내용입니다.", resultIntent)
        }
    }

    fun createNotificationChannel(id:String, name:String, description:String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_LOW
            val channel = NotificationChannel(id, name, importance)

            channel.description = description
            channel.enableLights(true)
            channel.lightColor = Color.RED
            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
            notiManager?.createNotificationChannel(channel)
        }
    }

    fun showNotification(title:String, contents: String, resultIntent: Intent) {
        val pendingIntent = PendingIntent.getActivity(applicationContext, 0, resultIntent, 0)

        val notification = NotificationCompat.Builder(this, channelId)
                .setContentTitle(title)
                .setContentText(contents)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setChannelId(channelId)
                .setContentIntent(pendingIntent)
                .build()

        notification.flags = Notification.FLAG_NO_CLEAR

        notiManager?.notify(101, notification)
    }

}