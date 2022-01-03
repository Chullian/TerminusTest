package com.chullian.terminustest.data.repository.tweet

import com.chullian.terminustest.data.db.TweetDao
import com.chullian.terminustest.data.model.TweetModel
import com.chullian.terminustest.data.persistance.Session
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext
import kotlin.random.Random

class TweetRepositoryImpl @Inject constructor(
    private val tweetDao: TweetDao,
    private val fireStore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth,
    private val prefs: Session
) : TweetRepository, CoroutineScope {
    override val coroutineContext: CoroutineContext = Job()

    private val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')

    private fun tweetId() = (1..10)
        .map { i -> Random.nextInt(0, charPool.size) }
        .map(charPool::get)
        .joinToString("");

    override suspend fun sentTweet(tweet: String) {
        val tweetModel = TweetModel(
            tweetId = tweetId(),
            tweet = tweet,
            author = prefs.user,
            authorImage = prefs.userImage,
            views = firebaseAuth.uid.toString(),
            createdAt = System.currentTimeMillis(),
            updatedAt = System.currentTimeMillis()
        )
        tweetDao.insertOrReplace(tweetModel)
        fireStore.collection("tweet")
            .document(tweetModel.tweetId)
            .set(tweetModel)
            .await()
    }

    override suspend fun retrieveTweets(): Flow<List<TweetModel>> {
        fireStore.collection("tweet")
            .addSnapshotListener { value, error ->
                launch{
                    value?.documents?.forEach { document ->
                        var tweetModel = TweetModel(
                            tweetId = document.id,
                            tweet = document["tweet"].toString(),
                            author = document["author"].toString(),
                            authorImage = document["authorImage"].toString(),
                            views = document["views"].toString(),
                            createdAt = document["createdAt"].toString().toLong(),
                            updatedAt = document["updatedAt"].toString().toLong()
                        )
                        tweetDao.insertOrReplace(tweetModel)
                    }
                }
            }
        return tweetDao.findAll()
    }

    override suspend fun updateTweetView(tweet: TweetModel) {
        val list = tweet.views.split(",").toMutableList()
        list.add(firebaseAuth.uid.toString())
        tweet.views = list.filter { it!=firebaseAuth.uid }.joinToString(",")
        tweetDao.update(tweet)
        fireStore.collection("tweet").document(tweet.tweetId).set(tweet).await()
    }
}

