package com.example.futuris

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

// Activity that displays the detailed view of a time capsule
class CapsuleDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_capsule_details) // Load the XML layout for this screen

        // Link UI components to variables
        val titleView = findViewById<TextView>(R.id.tvCapsuleTitle)
        val dateView = findViewById<TextView>(R.id.tvCapsuleDate)
        val messageView = findViewById<TextView>(R.id.tvCapsuleMessage)
        val backButton = findViewById<Button>(R.id.btnBackToStart)

        // Get the capsule data passed via Intent extras
        val title = intent.getStringExtra("title") ?: "No Title"                      // Default fallback if data is missing
        val unlockDate = intent.getStringExtra("unlockDate") ?: "Unknown Date"
        val message = intent.getStringExtra("message") ?: "No message available."

        // Set text values to the corresponding TextViews
        titleView.text = title
        dateView.text = "Unlock Date: $unlockDate"
        messageView.text = message

        // Set up the back button to return to the previous screen
        backButton.setOnClickListener {
            finish() // Ends the current activity
        }
    }
}
