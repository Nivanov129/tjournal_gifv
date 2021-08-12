package com.nick.lentagifv

import android.content.res.ColorStateList
import android.graphics.Color
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.isVisible
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.google.android.material.imageview.ShapeableImageView
import com.nick.lentagifv.models.FeedResponse
import com.nick.lentagifv.utils.humanDate


class RemoteFeedAdapter() :
    PagingDataAdapter<FeedResponse.Result.Item, RecyclerView.ViewHolder>(REPO_COMPARATOR) {

    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<FeedResponse.Result.Item>() {
            override fun areItemsTheSame(
                oldItem: FeedResponse.Result.Item,
                newItem: FeedResponse.Result.Item
            ): Boolean =
                oldItem.data?.id == newItem.data?.id

            override fun areContentsTheSame(
                oldItem: FeedResponse.Result.Item,
                newItem: FeedResponse.Result.Item
            ): Boolean =
                oldItem.data.toString() == newItem.data.toString()
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        getItem(position)?.let {
            (holder as? FeedItemViewHolder)?.bind(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_feed, parent, false)
        return FeedItemViewHolder(view)
    }



    inner class FeedItemViewHolder(private val view: View) :
        RecyclerView.ViewHolder(view) {

        var item: FeedResponse.Result.Item? = null

        val textTitle: TextView = view.findViewById(R.id.post_title)
        val textPost: TextView = view.findViewById(R.id.post_text)
        val textSubsection: TextView = view.findViewById(R.id.subsection)
        val textUserName: TextView = view.findViewById(R.id.user_name)
        val textDate: TextView = view.findViewById(R.id.date_time)
        val textRate: TextView = view.findViewById(R.id.rate)
        val textCommentCount: TextView = view.findViewById(R.id.comment_count)

        val userAvatar: ShapeableImageView = view.findViewById(R.id.user_avatar)

        val thumbnail: ImageView = view.findViewById(R.id.thumbnail)
        val mediaContentView: FrameLayout = view.findViewById(R.id.media_container)
        private val parent: View = itemView
        val rootView: ConstraintLayout = view.findViewById(R.id.root)

        init {
            textTitle.text = ""
            textTitle.isVisible = false
            textSubsection.text = ""
            textUserName.text = ""
            textPost.text = ""
            textDate.text = ""
            textRate.text = ""
            textCommentCount.text = ""
            textPost.isVisible = false
            userAvatar.setImageResource(0)
            thumbnail.setImageResource(0)
            val set = ConstraintSet()
            set.clone(rootView)
            set.setDimensionRatio(mediaContentView.id, "16:9")
            set.applyTo(rootView)
        }

        fun bind(item: FeedResponse.Result.Item?) {
            this.item = item
            parent.tag = this

            item?.data?.title?.let {
                if (it.isNotEmpty()) {
                    textTitle.text = it
                    textTitle.isVisible = true
                }

            }
            textSubsection.text = item?.data?.subsite?.name.toString()
            textUserName.text = item?.data?.author?.name.toString()
            textDate.text = item?.data?.date.humanDate()
            textCommentCount.text = (item?.data?.commentsSeenCount ?: 0).toString()
            textRate.text = (item?.data?.counters?.favorites ?: 0).toString()
            item?.data?.blocks?.findLast { it.type == "text" }?.data?.text?.let {
                if (it.isNotEmpty()) {
                    textPost.text = it
                    textPost.isVisible = true
                }
            }

            userAvatar.load("https://leonardo.osnova.io/${item?.data?.author?.avatar?.data?.uuid}/-/format/jpg/")

            item?.data?.author?.avatar?.data?.color?.let {
                if (it.isEmpty()) return
                val colorInt = Color.parseColor("#$it")
                val csl = ColorStateList.valueOf(colorInt)
                userAvatar.strokeColor = csl
            }


            var height = 9L
            var width = 16L

            item?.data?.blocks?.findLast { it.type == "media" }?.data?.items?.get(0)?.image?.data?.let {
                val mediaId = it.uuid

                thumbnail.load("https://leonardo.osnova.io/${mediaId}/-/format/jpg/")

                width = it.width
                height = it.height
            }

            item?.data?.blocks?.findLast { it.type == "video" }?.data?.video?.let {

                val mediaId = it.data.thumbnail.data.uuid

                thumbnail.load("https://leonardo.osnova.io/${mediaId}/-/format/jpg/")

                width = it.data.thumbnail.data.width
                height = it.data.thumbnail.data.height

            }
            if (width/height < 0.8){
                width = 4
                height = 3
            }
            val set = ConstraintSet()
            set.clone(rootView)
            set.setDimensionRatio(mediaContentView.id, "$width:$height")
            set.applyTo(rootView)
        }


    }


}

