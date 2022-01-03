package com.chullian.terminustest.activity.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chullian.terminustest.data.persistance.Session
import com.chullian.terminustest.data.repository.auth.AuthRepository
import com.chullian.terminustest.data.repository.tweet.TweetRepository
import com.chullian.terminustest.utils.PROGRESS_BAR_GONE
import com.chullian.terminustest.utils.UiStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainVM @Inject constructor(
    private val authRepository: AuthRepository,
    private val tweetRepository: TweetRepository,
    private val prefs: Session
) : ViewModel() {

    private val _uiState = MutableStateFlow(MainUiState())
    val uiState = _uiState.asStateFlow()

    fun getUserDetails() = viewModelScope.launch {
        authRepository.getCurrentUserData()
    }

    init {
        viewModelScope.launch {
            tweetRepository.sync()
        }
    }

    fun isLoggedIn() =viewModelScope.launch{
        delay(1000)
        _uiState.value = _uiState.value.copy(
            progressBarState = PROGRESS_BAR_GONE,
            uiStates = UiStates.Success("isLoggedIn"),
            isLoggedIn = prefs.isLoggedIn
        )
    }
}
