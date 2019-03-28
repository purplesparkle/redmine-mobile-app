package com.example.redmineapp.services

import android.content.SharedPreferences
import com.beust.klaxon.JsonArray
import com.beust.klaxon.Klaxon
import okhttp3.*
import java.io.StringReader

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

    fun tryAuth(url: String){

    }

    fun getProjects(urlPath: String, callback: Callback): Call {
        val request = createBaseBuilder(urlPath).build()
        var call = client.newCall(request)
        call.enqueue(callback)
        return call
    }
}

