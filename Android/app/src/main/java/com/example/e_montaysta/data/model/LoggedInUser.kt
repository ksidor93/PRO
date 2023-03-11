package com.example.e_montaysta.data.model

/**
 * Data class that captures user information for logged in users retrieved from AuthController
 */
data class LoggedInUser(
    val userId: String,
    val displayName: String,
    val role: String?
)