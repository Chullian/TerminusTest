package com.chullian.terminustest.activity.registration

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chullian.terminustest.data.Session
import com.chullian.terminustest.data.repository.AuthRepository
import com.chullian.terminustest.utils.PROGRESS_BAR_GONE
import com.chullian.terminustest.utils.UiStates
import com.google.firebase.auth.FirebaseAuthException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationVM @Inject constructor(
    private val repository: AuthRepository,
    private val prefs: Session,
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegistrationUiState())
    val uiState = _uiState.asStateFlow()

    fun doRegister(email: String, pass: String, uri: Uri, name: String, bio: String) =
        viewModelScope.launch {
            try {
                repository.doRegister(email, pass, uri, name, bio).let {
                    prefs.isLoggedIn = true
                    _uiState.value = _uiState.value.copy(
                        progressBarState = PROGRESS_BAR_GONE,
                        uiStates = UiStates.Success("Registered")
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
