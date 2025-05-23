package com.example.futuris

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class StartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)  // Load the start screen layout

        // Find buttons from the layout by their IDs
        val createButton = findViewById<Button>(R.id.btnCreateCapsule)
        val viewButton = findViewById<Button>(R.id.btnViewCapsules)
        val instructionsButton = findViewById<Button>(R.id.btnInstructions)

        // When Create Capsule button is clicked:
        createButton.setOnClickListener {
            // Show a short toast to confirm button press
            Toast.makeText(this, "Create Capsule Clicked", Toast.LENGTH_SHORT).show()
            // Start TimeCapsuleActivity for creating new capsule
            startActivity(Intent(this, TimeCapsuleActivity::class.java))
        }

        // When View Capsules button is clicked:
        viewButton.setOnClickListener {
            Toast.makeText(this, "View Capsule Clicked", Toast.LENGTH_SHORT).show()
            // Start ViewCapsulesActivity to display list of saved capsules
            startActivity(Intent(this, ViewCapsulesActivity::class.java))
        }

        // When Instructions button is clicked:
        instructionsButton.setOnClickListener {
            // Show a popup dialog with instructions
            showInstructionsPopup()
        }
    }

    // Function to show instructions in a modal popup dialog
    private fun showInstructionsPopup() {
        // Inflate the custom popup layout XML
        val dialogView = LayoutInflater.from(this).inflate(R.layout.popup_instructions, null)

        // Build an AlertDialog with the inflated view
        val dialogBuilder = AlertDialog.Builder(this).setView(dialogView)
        val alertDialog = dialogBuilder.create()

        // Make dialog background transparent for rounded corners or custom shape effect
        alertDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        // Show the dialog on screen
        alertDialog.show()

        // Adjust popup size to 90% of screen width and height for better user experience
        val window = alertDialog.window
        val metrics = resources.displayMetrics
        val width = (metrics.widthPixels * 0.9).toInt()
        val height = (metrics.heightPixels * 0.9).toInt()
        window?.setLayout(width, height)

        // Setup close button inside the popup to dismiss the dialog
        val closeBtn = dialogView.findViewById<Button>(R.id.closeInstructionsBtn)
        closeBtn.setOnClickListener {
            alertDialog.dismiss()
        }
    }
}




