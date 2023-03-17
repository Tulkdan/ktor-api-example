package com.example.user.models

import kotlinx.serialization.Serializable

@Serializable
data class User(val username: String, val passwords: String)

@Serializable
data class LoginResponse(val accessToken: String)
