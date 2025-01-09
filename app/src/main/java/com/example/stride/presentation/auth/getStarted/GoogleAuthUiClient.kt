package com.example.stride.presentation.auth.getStarted
import android.util.Log

import android.content.Context
import android.content.Intent
import android.content.IntentSender
import com.example.stride.R
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.BeginSignInRequest.GoogleIdTokenRequestOptions
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.tasks.await

class GoogleAuthUiClient(
    private val context: Context,
    private val oneTapClient: SignInClient
) {
    private val auth = Firebase.auth

    suspend fun signIn(): IntentSender? {
        val result = try {
            oneTapClient.beginSignIn(
                buildSignInRequest()
            ).await()
        } catch(e: Exception) {
            e.printStackTrace()
            if(e is CancellationException) throw e
            null
        }
        return result?.pendingIntent?.intentSender
    }

    suspend fun signInWithIntent(intent: Intent): SignInResult {
        val credential = oneTapClient.getSignInCredentialFromIntent(intent)
        val googleIdToken = credential.googleIdToken
        val googleCredentials = GoogleAuthProvider.getCredential(googleIdToken, null)
        Log.d("GoogleAuthUiClien", "signInWithIntent: $googleCredentials")

        return try {
            val email = extractEmailFromIdToken(googleIdToken)
            val user = auth.signInWithCredential(googleCredentials).await().user
            Log.d("GoogleAuthUiClient", "signInWithIntent: $user")
            Log.d("email","${email}")
            SignInResult(
                data = user?.run {
                    UserData(
                        userId = uid
                    )
                },
                errorMessage = null
            )
        } catch(e: Exception) {
            e.printStackTrace()
            if(e is CancellationException) throw e
            SignInResult(
                data = null,
                errorMessage = e.message
            )
        }
    }
    private fun extractEmailFromIdToken(googleIdToken: String?): String? {
        return try {
            val parts = googleIdToken?.split(".") ?: return null
            if (parts.size < 2) return null
            val payloadJson = String(android.util.Base64.decode(parts[1], android.util.Base64.URL_SAFE), Charsets.UTF_8)
            val payload = org.json.JSONObject(payloadJson)
            payload.optString("email")
        } catch (e: Exception) {
            Log.e("GoogleAuthUiClient", "Error parsing ID token: ${e.message}", e)
            null
        }
    }
    suspend fun signOut() {
        try {
            oneTapClient.signOut().await()
            auth.signOut()
        } catch(e: Exception) {
            e.printStackTrace()
            if(e is CancellationException) throw e
        }
    }

    fun getSignedInUser(): UserData? = auth.currentUser?.run {
        UserData(
            userId = uid
        )
    }

    private fun buildSignInRequest(): BeginSignInRequest {
        return BeginSignInRequest.Builder()
            .setGoogleIdTokenRequestOptions(
                GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setFilterByAuthorizedAccounts(false)
                    .setServerClientId(context.getString(R.string.web_client_id))
                    .build()
            )
            .setAutoSelectEnabled(true)
            .build()
    }
}