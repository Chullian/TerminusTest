package com.chullian.terminustest.activity.registration

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import coil.load
import coil.transform.CircleCropTransformation
import com.chullian.terminustest.activity.login.isEmail
import com.chullian.terminustest.activity.login.isPassword
import com.chullian.terminustest.activity.main.MainActivity
import com.chullian.terminustest.databinding.ActivityRegistrationBinding
import com.chullian.terminustest.utils.UiStates
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect


/**
 * Created by binMammootty on 02/01/2022.
 */

@AndroidEntryPoint
@RequiresApi(Build.VERSION_CODES.M)
class RegistrationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegistrationBinding
    private val registrationVm: RegistrationVM by viewModels()
    private var imageUri = Uri.EMPTY
    var getPicture = registerForActivityResult<String, Uri>(
        ActivityResultContracts.GetContent()
    ) { uri ->
        imageUri = uri
        binding.registrationImageIv.load(uri) {
            transformations(CircleCropTransformation())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
        observeUiChanges()
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                getPicture.launch("image/*");
            } else {
                Toast.makeText(
                    this,
                    "Give Camera permission to to save picture",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }


    private fun initViews() {
        binding.apply {
            registrationSubmitBtn.setOnClickListener {
                if (validFields()) {
                    registrationVm.doRegister(
                        email = registrationEmailEt.text.toString(),
                        pass = registrationPassEt.text.toString(),
                        uri = imageUri,
                        name = registrationNameEt.text.toString(),
                        bio = registrationBioEt.text.toString()
                    )
                }
            }
            registrationImageIv.setOnClickListener {
                when {
                    checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED -> {getPicture.launch("image/*")}
                    shouldShowRequestPermissionRationale(Manifest.permission.CAMERA) -> {}
                    else -> {requestPermissionLauncher.launch(Manifest.permission.CAMERA)
                    }
                }
            }
        }
    }

    private fun validFields(): Boolean {
        binding.apply {
            return when {
                registrationNameEt.text.toString().isEmpty() -> {
                    registrationNameContainer.error = "Name field cannot be empty";false
                }
                !registrationPassEt.text.toString().isPassword() -> {
                    registrationPassContainer.error =
                        "Password must be at least 8 characters with one number, one lowercase letter, one uppercase letter, and one special character";false
                }
                !registrationEmailEt.text.toString().isEmail() -> {
                    registrationEmailContainer.error = "Please provide a valid email address";false
                }
                else -> true
            }
        }
    }

    private fun observeUiChanges() = lifecycleScope.launchWhenCreated {
        registrationVm.uiState.collect {
            progressBar(it.progressBarState)
            when (it.uiStates) {
                UiStates.Idle -> {}
                is UiStates.Error -> {
                    Toast.makeText(
                        this@RegistrationActivity,
                        it.uiStates.message,
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
                is UiStates.Success -> {
                    when (it.uiStates.message) {
                        "Registered" ->
                            startActivity(
                                Intent(
                                    this@RegistrationActivity,
                                    MainActivity::class.java
                                )
                            )
                    }
                }
            }
        }
    }

    private fun progressBar(progressBarState: Int) {
        when (progressBarState) {

        }
    }

}