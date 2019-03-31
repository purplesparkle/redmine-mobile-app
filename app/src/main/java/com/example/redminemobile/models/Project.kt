package com.example.redminemobile.models

import com.beust.klaxon.Json

data class Project(
    var id: Int,
    var name: String,
    var description: String? = null,
    @Json(name = "is_public")
    var isPublic: Boolean? = null,
    @Json(name = "created_on")
    var createdOn: String)