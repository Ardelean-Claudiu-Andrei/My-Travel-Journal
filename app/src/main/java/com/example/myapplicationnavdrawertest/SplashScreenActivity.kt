package com.example.myapplicationnavdrawertest
import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import androidx.appcompat.app.AppCompatActivity

class SplashScreenActivity : AppCompatActivity() {

    private val SPLASH_TIME_OUT: Long = 2000 // 2 seconds delay

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)

        SystemClock.sleep(2000) // 2 seconds delay

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

}


