package com.sangeetha.mymu_spotifyclone.di

import android.content.Context
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.audio.AudioAttributes
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.sangeetha.mymu_spotifyclone.data.database.SongDatabase
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ServiceScoped
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ServiceComponent

@Module
@InstallIn(ServiceComponent::class)
object ServiceModule {

    @ServiceScoped
    @Provides
    fun provideAudioAttributes(): AudioAttributes {
        return AudioAttributes.Builder()
            .setContentType(C.CONTENT_TYPE_MUSIC)
            .setUsage(C.USAGE_MEDIA)
            .build()
    }

    @ServiceScoped
    @Provides
    fun provideExoplayer(@ApplicationContext context: Context, audioAttributes: AudioAttributes): SimpleExoPlayer {
        return SimpleExoPlayer.Builder(context).build().apply {
            setAudioAttributes(audioAttributes, true)
                setHandleAudioBecomingNoisy(true)
        }
    }

    @ServiceScoped
    @Provides
    fun provideDataSourceFactory(@ApplicationContext context: Context): DefaultDataSourceFactory {
        return DefaultDataSourceFactory(context, Util.getUserAgent(context, "MyMu"))
    }

    @ServiceScoped
    @Provides
    fun provideSongDatabase() = SongDatabase()
}
