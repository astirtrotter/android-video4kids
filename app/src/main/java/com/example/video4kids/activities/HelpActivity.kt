package com.example.video4kids.activities

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.video4kids.R
import com.example.video4kids.activities.interfaces.IMultiLangScreen
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
            gotoAnotherActivity<AgeSelectionActivity>()
        }
    }

    override fun configureMultiLangViews() {
        helpContent.text = getString(R.string.user_details, R.string.perisan_user);
        btnNext.text = getString(R.string.next, R.string.perisan_next);
        helpContact.text = getString(R.string.contact_us, R.string.perisan_contact_us);
        helpContactId.text = getString(R.string.deedani_ha, R.string.perisan_deedani_ha);
    }
}
