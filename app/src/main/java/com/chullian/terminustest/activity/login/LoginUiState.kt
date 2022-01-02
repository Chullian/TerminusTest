package com.chullian.terminustest.activity.login

import com.chullian.terminustest.utils.PROGRESS_BAR_GONE
import com.chullian.terminustest.utils.UiStates

data class LoginUiState(
    val progressBarState:Int = PROGRESS_BAR_GONE,
    val uiStates: UiStates = UiStates.Idle
)
