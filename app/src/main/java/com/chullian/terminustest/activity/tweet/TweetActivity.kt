package com.chullian.terminustest.activity.tweet

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import coil.transform.CircleCropTransformation
import com.chullian.terminustest.App
import com.chullian.terminustest.R
import com.chullian.terminustest.activity.main.MainActivity
import com.chullian.terminustest.databinding.ActivityTweetBinding
import com.chullian.terminustest.utils.PROGRESS_BAR_GONE
import com.chullian.terminustest.utils.UiStates
import com.chullian.terminustest.utils.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class TweetActivity : AppCompatActivity() {
    lateinit var binding: ActivityTweetBinding
    val tweetVM: TweetVM by viewModels()

    private val adapter = TweetAdapter{tweet->
        tweetVM.updateTweetView(tweet)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTweetBinding.inflate(layoutInflater)
        setContentView(binding.root)
        tweetVM.getUser()
        tweetVM.getTweets()
        initViews()
        observeUiChanges()
    }

    private fun observeUiChanges() = lifecycleScope.launchWhenCreated {
        tweetVM.uiState.collect {
            progress(it.progressBarState)
            updateUserData(it.username,it.userImage,it.userBio)
            when (it.uiStates) {
                UiStates.Idle -> {}
                is UiStates.Error -> {}
                is UiStates.Success -> {
                    when (it.uiStates.message) {
                        "tweets" -> {
                            adapter.tweets = it.tweetList
                            binding.tweetRecyclerView.scrollToPosition(0)
                        }
                    }
                }
            }
        }
    }

    private fun updateUserData(name:String,image:String,bio:String) {
       binding.toolbar.apply {
           toolbarUserNameTv.text = name
           toolbarUserBioTv.text = bio
           App.instance?.imageLoader?.let {
               toolbarUserImageIv.load(image, it){
                   transformations(CircleCropTransformation())
                   placeholder(R.drawable.ic_person)
               }
           }
           if(bio.isEmpty()){
               toolbarUserBioTv.visibility = View.GONE
           }else{
               toolbarUserBioTv.visibility = View.VISIBLE
           }
       }
    }

    private fun progress(progressBarState: Int) {

    }


    private fun initViews() {
        binding.apply {
            tweetRecyclerView.apply {
                layoutManager = LinearLayoutManager(this@TweetActivity)
                adapter = this@TweetActivity.adapter
                addItemDecoration(
                    DividerItemDecoration(
                        this@TweetActivity,
                        LinearLayoutManager.VERTICAL
                    )
                )
            }
            tweetSentIv.setOnClickListener {
                if (tweetEt.text.toString().trim().isNotEmpty()) {
                    tweetVM.sendTweet(tweetEt.text.toString())
                    tweetEt.setText("")
                    hideKeyboard()
                }
            }
            toolbar.toolbarLogoutUseriv.setOnClickListener {
                val alert = AlertDialog.Builder(this@TweetActivity)
                alert.setTitle("Exit")
                alert.setMessage("Are you sure to logout from this FireTweet?")
                alert.setPositiveButton("Yes"){ dialog: DialogInterface, _->
                    tweetVM.logout()
                    val intent = Intent(this@TweetActivity,MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                }
                alert.setNegativeButton("No"){ dialog: DialogInterface, _->
                    dialog.dismiss()
                }
                alert.show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.logout,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId==R.id.logout_menu_item){


        }
        return super.onOptionsItemSelected(item)
    }
}


