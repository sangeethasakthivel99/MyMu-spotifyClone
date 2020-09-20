package com.sangeetha.mymu_spotifyclone.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.sangeetha.mymu_spotifyclone.R
import kotlinx.android.synthetic.main.activity_splash_screen.*

class SplashScreenActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        Glide.with(this).load(R.raw.headphone).into(logo)
    }
}