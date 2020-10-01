package com.sangeetha.mymu_spotifyclone.exoplayer

import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaBrowserCompat.MediaItem.FLAG_PLAYABLE
import android.support.v4.media.MediaDescriptionCompat
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.MediaMetadataCompat.*
import androidx.core.net.toUri
import com.google.android.exoplayer2.source.ConcatenatingMediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.sangeetha.mymu_spotifyclone.data.database.SongDatabase
import com.sangeetha.mymu_spotifyclone.exoplayer.State.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FirebaseMusicService @Inject constructor(private val songDatabase: SongDatabase){

    private var songs = emptyList<MediaMetadataCompat>()

    private val onReadyListener = mutableListOf<(Boolean) -> Unit>()

    suspend fun fetchMediaData() = withContext(Dispatchers.IO) {
        state = STATE_INITIALIZING
        val allSongs = songDatabase.getAllSongs()
        songs = allSongs.map {
            MediaMetadataCompat.Builder()
                .putString(METADATA_KEY_ARTIST, it.subtitle)
                .putString(METADATA_KEY_MEDIA_ID, it.mediaId)
                .putString(METADATA_KEY_TITLE, it.title)
                .putString(METADATA_KEY_DISPLAY_TITLE, it.title)
                .putString(METADATA_KEY_DISPLAY_ICON_URI, it.imageUrl)
                .putString(METADATA_KEY_MEDIA_URI, it.songUrl)
                .putString(METADATA_KEY_ALBUM_ART_URI, it.imageUrl)
                .putString(METADATA_KEY_DISPLAY_SUBTITLE, it.subtitle)
                .putString(METADATA_KEY_DISPLAY_DESCRIPTION, it.subtitle)
                .build()
        }
        state = STATE_INITIALIZED
    }

    fun asMediaSource(dataSourceFactory: DefaultDataSourceFactory): ConcatenatingMediaSource {
        val concatenatingMediaSource = ConcatenatingMediaSource()
        songs.forEach {
            val mediaSource = ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(it.getString(METADATA_KEY_MEDIA_URI).toUri())
            concatenatingMediaSource.addMediaSource(mediaSource)
        }
        return concatenatingMediaSource
    }

    fun asMediaItems() = songs.map {
        val description = MediaDescriptionCompat.Builder()
            .setTitle(it.description.title)
            .setMediaUri(it.getString(METADATA_KEY_MEDIA_URI).toUri())
            .setSubtitle(it.description.subtitle)
            .setMediaId(it.description.mediaId)
            .setIconUri(it.description.iconUri)
            .build()

        MediaBrowserCompat.MediaItem(description, FLAG_PLAYABLE)
    }

    private var state = STATE_CREATED
        set(value) {
            if (value == STATE_INITIALIZED || value == ERROR) {
                synchronized(onReadyListener) {
                    field = value
                    onReadyListener.forEach { listener ->
                        listener(state == STATE_INITIALIZED )
                    }
                }
            } else {
                field = value
            }
        }

    fun whenReady(action: (Boolean) -> Unit): Boolean {
        return if (state == STATE_CREATED || state == STATE_INITIALIZING) {
            onReadyListener += action
            false
        } else {
            action(state == STATE_INITIALIZED)
            true
        }
    }

}

enum class State {
    STATE_CREATED,
    STATE_INITIALIZING,
    STATE_INITIALIZED,
    ERROR
}