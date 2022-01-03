package com.chullian.terminustest.activity.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chullian.terminustest.data.persistance.Session
import com.chullian.terminustest.data.repository.auth.AuthRepository
import com.chullian.terminustest.utils.PROGRESS_BAR_GONE
import com.chullian.terminustest.utils.PROGRESS_BAR_VISIBLE
import com.chullian.terminustest.utils.UiStates
import com.google.firebase.auth.FirebaseAuthException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginVM @Inject constructor(
    private val repository: AuthRepository,
    private val prefs: Session
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()


    fun doLogin(email: String, pass: String) = viewModelScope.launch {
        _uiState.value = _uiState.value.copy(
            progressBarState = PROGRESS_BAR_VISIBLE,
            uiStates = UiStates.Idle
        )
        try {
            repository.authenticate(email, pass)?.let {
                prefs.isLoggedIn = true
                _uiState.value = _uiState.value.copy(
                    progressBarState = PROGRESS_BAR_GONE,
                    uiStates = UiStates.Success("Logged In")
                )

            } ?: run {
                _uiState.value = _uiState.value.copy(
                    progressBarState = PROGRESS_BAR_GONE,
                    uiStates = UiStates.Error("Unknown Error")
                )
            }
        } catch (e: FirebaseAuthException) {
            _uiState.value = _uiState.value.copy(
                progressBarState = PROGRESS_BAR_GONE,
                uiStates = UiStates.Error(e.message.toString())
            )
        }
    }

}
