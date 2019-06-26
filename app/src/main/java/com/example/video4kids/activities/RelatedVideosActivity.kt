package com.example.video4kids.activities

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.view.KeyEvent
import android.view.Window
import com.example.video4kids.R
import com.example.video4kids.activities.adapters.VideoThumbnailAdapter
import com.example.video4kids.activities.interfaces.IMultiLangScreen
import com.example.video4kids.common.Pref
import com.example.video4kids.common.backend.BackendManager
import com.example.video4kids.common.backend.api.VideoItem
import com.example.video4kids.common.extensions.getMultiLangString
import com.example.video4kids.common.extensions.gotoAnotherActivity
import com.mcxiaoke.koi.ext.hideSoftKeyboard
import com.mcxiaoke.koi.ext.onClick
import com.mcxiaoke.koi.ext.showSoftKeyboard
import com.pawegio.kandroid.IntentFor
import com.pawegio.kandroid.hide
import com.pawegio.kandroid.show
import kotlinx.android.synthetic.main.activity_related_videos_screen.learntoolbar
import kotlinx.android.synthetic.main.activity_related_videos_screen.recyclerView
import kotlinx.android.synthetic.main.inside_common_toolbar.*
import kotlinx.android.synthetic.main.inside_common_toolbar.view.*
import kotlinx.android.synthetic.main.settings_custom_dialog.*
import rx.subscriptions.CompositeSubscription

class RelatedVideosActivity : AppCompatActivity(), IMultiLangScreen {
    companion object {
        private val REQUEST_FOR_DOWNLOAD = 101
        private val REQUEST_FOR_BLOCK = 102
        private val REQUEST_FOR_CHANGEAGE = 103
        private lateinit var download: () -> Any
        private lateinit var block: () -> Any
        private lateinit var changeAge: () -> Any
    }

    private var videoTitle = ""
    private var tagSlug = ""

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

    private var search = ""
        set(value) {
            field = value.trim()
            updateRecyclerView()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_related_videos_screen)

        videoTitle = intent.getStringExtra("videoTitle")
        tagSlug = intent.getStringExtra("tagSlug")

        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@RelatedVideosActivity)
        }

        configureMultiLangViews()

        editSearch.setOnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                icRight.callOnClick()
                true
            }else {
                false
            }
        }
        icLeft.onClick {
            editSearch.show()
            editSearch.requestFocus()
            editSearch.showSoftKeyboard()
            icRight.setImageResource(R.drawable.icon_search)
        }
        icRight.onClick {
            if (editSearch.isShown) {
                // search
                editSearch.hideSoftKeyboard()
                editSearch.hide()
                icRight.setImageResource(R.drawable.setting_icon)
                search = editSearch.text.toString()
            } else {
                // setting
                val dialog = Dialog(this).apply {
                    requestWindowFeature(Window.FEATURE_NO_TITLE)
                    setCancelable(true)
                    setContentView(R.layout.settings_custom_dialog)
                    window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                    setOnShowListener {
                        btnChangeLang.text = getMultiLangString(R.string.change_language, R.string.perisan_change_language)
                        btnChangeAge.text = getMultiLangString(R.string.select_age_selection, R.string.perisan_select_age_selection)
                        btnChangePasscode.text = getMultiLangString(R.string.change_passcode_setting, R.string.perisan_change_passcode_setting)
                        btnContactUs.text = getMultiLangString(R.string.contact_us_setting, R.string.perisan_contact_us_setting)
                        btnLogout.text = getMultiLangString(R.string.logout, R.string.perisan_logout)

                        btnChangeLang.onClick { gotoAnotherActivity<LanguageSelectionActivity>() }
                        btnChangeAge.onClick {
                            changeAge = {
                                gotoAnotherActivity<AgeSelectionActivity>()
                            }
                            if (Pref.passcode == null) {
                                PasscodeActivity.initStep()
                                startActivityForResult(
                                    IntentFor<PasscodeActivity>(this@RelatedVideosActivity),
                                    REQUEST_FOR_CHANGEAGE
                                )
                            } else {
                                changeAge()
                            }
                        }
                        btnContactUs.onClick { gotoAnotherActivity<HelpActivity>(false) }
                        btnChangePasscode.onClick {
                            PasscodeActivity.initStep(true)
                            gotoAnotherActivity<PasscodeActivity>(false)
                        }
                        btnLogout.onClick {
                            Pref.clean()
                            finish()
                        }
                    }
                }
                dialog.show()
            }
        }

        loadData()
    }

    override fun configureMultiLangViews() {
        learntoolbar.titletext.text = videoTitle
    }

    fun loadData() {
        progress.show();

        CompositeSubscription().add(
            BackendManager.getVideosMore(tagSlug, "videos")
                .subscribe(
                    { res ->
                        items = ArrayList(res)
                    },
                    { e ->
                        progress.dismiss()
                        showAlert(e.message ?: "Something went wrong!")
                    }
                ))
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

    private fun updateRecyclerView() {
        if (progress.isShowing) progress.dismiss()

        val visibleItems = ArrayList(items.filter { item ->
            !(Pref.blockVideoIds.contains(item.video_id!!)) &&
                    (search.isEmpty() || item.title.contains(search))
        })

        if (visibleItems.size == 0) {
            showAlert(getMultiLangString(R.string.no_data, R.string.perisan_no_data))
        }

        // adapter
        recyclerView.adapter = VideoThumbnailAdapter(this, visibleItems)
    }

    fun requestPasscodeAndBlock(block: () -> Unit) {
        RelatedVideosActivity.block = block
        PasscodeActivity.initStep()
        startActivityForResult(IntentFor<PasscodeActivity>(this), REQUEST_FOR_BLOCK)
    }

    fun requestPermissionAndDownload(download: () -> AlertDialog) {
        RelatedVideosActivity.download = download
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), REQUEST_FOR_DOWNLOAD)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            REQUEST_FOR_BLOCK -> {
                if (resultCode == Activity.RESULT_OK) {
                    block()
                }
            }
            REQUEST_FOR_CHANGEAGE -> {
                if (resultCode == Activity.RESULT_OK) {
                    changeAge()
                }
            }
            else -> super.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_FOR_DOWNLOAD -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    download()
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        shouldShowRequestPermissionRationale(permissions[0])
                    }
                }
            }
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }
}
