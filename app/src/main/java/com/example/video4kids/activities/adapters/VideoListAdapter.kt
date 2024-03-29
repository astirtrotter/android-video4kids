package com.example.video4kids.activities.adapters

import android.Manifest
import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.PorterDuff
import android.net.Uri
import android.os.Environment
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.video4kids.R
import com.example.video4kids.activities.MainActivity
import com.example.video4kids.activities.RelatedVideosActivity
import com.example.video4kids.activities.SingleVideoPlayActivity
import com.example.video4kids.common.Pref
import com.example.video4kids.common.backend.api.VideoItem
import com.example.video4kids.common.extensions.gotoAnotherActivity
import com.mcxiaoke.koi.ext.onClick
import com.pawegio.kandroid.hide
import com.pawegio.kandroid.show
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.adapter_learn.view.*
import java.io.File

class VideoListAdapter(private val activity: MainActivity,
                       private val items: ArrayList<VideoItem>) : RecyclerView.Adapter<VideoListAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_learn, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(viewHolder: MyViewHolder, i: Int) {
        val item = items[i]
        val itemView = viewHolder.itemView

        itemView.likeIV.setColorFilter(
            ContextCompat.getColor(itemView.context, R.color.red_color).takeIf { item.isFav } ?: Color.DKGRAY,
            PorterDuff.Mode.MULTIPLY)
        itemView.titleTV.text = item.title
        if (item.videoCount > 0) {
            itemView.notificationValue.show()
            var strCount = item.videoCount.toString()
            if (strCount.length == 1) strCount = " ${strCount} "
            itemView.notificationValue.text = strCount
            itemView.notificationValue.onClick {
                activity.gotoAnotherActivity<RelatedVideosActivity>(false,
                    Pair("videoTitle", activity.kind),
                    Pair("tagSlug", item.tagSlug))
            }
        } else {
            itemView.notificationValue.hide(true)
        }
        Glide.with(activity)
            .load("https://mywork.promoletter.com/storage/app/public/images/" + item.imagePath)
            .into(itemView.videoIV)
        itemView.videoIV.onClick {
            activity.gotoAnotherActivity<SingleVideoPlayActivity>(false,
                Pair("imagePath", item.imagePath),
                Pair("videoPath", item.videoPath))
        }

        itemView.blockIV.onClick {
            val block = {
                Pref.blockVideo(item.video_id!!)
                val index = items.indexOf(item)
                items.removeAt(index)
                notifyItemRemoved(index)
            }
            activity.requestPasscodeAndBlock(block)
        }

        itemView.shareIV.onClick {
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, "http://mywork.promoletter.com/public/video/" + item.title.replace(" ", "_"))
            }
            activity.startActivity(Intent.createChooser(intent, "Share with"))
        }

        itemView.likeIV.onClick {
            if (!item.isFav) {
                Pref.favoriteVideo(item)
            } else {
                Pref.unfavoriteVideo(item)
                if (activity.bottomNavigation.selectedItemId == R.id.nav_favorite) {
                    val index = items.indexOf(item)
                    items.removeAt(index)
                    notifyItemRemoved(index)
                    return@onClick
                }
            }
            itemView.likeIV.setColorFilter(
                ContextCompat.getColor(itemView.context, R.color.red_color).takeIf { item.isFav } ?: Color.DKGRAY,
                PorterDuff.Mode.MULTIPLY)
        }

        itemView.downloadIV.onClick {
            val download = {
                val downloadManager = activity.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
                val Download_Uri = Uri.parse("https://mywork.promoletter.com/storage/app/public/videos/${item.videoPath}")
                val builder = AlertDialog.Builder(activity)
                builder.setMessage(activity.resources.getText(R.string.areyousure))
                builder.setPositiveButton(
                    activity.resources.getText(R.string.yes) //end onClick
                ) { dialog, id ->
                    val sdImageMainDirectory: File
                    val root =
                        File(Environment.getExternalStorageDirectory().toString() + File.separator + "Video4Kids" + File.separator)
                    root.mkdirs()
                    sdImageMainDirectory = File(root, item.title + ".mp4")
                    if (sdImageMainDirectory.exists()) {
                        Toast.makeText(activity, activity.resources.getString(R.string.before), Toast.LENGTH_SHORT).show()
                    } else {
                        val request = DownloadManager.Request(Download_Uri)
                        //Setting title of request
                        val songname = item.title
                        request.setTitle(songname)
                        //Setting description of request
                        request.setDescription(" ")
                        request.setDestinationInExternalPublicDir("/Video4Kids", songname + ".mp4")
                        Toast.makeText(activity, activity.resources.getString(R.string.downloding), Toast.LENGTH_SHORT).show()
                        //Enqueue download and save the referenceId
                        downloadManager.enqueue(request)
                    }
                }
                builder.setNegativeButton(activity.resources.getText(R.string.no)) { dialog, id -> dialog.cancel() }
                builder.show()
            }

            // check write permission
            if (ContextCompat.checkSelfPermission(
                    activity,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                activity.requestPermissionAndDownload(download)
            } else {
                download()
            }
        }
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
}