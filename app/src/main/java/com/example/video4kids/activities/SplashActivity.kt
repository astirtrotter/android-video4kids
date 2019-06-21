package com.example.video4kids.activities

import android.app.Activity
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import com.example.video4kids.R
import com.example.video4kids.common.Pref
import com.pawegio.kandroid.IntentFor
import com.pawegio.kandroid.start

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)

        if (savedInstanceState != null) return

        val navigate = {
            if (Pref.isFirstLaunch) {
                gotoActivity<LanguageSelectionActivity>()
            } else {
                gotoActivity<MainActivity>()
            }
            finish()
        }

        MediaPlayer.create(this, R.raw.music_info).apply {
            setOnCompletionListener { navigate() }
            start()
        }
    }

    private inline fun <reified T: Activity> gotoActivity() {
        val intent = IntentFor<T>(this)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK.or(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.start(this)
        finish()
    }
}
