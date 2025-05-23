package com.example.zen

// Required Android framework and UI components

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


// Entry activity for the Zen app UI
class StartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Enables drawing behind system bars (status, nav)
        enableEdgeToEdge()

        // Sets the content layout for the activity
        setContentView(R.layout.activity_start)

        // Adjust padding to avoid overlapping system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Bind and configure "How It Works" button to show popup
        val howItWorksBtn = findViewById<Button>(R.id.howitworksButton)
        howItWorksBtn.setOnClickListener {
            showHowItWorksPopup()
        }

        // Bind and configure "Instructions" button to show popup
        val instructionsBtn = findViewById<Button>(R.id.instructionsButton)
        instructionsBtn.setOnClickListener {
            showInstructionsPopup()
        }

        // Launch calculator activity on "Start" button click
        val startBtn = findViewById<Button>(R.id.startButton)
        startBtn.setOnClickListener {
            val intent = Intent(this, ActivityCalculator::class.java)
            startActivity(intent)
        }
    }

    /**
     * Displays a modal dialog with "How It Works" content.
     * Uses a custom layout and sets dimensions to 80% of screen size.
     */
    private fun showHowItWorksPopup() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.popup_how_to_use, null)
        val dialogBuilder = AlertDialog.Builder(this).setView(dialogView)
        val alertDialog = dialogBuilder.create()

        alertDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        alertDialog.show()

        // Dynamically resize popup window to 80% of screen width/height
        val window = alertDialog.window
        val metrics = resources.displayMetrics
        val width = (metrics.widthPixels * 0.8).toInt()
        val height = (metrics.heightPixels * 0.8).toInt()
        window?.setLayout(width, height)

        // Dismiss the dialog on close button click
        val closeBtn = dialogView.findViewById<Button>(R.id.closePopupBtn)
        closeBtn.setOnClickListener {
            alertDialog.dismiss()
        }
    }

    /**
     * Displays a modal dialog with usage instructions.
     * Reuses the popup logic with different layout and close handler.
     */
    private fun showInstructionsPopup() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.popup_instructions, null)
        val dialogBuilder = AlertDialog.Builder(this).setView(dialogView)
        val alertDialog = dialogBuilder.create()

        alertDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        alertDialog.show()

        // Resize popup window to 80% of screen size
        val window = alertDialog.window
        val metrics = resources.displayMetrics
        val width = (metrics.widthPixels * 0.8).toInt()
        val height = (metrics.heightPixels * 0.8).toInt()
        window?.setLayout(width, height)

        // Dismiss the dialog on close button click
        val closeBtn = dialogView.findViewById<Button>(R.id.closeInstructionsBtn)
        closeBtn.setOnClickListener {
            alertDialog.dismiss()
        }
    }
}







