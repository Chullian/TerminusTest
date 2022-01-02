package com.chullian.terminustest.data.repository

import android.net.Uri
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

/**
 * Created by binMammootty on 02/01/2022.
 */
interface AuthRepository {
    suspend fun authenticate(email:String,pass:String): FirebaseUser?
    suspend fun doRegister(email: String, pass: String, uri: Uri, name: String, bio: String):FirebaseUser?
    fun isLoggedIn():Boolean
}