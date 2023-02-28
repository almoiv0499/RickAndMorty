package com.aston.rickandmorty.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.aston.rickandmorty.R

class SplashScreenActivity : AppCompatActivity() {

    companion object {
        private const val SPLASH_SCREEN_LOADING_TIME = 2000L
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        launchMainActivity()
    }

    private fun launchMainActivity() {
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }, SPLASH_SCREEN_LOADING_TIME)
    }
}