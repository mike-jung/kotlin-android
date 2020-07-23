package org.techtown.database

import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val databaseName = "people"
    var database: SQLiteDatabase? = null

    val tableName = "person"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        doButton1.setOnClickListener {
            createDatabase()
        }

        doButton2.setOnClickListener {
            createTable()
        }

        doButton3.setOnClickListener {
            addData()
        }

        doButton4.setOnClickListener {
            queryData()
        }

    }

    fun createDatabase() {
        database = openOrCreateDatabase(databaseName, MODE_PRIVATE, null)
        output1.append("데이터베이스 생성 또는 오픈함\n")
    }

    fun createTable() {
        val sql = "create table if not exists ${tableName}" +
                "(_id integer PRIMARY KEY autoincrement, " +
                " name text, " +
                " age integer, " +
                " mobile text)"

        if (database == null) {
            output1.append("데이터베이스를 먼저 오픈하세요.\n")
            return
        }

        database?.execSQL(sql)
        output1.append("테이블 생성함\n")
    }

    fun addData() {
        val sql = "insert into ${tableName}(name, age, mobile) " +
                  " values " +
                  "('john', 20, '010-1000-1000')"

        if (database == null) {
            output1.append("데이터베이스를 먼저 오픈하세요.\n")
            return
        }

        database?.execSQL(sql)
        output1.append("데이터 추가함\n")
    }

    fun queryData() {
        val sql = "select _id, name, age, mobile from ${tableName}"

        if (database == null) {
            output1.append("데이터베이스를 먼저 오픈하세요.\n")
            return
        }

        val cursor = database?.rawQuery(sql, null)
        if (cursor != null) {
            for (index in 0 until cursor.count) {
                cursor.moveToNext()
                val id = cursor.getInt(0)
                val name = cursor.getString(1)
                val age = cursor.getInt(2)
                val mobile = cursor.getString(3)
                output1.append("레코드 #${index} : $id, $name, $age, $mobile\n")
            }
            cursor.close()
        }
        output1.append("데이터 조회함\n")
    }

}