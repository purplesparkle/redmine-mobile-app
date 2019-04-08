package com.example.redminemobile.activities

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import com.example.redminemobile.services.ApiService
import com.example.redminemobile.R
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException
import android.widget.ArrayAdapter
import com.beust.klaxon.JsonReader
import com.beust.klaxon.Klaxon
import com.example.redminemobile.models.KeyValue
import java.io.StringReader

class AddEntryActivity : AppCompatActivity() {

    private var issueId = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_entry)
        issueId = intent.getIntExtra("issue_id",-1)
        val issue = findViewById(R.id.addEntryIssue) as TextView?
        issue?.text = issueId.toString()
        initSpinner()
    }

    fun initSpinner(){
        var dropdown = findViewById(R.id.spinner) as Spinner?

        val prefs = getSharedPreferences("Server", Context.MODE_PRIVATE)
        var itemList = emptyList<KeyValue>()
        ApiService(prefs).requestEntryActivities(callback =  object : Callback {
            override fun onFailure(call: Call, e: IOException) {}
            override fun onResponse(call: Call, response: Response) {
                val body = response.body()?.string()
                val klaxon = Klaxon()
                val parsed = klaxon.parseJsonObject(StringReader(body))
                val dataArray = parsed.array<Any>("time_entry_activities")
                var output = dataArray?.let { klaxon.parseFromJsonArray<KeyValue>(it) }
                itemList = output!!
            }
        })

        //val items = arrayOf(8, 9)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, itemList)
        dropdown?.adapter = adapter
    }

    fun sendRequest(view: View){
        val hoursView= findViewById(R.id.addEntryHours) as TextView?
        val comment = findViewById(R.id.addEntryComments) as TextView?
        var dropdown = findViewById(R.id.spinner) as Spinner?
        val activity = dropdown?.selectedItem as Int

        val hours = hoursView?.text.toString().toInt()

        val prefs = getSharedPreferences("Server", Context.MODE_PRIVATE)
        ApiService(prefs).createEntry(hours, issueId,comment?.text.toString(),activity,
            object : Callback {
                override fun onFailure(call: Call, e: IOException) {}
                override fun onResponse(call: Call, response: Response) {
                    if (response.code() == 201)
                        finish()
                    else{
                        var out = response.body().toString()
                        runOnUiThread{
                            Toast.makeText(this@AddEntryActivity,out,Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            })
    }
}