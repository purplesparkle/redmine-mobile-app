package com.example.redmineapp.data

import com.beust.klaxon.Json
import java.util.*

data class Project(
    var id: Int,
    var name: String,
    var identifier: String?,
    var description: String?,
     var status: Int?
//    @Json(name = "is_public")
//    var isPublic: Boolean?,
//    @Json(name = "created_on", ignored = true)
//    var createdOn: Date,
//    @Json(name = "created_on", ignored = true)
//    var updated_on: Date
)