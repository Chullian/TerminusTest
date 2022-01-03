package com.chullian.terminustest.activity.tweet

import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.chullian.terminustest.App
import com.chullian.terminustest.R
import com.chullian.terminustest.data.model.TweetModel
import com.chullian.terminustest.databinding.TweetItemBinding
import com.chullian.terminustest.utils.DAY_MILLIS
import com.chullian.terminustest.utils.HOUR_MILLIS
import com.chullian.terminustest.utils.MINUTE_MILLIS
import kotlin.properties.Delegates


class TweetAdapter(val onItemViewed: (tweet: TweetModel) -> Unit) :
    RecyclerView.Adapter<TweetAdapter.ViewHolder>() {

    var tweets: List<TweetModel> by Delegates.observable(emptyList()) { property, oldValue, newValue ->
        notifyChanges(oldValue, newValue)
    }

    class ViewHolder(val binding: TweetItemBinding) : RecyclerView.ViewHolder(binding.root) {


        fun getTimeAgo(time: Long): String? {
            var time = time
            val now: Long = System.currentTimeMillis()
            if (time > now || time <= 0) {
                return null
            }
            val diff = now - time
            return if (diff < MINUTE_MILLIS) {
                "just now"
            } else if (diff < 2 * MINUTE_MILLIS) {
                "a minute ago"
            } else if (diff < 50 * MINUTE_MILLIS) {
                "${diff / MINUTE_MILLIS} minutes ago"
            } else if (diff < 90 * MINUTE_MILLIS) {
                "an hour ago"
            } else if (diff < 24 * HOUR_MILLIS) {
                "${diff / HOUR_MILLIS} hours ago"
            } else if (diff < 48 * HOUR_MILLIS) {
                "yesterday"
            } else {
                "${diff / DAY_MILLIS} days ago"
            }
        }

        fun bind(tweet: TweetModel) {
            binding.apply {
                tweetAutherTv.text = tweet.author
                tweetMessageTv.text = tweet.tweet
                if (tweet.views.split(",").isNotEmpty()) {
                    tweetViewedByTv.visibility = VISIBLE
                    tweetViewedByTv.text = "${tweet.views.split(",").size}"
                } else {
                    tweetViewedByTv.visibility = GONE
                }
                tweetTimeTv.text = getTimeAgo(tweet.createdAt)
                App.instance?.imageLoader?.let {
                    tweetUserIv.load(tweet.authorImage, it) {
                        transformations(CircleCropTransformation())
                        placeholder(R.drawable.ic_person)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(TweetItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(tweets[position])
        onItemViewed(tweets[position])
    }

    override fun getItemCount() = tweets.size

    private fun notifyChanges(oldList: List<TweetModel>, newList: List<TweetModel>) {
        val diff = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return oldList[oldItemPosition].tweetId == newList[newItemPosition].tweetId
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return oldList[oldItemPosition] == newList[newItemPosition]
            }

            override fun getOldListSize() = oldList.size
            override fun getNewListSize() = newList.size
        })
        diff.dispatchUpdatesTo(this)
    }


}
