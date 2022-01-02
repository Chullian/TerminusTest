package com.chullian.terminustest.activity.main

import com.chullian.terminustest.utils.PROGRESS_BAR_GONE
import com.chullian.terminustest.utils.UiStates

data class MainUiState(
    val progressBarState:Int = PROGRESS_BAR_GONE,
    val uiStates: UiStates = UiStates.Idle,
    val isLoggedIn:Boolean = false
) {

}
