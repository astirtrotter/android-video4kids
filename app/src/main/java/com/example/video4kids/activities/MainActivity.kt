package com.example.video4kids.activities

import android.app.ProgressDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.example.video4kids.R
import com.example.video4kids.activities.adapters.LearningAdapter
import com.example.video4kids.activities.interfaces.IMultiLangScreen
import com.example.video4kids.common.Pref
import com.example.video4kids.common.backend.BackendManager
import com.example.video4kids.common.backend.api.VideoItem
import com.example.video4kids.common.extensions.getMultiLangString
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.inside_common_toolbar.view.*
import rx.subscriptions.CompositeSubscription

class MainActivity : AppCompatActivity(), IMultiLangScreen {
    private var selectedNavItemId: Int = -1
        set(value) {
            if (field == value) return
            field = value
            configureMultiLangTitle()
            loadData()
        }

    private var items: ArrayList<VideoItem> = arrayListOf()
        set(value) {
            field = value
            updateRecyclerView()
        }
    private val progress: ProgressDialog by lazy { ProgressDialog(this).apply {
        setCancelable(false)
        setOnShowListener {
            window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setContentView(R.layout.progressdialog)
        }
    } }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
        }

        configureMultiLangViews()

        bottomNavigation.setOnNavigationItemSelectedListener { navItem ->
            selectedNavItemId = navItem.itemId
            true
        }
        selectedNavItemId = bottomNavigation.selectedItemId
    }

    override fun configureMultiLangViews() {}

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

    private fun loadData() {
        if (selectedNavItemId == R.id.nav_favorite) {
            items = ArrayList(Pref.favVideoItems)
            return
        }

        progress.show();

        CompositeSubscription().add(BackendManager.getVideos(Pref.path, learntoolbar.titletext.text.toString())
            .subscribe(
                { res ->
                    items = ArrayList(res.filter { item ->
                        !Pref.blockVideoIds.contains(item.video_id!!)
                    })
                },
                { e ->
                    progress.dismiss()
                    showAlert(e.message ?: "Something went wrong!")
                }
            ))
    }

    private fun updateRecyclerView() {
        progress.dismiss()

        if (items.size == 0 && selectedNavItemId != R.id.nav_favorite) {
            showAlert(getMultiLangString(R.string.no_data, R.string.perisan_no_data))
        }

        // adapter
        recyclerView.adapter = LearningAdapter(items)
    }

    private fun showAlert(msg: String) {
        AlertDialog.Builder(this)
            .setTitle("Alert")
            .setIcon(R.drawable.app_logo)
            .setCancelable(false)
            .setMessage(msg)
            .setPositiveButton("Ok") { dialoginterface, a ->
                dialoginterface.dismiss()
            }.show()
    }
}
