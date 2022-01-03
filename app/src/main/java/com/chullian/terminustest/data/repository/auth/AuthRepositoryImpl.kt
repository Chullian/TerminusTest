package com.chullian.terminustest.data.repository.auth

import android.net.Uri
import android.util.Log
import com.chullian.terminustest.data.persistance.Session
import com.google.firebase.auth.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val fireStore: FirebaseFirestore,
    private val firebaseStorage: FirebaseStorage,
    private val session: Session
) : AuthRepository {


    override suspend fun authenticate(email: String, pass: String): FirebaseUser? {
        firebaseAuth.signInWithEmailAndPassword(email, pass).await()
        return firebaseAuth.currentUser ?: throw FirebaseAuthException("400", "User Not found")
    }

    override suspend fun doRegister(email: String, pass: String, uri: Uri, name: String, bio: String) :FirebaseUser?{
        var result: AuthResult = firebaseAuth.createUserWithEmailAndPassword(email, pass).await()
        var user = firebaseAuth.currentUser
        val profile = UserProfileChangeRequest.Builder()
        profile.displayName = name
        uploadProfilePicture(uri)
        user?.updateProfile(profile.build())
        user?.let { firebaseAuth.updateCurrentUser(it) }
        if(result.user!=null){
            storeProfileData(firebaseAuth.uid.toString(),bio)
        }
        getCurrentUserData()
        return firebaseAuth.currentUser ?: throw FirebaseAuthException("400", "User Not found")
    }

    suspend fun uploadProfilePicture(uri:Uri){
       val avatarStgRef = firebaseStorage.getReference("user"+firebaseAuth.currentUser?.uid+"avatar.jpg")
        avatarStgRef.putFile(uri).await()
    }

    override suspend fun getCurrentUserData() {
        session.email =  firebaseAuth.currentUser?.email.toString()
        session.user =  firebaseAuth.currentUser?.displayName.toString()
        fireStore.collection("user").document(firebaseAuth.uid.toString()).get().addOnSuccessListener {
            session.userBio = it["bio"].toString()
        }
        session.userImage = firebaseStorage.getReference("user"+firebaseAuth.currentUser?.uid+"avatar.jpg").downloadUrl.await()
            .toString()
    }

    private suspend fun storeProfileData(uid: String, bio: String) {
        fireStore.collection("user").document(uid)
            .set(mapOf("bio" to bio))
            .await()
    }
    override fun isLoggedIn() = session.isLoggedIn

    override fun logout() = flow<Boolean> {
        firebaseAuth.signOut()
    }
}