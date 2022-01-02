package com.chullian.terminustest.data.repository

import com.chullian.terminustest.data.Session
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val session: Session
) : AuthRepository {


    override suspend fun authenticate(email: String, pass: String): FirebaseUser? {
        firebaseAuth.signInWithEmailAndPassword(email, pass).await()
        return firebaseAuth.currentUser ?: throw FirebaseAuthException("400", "User Not found")
    }

    override suspend fun doRegister(email: String, pass: String) :FirebaseUser?{
        firebaseAuth.createUserWithEmailAndPassword(email, pass).await()
        return firebaseAuth.currentUser ?: throw FirebaseAuthException("400", "User Not found")
    }

    override fun isLoggedIn() = session.isLoggedIn

}