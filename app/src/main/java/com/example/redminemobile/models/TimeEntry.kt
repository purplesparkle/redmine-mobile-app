package com.example.redminemobile.models

import com.beust.klaxon.Json

data class TimeEntry(
    var id: Int,
    var hours: Float,
    var comments: String,
    @Json(name = "user")
    var user: KeyValue? = null
//    var spentOn: Date,
//    var createdOn: Date,
//    var updatedOn: Date,
//    @Json(path = "$.activity.name")
//    var activity: String
)
