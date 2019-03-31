package com.example.redminemobile.services

import android.content.SharedPreferences
import okhttp3.*
import org.json.JSONObject
import org.json.JSONException
import okhttp3.RequestBody

class ApiService(private val prefs: SharedPreferences)
{
    private val client = OkHttpClient()

    private fun createBaseBuilder(apiCall: String): Request.Builder{
        val keys=  listOf("apiKey","host")
        var values = StorageService().fetchData(prefs, keys)
        val apiKey = values["apiKey"]
        val host = values["host"]

        val url = "$host/redmine/$apiCall"
        return Request.Builder()
            .url(url)
            .addHeader("X-Redmine-API-Key", apiKey)
    }

    fun requestAuth(log: String, pass: String, host: String, callback: Callback): Call {
        val url = "$host/redmine/users/current.json"
        val credential = Credentials.basic(log, pass)
        val request = Request.Builder()
            .url(url)
            .addHeader("Authorization", credential)
            .build()

        val call = client.newCall(request)
        call.enqueue(callback)
        return call
    }

    fun getProjects(urlPath: String, callback: Callback): Call {
        val request = createBaseBuilder(urlPath).build()
        var call = client.newCall(request)
        call.enqueue(callback)
        return call
    }

    fun createEntry(hours: Int, issue: Int, comments: String, activity: Int, callback: Callback): Call{
        val jsonObject = JSONObject()
        try {
            val props = JSONObject()
            props.put("hours", hours)
            props.put("issue_id", issue)
            props.put("comments", comments)
            props.put("activity_id", activity)
            jsonObject.put("time_entry",props)

        } catch (e: JSONException) {
            e.printStackTrace()
        }
        val JSON = MediaType.parse("application/json; charset=utf-8")
        val body = RequestBody.create(JSON, jsonObject.toString())

        var urlPath = "time_entries.json"
        val request = createBaseBuilder(urlPath).post(body).build()

        var call = client.newCall(request)
        call.enqueue(callback)
        return call
    }
}