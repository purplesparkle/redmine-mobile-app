package data

import com.beust.klaxon.Json
import java.util.*

data class Issue(
    var id: Int,
    var subject: String,
    var description: String,
    @Json(name = "start_date")
    var startDate: Date,
    @Json(name = "due_date")
    var dueDate: Date?,
    @Json(name = "done_ratio")
    var doneRatio: Int,
    @Json(name = "is_private")
    var isPrivate: Boolean,
    @Json(name = "estimated_hours")
    var estimatedHours: Float?,
    @Json(name = "created_on")
    var createdOn: Date,
    @Json(name = "updated_on")
    var updatedOn: Date,
    @Json(name = "closed_on")
    var closedOn: Date?,
    @Json(path = "$.tracker.name")
    var tracker: String,
    @Json(path = "$.priority.name")
    var priority: String,
    @Json(path = "$.author.name")
    var author: String,
    @Json(path = "$.assigned_to.name")
    var assignedTo: String
)