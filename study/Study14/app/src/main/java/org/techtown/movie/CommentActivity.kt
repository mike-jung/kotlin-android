package org.techtown.movie

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_comment.*
import java.util.*
import kotlin.collections.HashMap

class CommentActivity : AppCompatActivity() {
    var index:Int = 0
    var listType:String = ""

    lateinit var adapter: MovieCommentAdapter

    var curTitle = ""
    var curGrade = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment)

        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        layoutManager.reverseLayout = true
        layoutManager.stackFromEnd = true
        commentRecyclerView.layoutManager = layoutManager

        adapter = MovieCommentAdapter()

        commentRecyclerView.adapter = adapter

        adapter.listener = object : OnMovieCommentClickListener {
            override fun onItemClick(holder: MovieCommentAdapter.ViewHolder?, view: View?, position: Int) {
                val name = adapter.items[position]
                println("아이템 선택됨 : $name")
            }
        }

        // 리스트에 파이어베이스의 한줄평 데이터 표시
        loadComment()


        // 작성하기 버튼 클릭 시
        writeButton.setOnClickListener {
            val activityIntent = Intent(this, CommentWriteActivity::class.java)
            activityIntent.putExtra("index", index)
            activityIntent.putExtra("listType", listType)
            activityIntent.putExtra("title", curTitle)
            activityIntent.putExtra("grade", curGrade)
            startActivityForResult(activityIntent, 101)
        }

        // 닫기 버튼 클릭 시
        closeButton.setOnClickListener {
            finish()
        }

        processIntent()
    }

    fun processIntent() {
        if (intent != null) {
            index = intent.getIntExtra("index", 0)
            listType = intent.getStringExtra("listType")
            curTitle = intent.getStringExtra("title")
            curGrade = intent.getIntExtra("grade", 0)
        }

        // 타이틀, 등급 표시
        movieTitleView.text = curTitle

        when(curGrade) {
            0 -> {
                ratingView.setImageResource(R.drawable.grade_all)
            }
            1 -> {
                ratingView.setImageResource(R.drawable.grade_12)
            }
            2 -> {
                ratingView.setImageResource(R.drawable.grade_15)
            }
            3 -> {
                ratingView.setImageResource(R.drawable.grade_19)
            }
        }

    }

    fun loadComment() {
        AppInfo.databaseRef = FirebaseDatabase.getInstance().reference

        AppInfo.databaseRef.orderByKey().limitToLast(10).addValueEventListener(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                loadCommentList(dataSnapshot)
            }
            override fun onCancelled(databaseError: DatabaseError) {
                println("loadItem:onCancelled : ${databaseError.toException()}")
            }
        })

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
                val curTime = Utils.convertCommentTime(curDate)
                var commentTime = ""
                if (curTime != null) {
                    commentTime = curTime
                }

                adapter.items.add(MovieComment(objectId, author, commentTime, rating, contents, recommendCount))
            }

            adapter.notifyDataSetChanged()
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 101) {
            // 리스트 업데이트
            adapter.notifyDataSetChanged()
            commentRecyclerView.smoothScrollToPosition(0)
        }

    }

}