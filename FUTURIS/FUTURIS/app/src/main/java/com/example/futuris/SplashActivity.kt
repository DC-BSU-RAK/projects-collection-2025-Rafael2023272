package com.example.futuris

// Standard Android and UI imports
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

/**
 * SplashActivity is the entry point of the app.
 * It displays a GIF-based splash screen for 5 seconds before navigating to the StartActivity.
 */
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Sets the layout for the splash screen
        setContentView(R.layout.activity_splash)

        // Reference to ImageView used for displaying the animated GIF
        val gifView = findViewById<ImageView>(R.id.gifView)

        // Load and display the GIF using Glide library (asGif ensures proper decoding)
        Glide.with(this)
            .asGif()
            .load(R.drawable.finalappgif)
            .into(gifView)

        // Post a delayed task to the main thread's message queue
        // Transitions to StartActivity after a 5-second (5000 ms) delay
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, StartActivity::class.java))
            finish() // Ensures SplashActivity is removed from back stack
        }, 5000)
    }
}


