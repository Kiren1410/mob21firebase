package com.kiren.mob21firebase.core.service

import android.content.Intent
import android.content.IntentSender
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.identity.SignInCredential
import com.google.android.gms.common.internal.safeparcel.SafeParcelableSerializer
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.kiren.mob21firebase.data.model.User
import kotlinx.coroutines.tasks.await
import java.util.concurrent.CancellationException

class AuthService(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
) {

    suspend fun register(email: String, pass: String): FirebaseUser? {
        val task = auth.createUserWithEmailAndPassword(email, pass).await()
        return task.user
    }

    suspend fun login(email: String, pass: String): FirebaseUser? {
        val task = auth.signInWithEmailAndPassword(email, pass).await()
        return task.user
    }

    fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }

    fun logout() {
        auth.signOut()
    }
}