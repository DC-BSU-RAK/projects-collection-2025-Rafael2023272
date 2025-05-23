package com.example.futuris.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.futuris.R
import com.example.futuris.model.Capsule
import java.text.SimpleDateFormat
import java.util.*

// Adapter class for displaying a list of Capsules in a RecyclerView
class CapsuleAdapter(
    private val capsules: List<Capsule>,               // List of capsules to be displayed
    private val onDelete: (Capsule) -> Unit,           // Lambda function to handle deletion
    private val onView: (Capsule) -> Unit              // Lambda function to handle viewing
) : RecyclerView.Adapter<CapsuleAdapter.ViewHolder>() {

    // ViewHolder class to hold and bind each item view
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.tvTitle)
        val message: TextView = view.findViewById(R.id.etMessage)
        val unlockDate: TextView = view.findViewById(R.id.textUnlockDate)
        val deleteButton: Button = view.findViewById(R.id.btnDelete)
        val viewButton: Button = view.findViewById(R.id.btnView)
        val lockIcon: ImageView = view.findViewById(R.id.capsuleLockIcon)
    }

    // Inflates the layout for each item in the RecyclerView
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_capsule, parent, false)
        return ViewHolder(view)
    }

    // Binds data to each view holder based on the current position
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val capsule = capsules[position]
        holder.title.text = capsule.title
        holder.unlockDate.text = "Unlocks on: ${capsule.unlockDate}"

        // Check if the current date is on or after the unlock date
        val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        val isUnlocked = currentDate >= capsule.unlockDate

        if (isUnlocked) {
            // Show message if capsule is already unlocked
            holder.message.text = capsule.message
        } else {
            // Otherwise, show locked indicator
            holder.message.text = "🔒 Locked until ${capsule.unlockDate}"
        }

        // Delete button functionality – triggers the onDelete lambda
        holder.deleteButton.setOnClickListener {
            onDelete(capsule)
        }

        // View button functionality – shows toast if locked, or opens if unlocked
        holder.viewButton.setOnClickListener {
            val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(
                SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
            )
            val unlockDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(capsule.unlockDate)

            if (currentDate != null && unlockDate != null && currentDate.before(unlockDate)) {
                // If still locked, show message
                Toast.makeText(
                    holder.itemView.context,
                    "This capsule is still locked until ${capsule.unlockDate}",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                // If unlocked, trigger the view function
                onView(capsule)
            }
        }
    }

    // Returns the total number of capsules to be shown
    override fun getItemCount(): Int = capsules.size
}
