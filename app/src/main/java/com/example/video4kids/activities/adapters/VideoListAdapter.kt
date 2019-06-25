package com.example.video4kids.activities.adapters

import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.video4kids.R
import com.example.video4kids.activities.MainActivity
import com.example.video4kids.activities.RelatedVideosActivity
import com.example.video4kids.common.Pref
import com.example.video4kids.common.backend.api.VideoItem
import com.example.video4kids.common.extensions.gotoAnotherActivity
import com.mcxiaoke.koi.ext.onClick
import com.pawegio.kandroid.alert
import com.pawegio.kandroid.hide
import com.pawegio.kandroid.show
import kotlinx.android.synthetic.main.adapter_learn.view.*

class VideoListAdapter(private val activity: MainActivity,
                       private val items: ArrayList<VideoItem>) : RecyclerView.Adapter<VideoListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_learn, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.representData(items[i], activity)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun representData(item: VideoItem, activity: MainActivity) {
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
                activity.gotoAnotherActivity<RelatedVideosActivity>(false,
                    Pair("imagePath", item.imagePath))
            }

            itemView.blockIV.onClick {
                val block = {
                    Pref.blockVideo(item.video_id!!)
                }
                if (Pref.passcode == null) {
                    activity.createPasscode(block)
                } else {
                    activity.inputPasscode(block)
                }
            }

            itemView.shareIV.onClick {
                val intent = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, "http://mywork.promoletter.com/public/video/" + item.title.replace(" ", "_"))
                }
                activity.startActivity(Intent.createChooser(intent, "Share with"))
            }

            itemView.likeIV.onClick {
                if (item.isFav) {
                    Pref.favoriteVideo(item)
                } else {
                    Pref.unfavoriteVideo(item)
                }
                activity.loadData()
            }

            itemView.downloadIV.onClick {

            }
        }
    }
}