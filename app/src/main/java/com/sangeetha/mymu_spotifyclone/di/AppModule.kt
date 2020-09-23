package com.sangeetha.mymu_spotifyclone.di

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.sangeetha.mymu_spotifyclone.R
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideGlideInstance(@ApplicationContext context: Context) = Glide.with(context)
        .applyDefaultRequestOptions(
            RequestOptions().placeholder(R.drawable.ic_play_button)
                .error(R.drawable.ic_play_button)
                .diskCacheStrategy(DiskCacheStrategy.DATA)
        )
}
