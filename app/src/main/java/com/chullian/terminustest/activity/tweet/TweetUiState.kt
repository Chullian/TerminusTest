package com.chullian.terminustest.activity.tweet

import com.chullian.terminustest.data.model.TweetModel
import com.chullian.terminustest.utils.PROGRESS_BAR_GONE
import com.chullian.terminustest.utils.UiStates

data class TweetUiState(
    val progressBarState:Int = PROGRESS_BAR_GONE,
    val uiStates: UiStates = UiStates.Idle,
    val tweetList:List<TweetModel> = emptyList(),
    val username:String="",
    val userBio:String="",
    val userImage:String=""
)
