package com.example.video4kids.activities

import android.app.Activity
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import com.example.video4kids.R
import com.example.video4kids.common.Pref
import com.example.video4kids.common.extensions.gotoAnotherActivity
import com.pawegio.kandroid.IntentFor
import com.pawegio.kandroid.start

class SplashActivity : AppCompatActivity() {

    private lateinit var player: MediaPlayer
    private var isPlayerReleased: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)

        if (savedInstanceState != null) return

        player = MediaPlayer.create(this, R.raw.music_info).apply {
            setOnCompletionListener {
                if (Pref.isFirstLaunch) {
                    gotoAnotherActivity<LanguageSelectionActivity>()
                } else {
                    gotoAnotherActivity<MainActivity>()
                }
                release()
                isPlayerReleased = true
            }
            start()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (!isPlayerReleased) {
            player.stop()
            player.release()
        }

    }
}
