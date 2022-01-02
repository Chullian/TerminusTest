package com.chullian.terminustest.activity.main

import androidx.lifecycle.ViewModel
import com.chullian.terminustest.data.Session
import com.chullian.terminustest.utils.PROGRESS_BAR_GONE
import com.chullian.terminustest.utils.UiStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MainVM @Inject constructor(
    private val prefs: Session
) : ViewModel() {

    private val _uiState = MutableStateFlow(MainUiState())
    val uiState = _uiState.asStateFlow()

    fun isLoggedIn() {
        _uiState.value = _uiState.value.copy(
            progressBarState = PROGRESS_BAR_GONE,
            uiStates = UiStates.Success("isLoggedIn"),
            isLoggedIn = prefs.isLoggedIn
        )
    }
}
