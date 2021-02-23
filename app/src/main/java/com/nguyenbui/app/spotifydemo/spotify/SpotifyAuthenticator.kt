package com.nguyenbui.app.spotifydemo.spotify

import android.app.Activity
import android.content.Intent
import androidx.annotation.NonNull
import com.spotify.sdk.android.authentication.AuthenticationClient
import com.spotify.sdk.android.authentication.AuthenticationRequest
import com.spotify.sdk.android.authentication.AuthenticationResponse

class SpotifyAuthenticator {

    private var authResponse: AuthenticationResponse? = null
    private var mListener: OnAuthListener? = null

    interface OnAuthListener {
        fun onLoginSuccess(token: String)
        fun onLoginFailed()
    }

    fun login(@NonNull activity: Activity, listener: OnAuthListener?) {
        mListener = listener

        val builder = AuthenticationRequest.Builder(CLIENT_ID, AuthenticationResponse.Type.TOKEN, REDIRECT_URI)
        builder.setScopes(AUTH_SCOPES)
        val request = builder.build()
        AuthenticationClient.openLoginActivity(activity, REQUEST_CODE, request)
    }

    fun logout() {
        authResponse = null
    }

    fun onLoginResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode != REQUEST_CODE) return

        val response = AuthenticationClient.getResponse(resultCode, data)
        when (response.type) {
            AuthenticationResponse.Type.TOKEN -> {
                authResponse = response
                mListener?.onLoginSuccess(response.accessToken)
            }
            AuthenticationResponse.Type.ERROR -> {
                mListener?.onLoginFailed()
            }
            else -> {
                /* Do nothing */
            }
        }
    }

    companion object {
        private const val CLIENT_ID = "4aec545014984acca723c1c9d584caf6"
        private const val REDIRECT_URI = "http://localhost:8888/callback"
        private const val REQUEST_CODE = 1337

        private val AUTH_SCOPES = arrayOf(
                "ugc-image-upload",
                "user-read-recently-played",
                "user-top-read",
                "user-read-playback-position",
                "user-read-playback-state",
                "user-modify-playback-state",
                "user-read-currently-playing",
                "app-remote-control",
                "streaming",
                "playlist-modify-public",
                "playlist-modify-private",
                "playlist-read-private",
                "playlist-read-collaborative",
                "user-follow-modify",
                "user-follow-read",
                "user-library-modify",
                "user-library-read",
                "user-read-email",
                "user-read-private"
        )
    }
}