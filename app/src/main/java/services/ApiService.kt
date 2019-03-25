package com.example.redmineapp.services

import android.content.SharedPreferences
import com.beust.klaxon.Klaxon
import com.beust.klaxon.PathMatcher
import data.RedmineResponse
import okhttp3.*
import java.io.IOException
import java.io.StringReader
import java.util.regex.Pattern

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
            .addHeader("Authorization", apiKey)
    }

    fun getProjects(callback: Callback): Call {
        val request = createBaseBuilder("projects.json").build()
        var call = client.newCall(request)
        call.enqueue(callback)
        return call
    }
}

