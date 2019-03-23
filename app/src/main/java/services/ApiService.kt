package com.example.redmineapp.services

import android.content.SharedPreferences
import com.example.redmineapp.data.IssueResponse
import com.example.redmineapp.data.ProjectResponse
import okhttp3.*
import java.io.IOException
//import kotlinx.serialization.*
//import kotlinx.serialization.json.JSON

class ApiService(private val prefs: SharedPreferences)
{
    private val client = OkHttpClient()

    private fun createBaseBuilder(url: String): Request.Builder{
        var apiKey = StorageService().fetchByKey(prefs, "apiKey")
        return Request.Builder()
            .url(url)
            .addHeader("Authorization", apiKey)
    }

    fun getAllProjects(host: String): ProjectResponse?
    {
        val url = host.plus("/projects.json")
        val request = createBaseBuilder(url).build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {}
            override fun onResponse(call: Call, response: Response) {
                response.body()?.string()
            }
        })

        return null
    }

    fun getAllIssues(host:String, projectId: Int): IssueResponse?
    {
        throw NotImplementedError()
    }

    fun setTime(time: Float, issueId: Int): Boolean
    {
        throw NotImplementedError()
    }
}

