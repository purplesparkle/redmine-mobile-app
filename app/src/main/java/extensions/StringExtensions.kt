package extensions

import com.example.redmineapp.data.Issue
import java.text.SimpleDateFormat
import java.util.*

fun String.convertRedmineDateTime(): String{
    val tz = TimeZone.getTimeZone("UTC")
    val df = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
    df.timeZone = tz
    val date = df.parse(this)
    val format = SimpleDateFormat("HH:mm dd.MM.yy")
    return format.format(date)
}