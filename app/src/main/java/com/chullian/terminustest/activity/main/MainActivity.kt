package com.chullian.terminustest.activity.main

import android.content.Intent
import android.content.IntentSender
import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.chullian.terminustest.activity.login.LoginActivity
import com.chullian.terminustest.databinding.ActivityMainBinding
import com.chullian.terminustest.utils.UiStates
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    val mainVm: MainVM by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mainVm.isLoggedIn()
        observeUiChanges()
    }

    private fun observeUiChanges() = lifecycleScope.launchWhenCreated {
        mainVm.uiState.collect {
            progressBar(it.progressBarState)
            when(it.uiStates){
                UiStates.Idle -> {}
                is UiStates.Error -> {
                    Toast.makeText(this@MainActivity, it.uiStates.message, Toast.LENGTH_SHORT).show()}
                is UiStates.Success -> {
                    when(it.uiStates.message){
                        "isLoggedIn"-> if(!it.isLoggedIn){
                            startActivity(Intent(this@MainActivity,LoginActivity::class.java))
                        }
                    }
                }
            }
        }
    }

    private fun progressBar(progressBarState: Int) {
        when(progressBarState){

        }
    }


}