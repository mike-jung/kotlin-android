package org.techtown.firebase

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

class MainActivity : AppCompatActivity() {

    private lateinit var databaseRef: DatabaseReference
    private lateinit var adapter:MovieCommentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()

        saveButton.setOnClickListener {
            val author = input1.text.toString()
            val rating = ratingBar.rating.toLong()
            val contents = input2.text.toString()
            val recommendCount = 0L

            saveComment(author, rating, contents, recommendCount)
        }

        databaseRef = FirebaseDatabase.getInstance().reference

        databaseRef.orderByKey().limitToLast(10).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                loadCommentList(dataSnapshot)
            }
            override fun onCancelled(databaseError: DatabaseError) {
                println("loadItem:onCancelled : ${databaseError.toException()}")
            }
        })

    }

    fun initView() {

        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        layoutManager.setReverseLayout(true)
        layoutManager.setStackFromEnd(true)
        recyclerView.layoutManager = layoutManager

        adapter = MovieCommentAdapter()

        //adapter.items.add(MovieComment("0", "sky2****", "1분전", 5.0f, "정말 스릴넘치는 영화였어요. 한 번 더 보고 싶은 영화!!!", 5))
        //adapter.items.add(MovieComment("1", "john****", "3분전", 4.5f, "재미있어요.", 3))
        //adapter.items.add(MovieComment("2", "acou****", "12분전", 4.8f, "실화라고 생각하기에는 너무 영화같은...", 13))

        recyclerView.adapter = adapter

        adapter.listener = object : OnMovieCommentClickListener {
            override fun onItemClick(holder: MovieCommentAdapter.ViewHolder?, view: View?, position: Int) {
                val name = adapter.items[position]
                showToast("아이템 선택됨 : $name")
            }
        }

    }

    fun saveComment(author:String, rating:Long, contents:String, recommendCount:Long) {
        val key: String? = databaseRef.child("comments").push().getKey()
        val comment = MovieComment(key!!, author, "", rating, contents, recommendCount)
        val commentValues: HashMap<String, Any> = comment.toMap()
        commentValues["timestamp"] = ServerValue.TIMESTAMP

        val childUpdates: MutableMap<String, Any> = HashMap()
        childUpdates["/comments/$key"] = commentValues

        databaseRef.updateChildren(childUpdates)
    }

    fun loadCommentList(dataSnapshot: DataSnapshot) {
        val collectionIterator = dataSnapshot.children.iterator()
        if (collectionIterator.hasNext()) {
            adapter.items.clear()

            val comments = collectionIterator.next()
            val itemsIterator = comments.children.iterator()
            while (itemsIterator.hasNext()) {
                val currentItem = itemsIterator.next()

                val key = currentItem.key

                val map = currentItem.getValue() as HashMap<String, Any>
                val objectId = map.get("objectId") as String
                val author = map.get("author") as String
                val rating = map.get("rating") as Long
                val contents = map.get("contents") as String
                val recommendCount = map.get("recommendCount") as Long

                val curDate = Date(map.get("timestamp") as Long)
                val curTime = convertCommentTime(curDate)
                var commentTime = ""
                if (curTime != null) {
                    commentTime = curTime
                }

                adapter.items.add(MovieComment(objectId, author, commentTime, rating, contents, recommendCount))
            }

            adapter.notifyDataSetChanged()
        }
    }

    fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }


    // 자리수 추가
    fun addComma(value: Int): String? {
        val df = DecimalFormat("#,###")
        return df.format(value.toLong())
    }

    var inputDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    var outputDateFormat = SimpleDateFormat("yyyy.MM.dd")

    // 날짜 변환 : yyyy-MM-dd HH:mm:ss --> ~분전
    fun convertCommentTime(inputDateString: String?): String? {
        return convertCommentTime(inputDateString)
    }

    fun convertCommentTime(inputDate: Date?): String? {
        var outputDateString = ""
        try {
            if (inputDate != null) {
                val curDate = Date()
                val curDateMillis = curDate.time
                val inputDateMillis = inputDate.time
                val diffMillis = curDateMillis - inputDateMillis
                println("diffMillis : $diffMillis")

                var diffSeconds = diffMillis / 1000
                var diffMinutes: Long = 0
                if (diffSeconds > 60) {
                    diffMinutes = diffSeconds / 60
                    diffSeconds = diffSeconds % 60
                }

                var diffHours: Long = 0
                if (diffMinutes > 60) {
                    diffHours = diffMinutes / 60
                    diffMinutes = diffMinutes % 60
                }

                var diffDays: Long = 0
                if (diffHours > 24) {
                    diffDays = diffHours / 24
                    diffHours = diffHours % 24
                }

                println("diffDays : $diffDays, diffHours : $diffHours, diffMinutes : $diffMinutes, diffSeconds : $diffSeconds")
                if (diffDays > 30) {
                    outputDateString = outputDateFormat.format(inputDate)
                } else {
                    if (diffDays > 0) {
                        outputDateString = diffDays.toString() + "일전"
                    } else if (diffHours > 0) {
                        outputDateString = diffHours.toString() + "시간전"
                    } else if (diffMinutes > 0) {
                        outputDateString = diffMinutes.toString() + "분전"
                    } else if (diffSeconds > 0) {
                        outputDateString = diffSeconds.toString() + "초전"
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return outputDateString
    }


}