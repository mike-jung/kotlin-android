package org.techtown.youtube

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.NonNull
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var player:YouTubePlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        playerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                println("onReady 호출됨")

                player = youTubePlayer
                startButton.isEnabled = true
            }
        })

        startButton.setOnClickListener {
            val videoId = input1.text.toString()
            player?.cueVideo(videoId, 0f)
            player?.play()
        }

        playerView.getPlayerUiController().setFullScreenButtonClickListener(View.OnClickListener {
            if (playerView.isFullScreen()) {
                playerView.exitFullScreen()
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE

                if (supportActionBar != null) {
                    supportActionBar!!.show()
                }
            } else {
                playerView.enterFullScreen()
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

                if (supportActionBar != null) {
                    supportActionBar!!.hide()
                }
            }
        })

    }
}