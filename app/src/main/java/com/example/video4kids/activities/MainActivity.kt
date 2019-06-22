package com.example.video4kids.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.video4kids.R
import com.example.video4kids.activities.interfaces.IMultiLangScreen

class MainActivity : AppCompatActivity(), IMultiLangScreen {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        configureMultiLangViews()
    }

    override fun configureMultiLangViews() {

    }
}
