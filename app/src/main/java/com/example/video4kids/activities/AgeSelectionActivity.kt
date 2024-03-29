package com.example.video4kids.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.video4kids.R
import com.example.video4kids.activities.interfaces.IMultiLangScreen
import com.example.video4kids.common.Pref
import com.example.video4kids.common.extensions.getMultiLangString
import com.example.video4kids.common.extensions.gotoAnotherActivity
import com.mcxiaoke.koi.ext.onClick
import kotlinx.android.synthetic.main.activity_age_selection_screen.*

class AgeSelectionActivity : AppCompatActivity(), IMultiLangScreen {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_age_selection_screen)

        configureMultiLangViews()

        val navigate = {
            gotoAnotherActivity<MainActivity>()
        }
        kindChildren.onClick {
            Pref.isForAllAge = false
            navigate()
        }
        kindAll.onClick {
            Pref.isForAllAge = true
            navigate()
        }
    }

    override fun configureMultiLangViews() {
        txtDescription.text = getMultiLangString(R.string.range_children, R.string.perisan_range_children)
        txtChildren.text = getMultiLangString(R.string.children, R.string.perisan_children)
        txtAll.text = getMultiLangString(R.string.all, R.string.perisan_all)
    }
}
