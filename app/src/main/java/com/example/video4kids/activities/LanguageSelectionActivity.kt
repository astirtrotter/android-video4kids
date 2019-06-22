package com.example.video4kids.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.video4kids.R
import com.example.video4kids.common.Pref
import com.example.video4kids.common.extensions.gotoAnotherActivity
import com.mcxiaoke.koi.ext.onClick
import kotlinx.android.synthetic.main.activity_language_screen.*

class LanguageSelectionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_language_screen)

        val navigate = {
            if (Pref.isFirstLaunch) {
                gotoAnotherActivity<HelpActivity>()
            } else {
                gotoAnotherActivity<MainActivity>()
            }
        }
        langPersian.onClick {
            Pref.isEnglish = false
            navigate();
        }
        langEnglish.onClick {
            Pref.isEnglish = true
            navigate()
        }
    }
}
