package com.nguyenbui.app.spotifydemo.spotify

import kaaes.spotify.webapi.android.SpotifyApi
import kaaes.spotify.webapi.android.SpotifyCallback
import kaaes.spotify.webapi.android.SpotifyError
import kaaes.spotify.webapi.android.SpotifyService
import kaaes.spotify.webapi.android.models.Pager
import kaaes.spotify.webapi.android.models.SavedTrack
import retrofit.RestAdapter
import retrofit.client.Response

class SpotifyAppApiHandler : SpotifyAppApi {

    private var spotify: SpotifyService

    init {
        spotify = SpotifyApi().service
    }

    override fun setToken(spotifyToken: String?) {
        val restAdapter = RestAdapter.Builder()
                .setEndpoint(SpotifyApi.SPOTIFY_WEB_API_ENDPOINT)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setRequestInterceptor { request -> request.addHeader("Authorization", "Bearer $spotifyToken") }
                .build()
        spotify = restAdapter.create(SpotifyService::class.java)
    }

    override fun getMySavedTracks(callback: SpotifyCallback<Pager<SavedTrack>>) {
        val map = mutableMapOf<String, Any>(
                "market" to "VN"
        )
        spotify.getMySavedTracks(map, object : SpotifyCallback<Pager<SavedTrack>>() {
            override fun success(savedTrackPager: Pager<SavedTrack>, response: Response) {
                callback.success(savedTrackPager, response)
            }

            override fun failure(error: SpotifyError) {
                callback.failure(error)
            }
        })
    }
}