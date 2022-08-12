package com.buygrup.bookfinder

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

const val SPLASH_SCREEN_TIME_OUT:Long = 2300
class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val r= Runnable {
            startActivity(Intent(this@SplashScreenActivity,
                MainNavActivity::class.java))
            finish()
        }
        Handler(Looper.getMainLooper()).postDelayed(r, SPLASH_SCREEN_TIME_OUT)

    }
}