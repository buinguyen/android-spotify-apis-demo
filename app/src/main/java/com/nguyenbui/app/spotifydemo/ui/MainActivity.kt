package com.nguyenbui.app.spotifydemo.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.nguyenbui.app.spotifydemo.DemoApplication
import com.nguyenbui.app.spotifydemo.databinding.ActivityMainBinding
import com.nguyenbui.app.spotifydemo.spotify.SpotifyAppApi
import com.nguyenbui.app.spotifydemo.spotify.SpotifyAuthenticator
import kaaes.spotify.webapi.android.SpotifyCallback
import kaaes.spotify.webapi.android.SpotifyError
import kaaes.spotify.webapi.android.models.Pager
import kaaes.spotify.webapi.android.models.SavedTrack
import org.json.JSONObject
import retrofit.client.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var spotifyAuth: SpotifyAuthenticator
    private lateinit var spotifyAppApi: SpotifyAppApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        spotifyAuth = DemoApplication.getApp(this).provideSpotifyAuthenticator()
        spotifyAppApi = DemoApplication.getApp(this).provideSpotifyAppApi()

        binding.btnLogin.setOnClickListener { login() }
        binding.btnLogout.setOnClickListener { logout() }
        binding.btnPlaylist.setOnClickListener { getPlayList() }
        binding.btnMyTracks.setOnClickListener { getMyTracks() }
    }

    private fun setResult(obj: Any?) {
        val jsonObject = JSONObject(Gson().toJson(obj))
        val jsonString = jsonObject.toString(2)
        Log.d(TAG, "\n$jsonString")
        Handler(Looper.getMainLooper()).post {
            binding.tvResponse.text = jsonString
        }
    }

    private fun login() {
        spotifyAuth.login(this, object : SpotifyAuthenticator.OnAuthListener {
            override fun onLoginSuccess(token: String) {
                spotifyAppApi.setToken(token)
                Log.e(TAG, "onLoginSuccess")
            }

            override fun onLoginFailed() {
                Log.e(TAG, "onLoginFailed")
            }

        })
    }

    private fun logout() {
        spotifyAuth.logout()
    }

    private fun getPlayList() {

    }

    private fun getMyTracks() {
        spotifyAppApi.getMySavedTracks(object : SpotifyCallback<Pager<SavedTrack>>() {
            override fun success(savedTrackPager: Pager<SavedTrack>?, response: Response?) {
                setResult(savedTrackPager)
            }

            override fun failure(error: SpotifyError?) {
                setResult(error)
            }

        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        spotifyAuth.onLoginResult(requestCode, resultCode, data)
    }

    companion object {
        private val TAG = MainActivity::class.java.name
    }
}