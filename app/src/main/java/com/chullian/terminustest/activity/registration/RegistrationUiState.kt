package com.chullian.terminustest.activity.registration

import com.chullian.terminustest.utils.PROGRESS_BAR_GONE
import com.chullian.terminustest.utils.UiStates

data class RegistrationUiState(
    val progressBarState: Int = PROGRESS_BAR_GONE,
    val uiStates: UiStates = UiStates.Idle
)
