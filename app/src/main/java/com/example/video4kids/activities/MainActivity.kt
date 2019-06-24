package com.example.video4kids.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.video4kids.R
import com.example.video4kids.activities.interfaces.IMultiLangScreen
import com.example.video4kids.common.extensions.getMultiLangString
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.inside_common_toolbar.view.*

class MainActivity : AppCompatActivity(), IMultiLangScreen {
    private var selectedNavItemId: Int = -1
        set(value) {
            if (field == value) return
            field = value
            configureMultiLangTitle()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        configureMultiLangViews()

        bottomNavigation.setOnNavigationItemSelectedListener { navItem ->
            selectedNavItemId = navItem.itemId

            true
        }
        selectedNavItemId = bottomNavigation.selectedItemId
    }

    override fun configureMultiLangViews() {

    }

    private fun configureMultiLangTitle() {
        val title = when (selectedNavItemId) {
            R.id.nav_video -> getMultiLangString(R.string.video, R.string.perisan_video)
            R.id.nav_music -> getMultiLangString(R.string.musics, R.string.perisan_musics)
            R.id.nav_learn -> getMultiLangString(R.string.learns, R.string.perisan_learns)
            R.id.nav_favorite -> getMultiLangString(R.string.favorites, R.string.perisan_favorites)
            R.id.nav_explore -> getMultiLangString(R.string.explores, R.string.perisan_explores)
            else -> throw IllegalStateException()
        }
        learntoolbar.titletext.text = title
    }
}
