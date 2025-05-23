// Defines the package name where this class is located
package com.example.futuris.model

/**
 * A simple data class representing a time capsule.
 * Each capsule contains a title, a message, and a date when it will be unlocked.
 */
data class Capsule(
    val title: String,      // The title or name of the capsule (e.g., "Graduation Letter")
    val message: String,    // The main content or message inside the capsule
    val unlockDate: String  // The future date (in String format) when the capsule is set to be unlocked
)


