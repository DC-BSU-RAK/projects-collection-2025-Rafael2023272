package com.example.futuris

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.futuris.adapter.CapsuleAdapter
import com.example.futuris.model.Capsule
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.text.SimpleDateFormat
import java.util.*

class ViewCapsulesActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView        // Displays list of capsules
    private lateinit var emptyText: TextView               // Shows message if no capsules exist
    private lateinit var backButton: Button                 // Button to go back to previous screen
    private lateinit var adapter: CapsuleAdapter            // Adapter for managing RecyclerView items
    private var capsuleList = mutableListOf<Capsule>()      // In-memory list of saved capsules

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_capsules)

        // Initialize UI components by finding their views
        recyclerView = findViewById(R.id.capsuleRecyclerView)
        emptyText = findViewById(R.id.emptyText)
        backButton = findViewById(R.id.btnBack)

        // Set RecyclerView to display items vertically
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Load saved capsules from SharedPreferences (persistent storage)
        capsuleList = loadCapsules()

        // Set up adapter with data and define behavior for deleting and viewing capsules
        adapter = CapsuleAdapter(
            capsules = capsuleList,
            onDelete = { capsule -> deleteCapsule(capsule) },  // Callback to delete capsule
            onView = { capsule -> viewCapsule(capsule) }       // Callback to view capsule details
        )
        recyclerView.adapter = adapter

        // Show appropriate UI if no capsules are available
        if (capsuleList.isEmpty()) {
            recyclerView.visibility = RecyclerView.GONE
            emptyText.visibility = TextView.VISIBLE
        } else {
            recyclerView.visibility = RecyclerView.VISIBLE
            emptyText.visibility = TextView.GONE
        }

        // Finish activity and return when back button clicked
        backButton.setOnClickListener {
            finish()
        }
    }

    // Loads the saved capsules JSON string and converts it to a list of Capsule objects
    private fun loadCapsules(): MutableList<Capsule> {
        val prefs = getSharedPreferences("FuturisCapsules", Context.MODE_PRIVATE)
        val json = prefs.getString("capsule_list", null)
        return if (json != null) {
            val type = object : TypeToken<MutableList<Capsule>>() {}.type
            Gson().fromJson(json, type) // Deserialize JSON to list
        } else {
            mutableListOf() // Return empty list if none saved
        }
    }

    // Deletes a capsule from the list, updates storage, and refreshes the UI
    private fun deleteCapsule(capsule: Capsule) {
        capsuleList.remove(capsule)

        // Save updated list back to SharedPreferences as JSON
        val prefs = getSharedPreferences("FuturisCapsules", Context.MODE_PRIVATE)
        prefs.edit().putString("capsule_list", Gson().toJson(capsuleList)).apply()

        adapter.notifyDataSetChanged() // Notify adapter to refresh RecyclerView

        // If no capsules left, show empty text message and hide RecyclerView
        if (capsuleList.isEmpty()) {
            recyclerView.visibility = RecyclerView.GONE
            emptyText.visibility = TextView.VISIBLE
        }
    }

    // Handles viewing a capsule's message only if the unlock date is reached or passed
    private fun viewCapsule(capsule: Capsule) {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val currentDate = dateFormat.parse(dateFormat.format(Date())) // Today’s date with no time
        val unlockDate = dateFormat.parse(capsule.unlockDate)         // Capsule unlock date

        // Allow access only if currentDate >= unlockDate
        if (currentDate != null && unlockDate != null && !currentDate.before(unlockDate)) {
            // Pass capsule data to detail screen via intent extras
            val intent = Intent(this, CapsuleDetailActivity::class.java).apply {
                putExtra("title", capsule.title)
                putExtra("unlockDate", capsule.unlockDate)
                putExtra("message", capsule.message)
            }
            startActivity(intent)
        } else {
            // Show toast informing user capsule is still locked until unlock date
            Toast.makeText(this, "This capsule is locked until ${capsule.unlockDate}", Toast.LENGTH_SHORT).show()
        }
    }
}


