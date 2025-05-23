package com.example.zen

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

/**
 * ActivityCalculator handles the main stress level calculation logic.
 * It collects input from various checkboxes, computes a percentage score,
 * and displays a categorized stress result using a popup.
 */
class ActivityCalculator : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculator)

        // Button to trigger the stress calculation
        val calculateButton = findViewById<Button>(R.id.calculateButton)

        // On click, compute stress score and display results
        calculateButton.setOnClickListener {
            try {
                val stressPercentage = calculateStressPercentage()
                Log.d("ActivityCalculator", "Calculated stress percentage: $stressPercentage")
                showStressResult(stressPercentage)
            } catch (e: Exception) {
                // Log error and inform user via Toast
                Log.e("ActivityCalculator", "Error calculating stress: ${e.message}", e)
                Toast.makeText(this, "Error calculating stress level", Toast.LENGTH_SHORT).show()
            }
        }
    }

    /**
     * Calculates the stress percentage based on selected checkboxes.
     * Each category contributes to the total score and has a defined max value.
     */
    private fun calculateStressPercentage(): Int {
        var score = 0      // Accumulates actual score based on user selections
        var maxScore = 0   // Accumulates the maximum possible score

        // Mood scoring (max: 25)
        if (findViewById<CheckBox>(R.id.checkBox_mood4).isChecked) score += 25
        if (findViewById<CheckBox>(R.id.checkBox_mood3).isChecked) score += 15
        if (findViewById<CheckBox>(R.id.checkBox_mood1).isChecked) score += 0
        maxScore += 25

        // Meals skipped scoring (max: 30)
        if (findViewById<CheckBox>(R.id.checkBox_meal2).isChecked) score += 10
        if (findViewById<CheckBox>(R.id.checkBox_meal3).isChecked) score += 20
        if (findViewById<CheckBox>(R.id.checkBox_meal4).isChecked) score += 30
        maxScore += 30

        // Task overload scoring (max: 30)
        if (findViewById<CheckBox>(R.id.checkBox_tasks2).isChecked) score += 10
        if (findViewById<CheckBox>(R.id.checkBox_tasks3).isChecked) score += 20
        if (findViewById<CheckBox>(R.id.checkBox_tasks4).isChecked) score += 30
        maxScore += 30

        // Notification overload scoring (max: 30)
        if (findViewById<CheckBox>(R.id.checkBox_notif2).isChecked) score += 10
        if (findViewById<CheckBox>(R.id.checkBox_notif3).isChecked) score += 20
        if (findViewById<CheckBox>(R.id.checkBox_notif4).isChecked) score += 30
        maxScore += 30

        // Final stress score as a percentage of max possible score
        return if (maxScore > 0) (score * 100) / maxScore else 0
    }

    /**
     * Displays a categorized result of the stress score using a custom popup.
     * Categories: Low, Moderate, High, Very High
     */
    private fun showStressResult(percentage: Int) {
        // Determine stress category based on percentage thresholds
        val stressCategory = when {
            percentage <= 30 -> StressLevelPopup.STRESS_LOW
            percentage <= 60 -> StressLevelPopup.STRESS_MODERATE
            percentage <= 85 -> StressLevelPopup.STRESS_HIGH
            else -> StressLevelPopup.STRESS_VERY_HIGH
        }

        Log.d("ActivityCalculator", "Showing stress result: $percentage%, category: $stressCategory")

        try {
            // Initialize and display the custom popup with calculated data
            val popup = StressLevelPopup(this)
            popup.showStressResult(percentage, stressCategory)
        } catch (e: Exception) {
            Log.e("ActivityCalculator", "Error showing popup: ${e.message}", e)
            Toast.makeText(this, "Error displaying results", Toast.LENGTH_SHORT).show()
        }
    }
}






