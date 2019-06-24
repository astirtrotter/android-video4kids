package com.example.video4kids.activities.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.video4kids.R
import com.example.video4kids.common.backend.api.VideoItem

class LearningAdapter(private val items: ArrayList<VideoItem>) : RecyclerView.Adapter<LearningAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_learn, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.representData(items[i])
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun representData(item: VideoItem) {

        }
    }
}