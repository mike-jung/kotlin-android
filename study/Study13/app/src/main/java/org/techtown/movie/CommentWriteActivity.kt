package org.techtown.movie

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.ServerValue
import kotlinx.android.synthetic.main.activity_comment_write.*

class CommentWriteActivity : AppCompatActivity() {
    var index:Int = 0
    var listType:String = ""

    var curTitle = ""
    var curGrade = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment_write)

        // 저장 버튼 클릭 시
        saveButton.setOnClickListener {
            val rating = gradeRatingBar.rating.toLong()
            val author = authorInput.text.toString().trim()
            val contents = contentsInput.text.toString().trim()

            saveComment(author, rating, contents)

            finish()
        }

        cancelButton.setOnClickListener {
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

    fun saveComment(author:String, rating:Long, contents:String) {
        val key: String? = AppInfo.databaseRef.child("comments").push().getKey()
        val comment = MovieComment(key!!, author, "", rating, contents, 0)
        val commentValues: HashMap<String, Any> = comment.toMap()
        commentValues["timestamp"] = ServerValue.TIMESTAMP

        val childUpdates: MutableMap<String, Any> = HashMap()
        childUpdates["/comments/$key"] = commentValues

        AppInfo.databaseRef.updateChildren(childUpdates)
    }

}