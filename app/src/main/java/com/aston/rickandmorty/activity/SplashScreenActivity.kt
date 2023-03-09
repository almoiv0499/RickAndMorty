package com.aston.rickandmorty.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.aston.rickandmorty.R

private const val SPLASH_SCREEN_LOADING_TIME = 2000L

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        customizeWindow()
        launchMainActivity()
    }

    private fun customizeWindow() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
    }

    private fun launchMainActivity() {
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, SPLASH_SCREEN_LOADING_TIME)
    }
}