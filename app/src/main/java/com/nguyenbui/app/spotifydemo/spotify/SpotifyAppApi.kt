package com.nguyenbui.app.spotifydemo.spotify

import kaaes.spotify.webapi.android.SpotifyCallback
import kaaes.spotify.webapi.android.models.Pager
import kaaes.spotify.webapi.android.models.SavedTrack

interface SpotifyAppApi {

    fun setToken(spotifyToken: String?)

    fun getMySavedTracks(callback: SpotifyCallback<Pager<SavedTrack>>)
}