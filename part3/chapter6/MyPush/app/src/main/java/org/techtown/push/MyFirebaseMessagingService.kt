package org.techtown.push

import android.content.Context
import android.content.Intent
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)

        println("onNewToken 호출됨 : ${token}")
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        println("onMessageReceived 호출됨 : ${message}")

        println("수신 데이터 : ${message.from}, ${message.messageId}, ${message.data["title"]}, ${message.data["contents"]}")
        sendToActivity(message.from, message.data["title"], message.data["contents"])
    }

    fun sendToActivity(from:String?, title:String?, contents:String?) {
        val intent = Intent(applicationContext, MainActivity::class.java)
        intent.putExtra("from", from)
        intent.putExtra("title", title)
        intent.putExtra("contents", contents)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK + Intent.FLAG_ACTIVITY_SINGLE_TOP + Intent.FLAG_ACTIVITY_CLEAR_TOP)

        applicationContext.startActivity(intent)
    }

}
