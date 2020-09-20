package com.sangeetha.mymu_spotifyclone.view.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.RequestManager
import com.sangeetha.mymu_spotifyclone.MainActivity
import com.sangeetha.mymu_spotifyclone.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_splash_screen.*
import kotlinx.coroutines.*
import javax.inject.Inject

@AndroidEntryPoint
class SplashScreenActivity(): AppCompatActivity() {

    @Inject
    lateinit var glide: RequestManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        setLogo()
        setSplashScreen()
    }

    private fun setLogo() {
        glide.load(R.raw.headphone).into(logo)
    }

    private fun setSplashScreen() {
        CoroutineScope(Dispatchers.Main).launch {
            delay(3000)
            navigateToMainActivity()
            finish()
        }
    }

    private fun navigateToMainActivity() {
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
    }
}