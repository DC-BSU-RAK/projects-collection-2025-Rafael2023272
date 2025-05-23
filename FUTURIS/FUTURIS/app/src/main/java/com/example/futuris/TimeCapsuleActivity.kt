package com.example.futuris

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.futuris.model.Capsule
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.text.SimpleDateFormat
import java.util.*

class TimeCapsuleActivity : AppCompatActivity() {

    private lateinit var titleInput: EditText
    private lateinit var messageInput: EditText
    private lateinit var datePickerButton: Button
    private lateinit var saveButton: Button
    private var selectedDate: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_time_capsule)

        // Initialize views
        titleInput = findViewById(R.id.tvTitle)
        messageInput = findViewById(R.id.etMessage)
        datePickerButton = findViewById(R.id.etDate)
        saveButton = findViewById(R.id.btnSave)

        // Date picker dialog triggered when date button clicked
        datePickerButton.setOnClickListener { showDatePickerDialog() }

        // Save capsule on save button click
        saveButton.setOnClickListener { saveCapsule() }

        // Back button to return to StartActivity
        val backButton = findViewById<Button>(R.id.btnBack)
        backButton.setOnClickListener {
            finish() // finish() is enough here; no need to start StartActivity explicitly
        }
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val dpd = DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->
                val selected = Calendar.getInstance()
                selected.set(year, month, dayOfMonth)

                // Format date as yyyy-MM-dd and update button text
                selectedDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(selected.time)
                datePickerButton.text = selectedDate
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        dpd.show()
    }

    private fun saveCapsule() {
        val title = titleInput.text.toString().trim()
        val message = messageInput.text.toString().trim()

        // Validate input fields
        if (title.isEmpty() || message.isEmpty() || selectedDate == null) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        val newCapsule = Capsule(title, message, selectedDate!!)

        // Load existing capsules from SharedPreferences
        val prefs = getSharedPreferences("FuturisCapsules", Context.MODE_PRIVATE)
        val json = prefs.getString("capsule_list", null)
        val capsuleList: MutableList<Capsule> = if (json != null) {
            val type = object : TypeToken<MutableList<Capsule>>() {}.type
            Gson().fromJson(json, type)
        } else {
            mutableListOf()
        }

        // Add new capsule and save back to SharedPreferences
        capsuleList.add(newCapsule)
        prefs.edit().putString("capsule_list", Gson().toJson(capsuleList)).apply()

        // Show confirmation dialog
        AlertDialog.Builder(this)
            .setTitle("Success")
            .setMessage("Capsule Created!")
            .setCancelable(false)
            .setPositiveButton("OK") { _, _ ->
                finish()  // Close this activity and return to previous (StartActivity)
            }
            .show()
    }
}
