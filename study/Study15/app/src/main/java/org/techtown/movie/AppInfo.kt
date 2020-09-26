package org.techtown.movie

import com.google.firebase.database.DatabaseReference

class AppInfo {

    companion object {

        // Firebase의 Realtime Database를 위한 객체
        lateinit var databaseRef: DatabaseReference

    }

}