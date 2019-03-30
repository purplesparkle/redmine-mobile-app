package com.example.redmineapp.services

import android.content.SharedPreferences
import com.beust.klaxon.JsonArray
import com.beust.klaxon.Klaxon
import okhttp3.*
import java.io.StringReader
import android.support.v4.media.MediaDescriptionCompatApi21.Builder.build



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

    fun requestAuth(log: String, pass: String, callback: Callback): Call {
        val key = "host"
        var host = StorageService().fetchByKey(prefs, key)

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
}