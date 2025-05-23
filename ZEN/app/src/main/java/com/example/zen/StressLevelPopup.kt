package com.example.zen

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat

/**
 * A reusable class that creates and shows a custom popup dialog displaying
 * a user's stress level based on a calculated percentage and category.
 */
class StressLevelPopup(private val context: Context) {
    private var dialog: Dialog? = null

    companion object {
        // Constants representing stress level categories
        const val STRESS_LOW = 1      // 0–30%
        const val STRESS_MODERATE = 2 // 31–60%
        const val STRESS_HIGH = 3     // 61–85%
        const val STRESS_VERY_HIGH = 4// 86–100%
    }

    /**
     * Displays the stress result popup with dynamic content based on stress percentage and category.
     */
    fun showStressResult(percentage: Int, stressCategory: Int) {
        dialog = Dialog(context)
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)

        // Inflate the custom layout for the popup
        dialog?.setContentView(R.layout.popup_result)

        // Configure the popup window size, background, and animation
        val window = dialog?.window
        window?.apply {
            val displayMetrics = DisplayMetrics()
            val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            windowManager.defaultDisplay.getMetrics(displayMetrics)
            val screenWidth = displayMetrics.widthPixels
            val screenHeight = displayMetrics.heightPixels

            // Set dialog size to 90% of screen dimensions
            val width = (screenWidth * 0.9).toInt()
            val height = (screenHeight * 0.9).toInt()

            setLayout(width, height) // Set custom size
            setGravity(Gravity.CENTER) // Center it on screen
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT)) // Remove background corners
            setWindowAnimations(android.R.style.Animation_Dialog) // Apply default fade animation
        }

        // Get references to UI elements from the layout
        val resultTextView = dialog?.findViewById<TextView>(R.id.tvStressPercentage)
        val categoryTextView = dialog?.findViewById<TextView>(R.id.tvStressCategory)
        val descriptionTextView = dialog?.findViewById<TextView>(R.id.tvStressDescription)
        val footerTextView = dialog?.findViewById<TextView>(R.id.tvFooter)
        val homeButton = dialog?.findViewById<Button>(R.id.btnHome)

        // Defensive check to prevent app crashes due to missing views
        if (resultTextView == null || categoryTextView == null || descriptionTextView == null || homeButton == null || footerTextView == null) {
            dialog?.dismiss()
            throw IllegalStateException("One or more required views are missing in popup_result.xml.")
        }

        // Display the calculated percentage
        resultTextView.text = "$percentage%"

        // Update UI content based on the stress level category
        when (stressCategory) {
            STRESS_LOW -> {
                categoryTextView.text = "LOW STRESS (0–30%):\nNICE! YOU ARE GOOD TO GO"
                categoryTextView.setTextColor(Color.parseColor("#2E8B57")) // Green
                descriptionTextView.text =
                    "Based on your results, your stress level is low. You're doing a great job managing your stress! Keep maintaining healthy habits like proper sleep, exercise, and relaxation."
            }

            STRESS_MODERATE -> {
                categoryTextView.text = "MODERATE STRESS (31–60%):\nMANAGEABLE STRESS, STAY STRONG!"
                categoryTextView.setTextColor(Color.parseColor("#FFA500")) // Orange
                descriptionTextView.text =
                    "Based on your results, your stress level is moderate. You're handling things well, but it might be helpful to take short breaks during the day, practice relaxation techniques, or spend time doing things you enjoy."
            }

            STRESS_HIGH -> {
                categoryTextView.text = "HIGH STRESS (61–85%):\nWARNING: TAKE A BREAK SOON!"
                categoryTextView.setTextColor(Color.parseColor("#FF6347")) // Red-Orange
                descriptionTextView.text =
                    "Based on your results, your stress level is high. I recommend that you take a break soon, get some rest, and try to lighten your workload if possible. If stress continues, consider talking to a friend, family member, or professional."
            }

            STRESS_VERY_HIGH -> {
                categoryTextView.text = "VERY HIGH STRESS (86–100%):\nWARNING: TAKE A BREAK"
                categoryTextView.setTextColor(Color.parseColor("#B22222")) // Dark red
                descriptionTextView.text =
                    "Based on your results, your stress level is very high. It's important to prioritize your health — take a longer rest, disconnect from stressful environments, and if symptoms persist, please consult a doctor or mental health professional AS SOON AS POSSIBLE."
            }
        }

        // Set motivational footer
        footerTextView.text = "REMEMBER, YOU ARE ALWAYS DOING YOUR BEST."

        // Set click listener to dismiss the popup and return to the home/start screen
        homeButton.setOnClickListener {
            dialog?.dismiss()
            val intent = Intent(context, StartActivity::class.java)
            context.startActivity(intent)
        }

        // Show the final popup
        dialog?.show()
    }

    /**
     * Dismiss the dialog if it is currently showing.
     */
    fun dismiss() {
        if (dialog?.isShowing == true) {
            dialog?.dismiss()
        }
    }
}



