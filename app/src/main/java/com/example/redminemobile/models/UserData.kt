package com.example.redminemobile.models

import com.beust.klaxon.Json

data class UserData(
    var id: Int,
    @Json(name = "api_key")
    var apiKey: String
)