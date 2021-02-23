package com.nguyenbui.app.spotifydemo

import android.app.Application
import android.content.Context
import androidx.annotation.NonNull
import com.nguyenbui.app.spotifydemo.spotify.SpotifyAppApi
import com.nguyenbui.app.spotifydemo.spotify.SpotifyAppApiHandler
import com.nguyenbui.app.spotifydemo.spotify.SpotifyAuthenticator

class DemoApplication : Application() {

    private lateinit var spotifyAuthenticator: SpotifyAuthenticator
    private lateinit var spotifyAppApi: SpotifyAppApi

    override fun onCreate() {
        super.onCreate()

        spotifyAuthenticator = SpotifyAuthenticator()
        spotifyAppApi = SpotifyAppApiHandler()
    }

    fun provideSpotifyAuthenticator(): SpotifyAuthenticator {
        return spotifyAuthenticator
    }

    fun provideSpotifyAppApi(): SpotifyAppApi {
        return spotifyAppApi
    }

    companion object {
        fun getApp(@NonNull context: Context): DemoApplication {
            return context.applicationContext as DemoApplication
        }
    }
}