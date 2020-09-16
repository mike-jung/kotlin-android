package org.techtown.moviedb

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_reserved.*
import java.util.ArrayList

class ReservedActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reserved)

        processIntent(intent)

        closeButton.setOnClickListener {
            finish()
        }
    }

    fun processIntent(intent: Intent?) {
        val movies = intent?.getSerializableExtra("movies") as ArrayList<ReservedMovie>?
        val movie = movies?.get(0)
        if (movie != null) {
            posterImageView.setImageURI(Uri.parse(movie.poster_image))
            input1.setText(movie.name)
            input2.setText(movie.reserved_time)
            input3.setText(movie.director)
            input4.setText(movie.synopsis)
        }
    }

}