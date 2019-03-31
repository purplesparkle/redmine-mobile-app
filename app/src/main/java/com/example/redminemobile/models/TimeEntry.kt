package com.example.redminemobile.models

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
