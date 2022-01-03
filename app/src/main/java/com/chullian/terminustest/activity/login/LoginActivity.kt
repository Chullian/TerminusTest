package com.chullian.terminustest.activity.login

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.chullian.terminustest.activity.main.MainActivity
import com.chullian.terminustest.activity.registration.RegistrationActivity
import com.chullian.terminustest.activity.tweet.TweetActivity
import com.chullian.terminustest.databinding.ActivityLoginBinding
import com.chullian.terminustest.utils.PROGRESS_BAR_GONE
import com.chullian.terminustest.utils.PROGRESS_BAR_VISIBLE
import com.chullian.terminustest.utils.UiStates
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

/**
 * Created by binMammootty on 02/01/2022.
 */
@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val loginVm: LoginVM by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
        observeUiChanges()
    }

    private fun initViews() {
        binding.apply {
            loginRegisterTxt.apply {
                val spannable = SpannableString("Not a member yet? Register Now!")
                spannable.setSpan(
                    ForegroundColorSpan(Color.RED),
                    18,
                    31,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                text = spannable
            }
            loginRegisterTxt.setOnClickListener {
                startActivity(
                    Intent(
                        this@LoginActivity,
                        RegistrationActivity::class.java
                    )
                )
            }
            loginSubmitBtn.setOnClickListener {
                if (!loginPasswordEt.text.toString().isPassword()) {
                    loginPasswordContainer.error = "Password must be at least 8 characters with one number, one lowercase letter, one uppercase letter, and one special character"
                } else if (!loginUsernameEt.text.toString().isEmail()) {
                    loginEmailContainer.error = "Invalid Email"
                } else {
                    loginVm.doLogin(
                        loginUsernameEt.text.toString(),
                        loginPasswordEt.text.toString()
                    )
                }
            }

        }
    }

    private fun observeUiChanges() = lifecycleScope.launchWhenCreated {
        loginVm.uiState.collect {
            progressBar(it.progressBarState)
            when (it.uiStates) {
                UiStates.Idle -> {}
                is UiStates.Error -> {
                    Toast.makeText(this@LoginActivity, it.uiStates.message, Toast.LENGTH_SHORT)
                        .show()
                }
                is UiStates.Success -> {
                    when (it.uiStates.message) {
                        "Logged In" -> {
                            startActivity(Intent(this@LoginActivity, TweetActivity::class.java))
                            this@LoginActivity.finish()
                        }
                    }
                }
            }
        }
    }

    private fun progressBar(progressBarState: Int) {
        binding.progressOverlay.root.setOnClickListener{}
        when (progressBarState) {
            PROGRESS_BAR_GONE ->binding.progressOverlay.root.visibility = View.GONE
            PROGRESS_BAR_VISIBLE ->binding.progressOverlay.root.visibility = View.GONE
        }
    }
}

fun String.isEmail(): Boolean {
    val p = "^(\\w)+(\\.\\w+)*@(\\w)+((\\.\\w+)+)\$".toRegex()
    return matches(p)
}

fun String.isPassword(): Boolean {
    val p = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#\$%!\\-_?&])(?=\\S+\$).{8,}".toRegex()
    return matches(p)
}


