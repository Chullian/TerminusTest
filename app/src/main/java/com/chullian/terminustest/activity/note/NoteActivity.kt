package com.chullian.terminustest.activity.note

import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.chullian.terminustest.activity.main.MainVM
import com.chullian.terminustest.databinding.ActivityMainBinding
import com.chullian.terminustest.databinding.ActivityNoteBinding

//@AndroidEntryPoint
class NoteActivity : AppCompatActivity() {
    lateinit var binding: ActivityNoteBinding
    val mainVm: NoteVM by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() {

    }
}