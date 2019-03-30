package com.example.redmineapp.data

import com.beust.klaxon.Json
import com.example.redmineapp.KeyValue
import java.text.SimpleDateFormat
import java.util.*

data class Issue(
    var id: Int,
    var subject: String,
    var description: String,
//    @Json(name = "start_date")
//    var startDate: Date,
//    @Json(name = "due_date")
//    var dueDate: Date?,
    @Json(name = "done_ratio")
    var doneRatio: Int?,
    @Json(name = "is_private")
    var isPrivate: Boolean,
    @Json(name = "estimated_hours")
    var estimatedHours: Float?,
    @Json(name = "created_on")
    var createdOn: String,
//    @Json(name = "updated_on")
//    var updatedOn: Date,
//    @Json(name = "closed_on")
//    var closedOn: Date?,
    var tracker: KeyValue? = null,
    var status: KeyValue? = null,
    var priority: KeyValue? = null,
    var author: KeyValue? = null,
    @Json(name = "assigned_to")
    var assignedTo: KeyValue? = null
)

val Issue.issueNameLabel: String get() {
    var id = this.id
    return "Задача №$id"
}

val Issue.usersNamesLabel: String get(){
    //TODO: implement logic based on current user id
    //var author = if (currentId == this.author.id) "мне" else "this.author.name"
    var author = this.author?.name
    var assigned = if (this.assignedTo != null) "; Назначен: " + assignedTo?.name else ""
    return "Автор: $author$assigned"
}
//val Issue.createdOnDate: String get() {
////    val parsedDate = this.createdOn.replace("T","").replace("Z","")
//    val tz = TimeZone.getTimeZone("UTC")
//    val df = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
//    df.timeZone = tz
//    val date = df.parse(this.createdOn)
//    val format = SimpleDateFormat("HH:mm dd.MM.yy")
//    return format.format(date)
//
//}