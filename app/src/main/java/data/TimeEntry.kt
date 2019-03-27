package com.example.redmineapp.data

import com.beust.klaxon.Json
import java.util.*

data class TimeEntry(
    var id: Int,
    var hours: Float,
    var comments: String
//    var spentOn: Date,
//    var createdOn: Date,
//    var updatedOn: Date,
//    @Json(path = "$.activity.name")
//    var activity: String
)
