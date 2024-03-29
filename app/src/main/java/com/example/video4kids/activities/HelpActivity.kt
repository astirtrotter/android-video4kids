package com.example.video4kids.activities

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.video4kids.R
import com.example.video4kids.activities.interfaces.IMultiLangScreen
import com.example.video4kids.common.Pref
import com.example.video4kids.common.extensions.getMultiLangString
import com.example.video4kids.common.extensions.gotoAnotherActivity
import com.mcxiaoke.koi.ext.onClick
import kotlinx.android.synthetic.main.activity_help_screen.*

class HelpActivity : AppCompatActivity(), IMultiLangScreen {

    @SuppressLint("StringFormatInvalid")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help_screen)

        configureMultiLangViews()

        btnNext.onClick {
            if (Pref.isFirstLaunch) {
                gotoAnotherActivity<AgeSelectionActivity>()
            } else {
                finish()
            }
        }
    }

    override fun configureMultiLangViews() {
        helpContent.text = getMultiLangString(R.string.user_details, R.string.perisan_user);
        if (Pref.isFirstLaunch) {
            btnNext.text = getMultiLangString(R.string.next, R.string.perisan_next);
        } else {
            btnNext.text = getMultiLangString(R.string.ok, R.string.perisan_ok);
        }
        helpContact.text = getMultiLangString(R.string.contact_us, R.string.perisan_contact_us);
        helpContactId.text = getMultiLangString(R.string.deedani_ha, R.string.perisan_deedani_ha);
    }
}
