package com.example.video4kids.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import cn.jzvd.JZVideoPlayer
import cn.jzvd.JZVideoPlayerStandard
import com.bumptech.glide.Glide
import com.example.video4kids.R
import com.pawegio.kandroid.hide
import kotlinx.android.synthetic.main.activity_singlevideoplay.*

class SingleVideoPlayActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_singlevideoplay)

        val videoPath = intent.getStringExtra("videoPath")
        val imagePath = intent.getStringExtra("imagePath")

        videoplayer.setUp("https://mywork.promoletter.com/storage/app/public/videos/" + videoPath, JZVideoPlayerStandard.SCREEN_WINDOW_FULLSCREEN, "")
        Glide.with(this)
            .load("https://mywork.promoletter.com/storage/app/public/images/" + imagePath)
            .into(videoplayer.thumbImageView)
        videoplayer.apply {
            batteryLevel.hide()
            batteryTimeLayout.hide()
            startVideo()
        }
    }

    override fun onPause() {
        super.onPause()
        if (videoplayer.isCurrentPlay) JZVideoPlayerStandard.releaseAllVideos()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (videoplayer.isCurrentPlay) JZVideoPlayerStandard.releaseAllVideos()
    }

    override fun onBackPressed() {
        if (JZVideoPlayer.backPress()) return
        super.onBackPressed()
    }
}
