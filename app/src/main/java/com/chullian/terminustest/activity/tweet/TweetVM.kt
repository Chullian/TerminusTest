package com.chullian.terminustest.activity.tweet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chullian.terminustest.activity.main.MainUiState
import com.chullian.terminustest.data.model.TweetModel
import com.chullian.terminustest.data.repository.tweet.TweetRepository
import com.chullian.terminustest.utils.PROGRESS_BAR_GONE
import com.chullian.terminustest.utils.UiStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TweetVM @Inject constructor(
    private val tweetRepository: TweetRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(TweetUiState())
    val uiState = _uiState.asStateFlow()

    fun sendTweet(tweet: String) = viewModelScope.launch {
        tweetRepository.sentTweet(tweet)
    }

    fun getTweets() = viewModelScope.launch {
        tweetRepository.retrieveTweets().collect {
            _uiState.value = _uiState.value.copy(
                progressBarState = PROGRESS_BAR_GONE,
                uiStates = UiStates.Success("tweets"),
                tweetList = it.sortedByDescending { item->item.createdAt }
            )
        }
    }

    fun updateTweetView(tweet: TweetModel) = viewModelScope.launch {
        tweetRepository.updateTweetView(tweet)
    }

}
