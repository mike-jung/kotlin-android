package org.techtown.recorder

import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.pedro.library.AutoPermissions
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File

class MainActivity : AppCompatActivity() {
    var recorder: MediaRecorder = MediaRecorder()
    var player: MediaPlayer = MediaPlayer()
    var filename:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        filename = externalCacheDir?.absolutePath + File.separator + "recording.mp4"

        startRecordingButton.setOnClickListener {
            startRecording()
            output1.append("녹음 시작함\n")
        }

        stopRecordingButton.setOnClickListener {
            recorder.stop()
            output1.append("녹음 중지함\n")
        }

        startPlayButton.setOnClickListener {
            startPlay()
            output1.append("재생 시작함\n")
        }

        stopPlayButton.setOnClickListener {
            player.stop()
            output1.append("재생 중지함\n")
        }

        AutoPermissions.Companion.loadAllPermissions(this, 101)
    }

    fun startRecording() {
        recorder.reset()

        recorder.setAudioSource(MediaRecorder.AudioSource.MIC)
        recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT)
        recorder.setOutputFile(filename)

        try {
            recorder.prepare()
            recorder.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun startPlay() {
        player.reset()

        player.setDataSource("file://$filename")

        try {
            player.prepare()
            player.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


}