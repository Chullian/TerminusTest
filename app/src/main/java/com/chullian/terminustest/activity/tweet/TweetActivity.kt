package com.chullian.terminustest.activity.tweet

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.chullian.terminustest.databinding.ActivityTweetBinding
import com.chullian.terminustest.utils.UiStates
import com.chullian.terminustest.utils.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class TweetActivity : AppCompatActivity() {
    lateinit var binding: ActivityTweetBinding
    val tweetVM: TweetVM by viewModels()

    private val adapter = TweetAdapter{tweet->
        tweetVM.updateTweetView(tweet)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTweetBinding.inflate(layoutInflater)
        setContentView(binding.root)
        tweetVM.getTweets()
        initViews()
        observeUiChanges()
    }

    private fun observeUiChanges() = lifecycleScope.launchWhenCreated {
        tweetVM.uiState.collect {
            progress(it.progressBarState)
            binding.tweetRefreshSwipeRv.isRefreshing = false
            when (it.uiStates) {
                UiStates.Idle -> {}
                is UiStates.Error -> {}
                is UiStates.Success -> {
                    when (it.uiStates.message) {
                        "tweets" -> {
                            binding.tweetRefreshSwipeRv.isRefreshing = false
                            adapter.tweets = it.tweetList
                        }
                    }
                }
            }
        }
    }

    private fun progress(progressBarState: Int) {

    }


    private fun initViews() {
        binding.apply {
            tweetRecyclerView.apply {
                layoutManager = LinearLayoutManager(this@TweetActivity)
                adapter = this@TweetActivity.adapter
                addItemDecoration(
                    DividerItemDecoration(
                        this@TweetActivity,
                        LinearLayoutManager.VERTICAL
                    )
                )
            }
            tweetSentIv.setOnClickListener {
                if (tweetEt.text.toString().trim().isNotEmpty()) {
                    tweetVM.sendTweet(tweetEt.text.toString())
                    tweetEt.setText("")
                    hideKeyboard()
                }
            }
            tweetRefreshSwipeRv.setOnRefreshListener {
                tweetVM.getTweets()
            }
        }
    }
}


