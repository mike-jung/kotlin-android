package org.techtown.movie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import kotlinx.android.synthetic.main.activity_youtube.*

class YoutubeActivity : AppCompatActivity() {
    var player: YouTubePlayer? = null
    var videoId: String = ""
    var videoTitle: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_youtube)

        processIntent()

        playerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                println("onReady 호출됨")

                player = youTubePlayer

                player?.cueVideo(videoId, 0f)
                player?.play()
            }
        })

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

    fun processIntent() {
        if (intent != null) {
            videoId = intent.getStringExtra("videoId")
            videoTitle = intent.getStringExtra("videoTitle")

            title = videoTitle
        }
    }

}