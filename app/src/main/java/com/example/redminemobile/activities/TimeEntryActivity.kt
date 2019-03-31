package com.example.redminemobile.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ListView
import android.widget.TextView
import com.beust.klaxon.Klaxon
import com.example.redminemobile.adapters.TimeEntriesListViewAdapter
import com.example.redminemobile.models.TimeEntry
import com.example.redminemobile.services.ApiService
import com.example.redminemobile.R
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException
import java.io.StringReader

class TimeEntryActivity : AppCompatActivity() {

    private var issueId: Int = -1
    private var entriesPlaceholder: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entry)
        issueId = intent.getIntExtra("issue_id",-1)
        entriesPlaceholder = findViewById(R.id.entriesPlaceholder) as TextView?
        fillEntries(issueId)
    }

    private fun fillEntries(issueId: Int)
    {
        val prefs = getSharedPreferences("Server", Context.MODE_PRIVATE)
        ApiService(prefs).getProjects("time_entries.json?issue_id=$issueId", object : Callback {
            override fun onFailure(call: Call, e: IOException) {}
            override fun onResponse(call: Call, response: Response) {
                val body = response.body()?.string()
                val klaxon = Klaxon()
                val parsed = klaxon.parseJsonObject(StringReader(body))
                val dataArray = parsed.array<Any>("time_entries")
                var output = dataArray?.let { klaxon.parseFromJsonArray<TimeEntry>(it) }
                if (output != null && output.count() != 0){
                    updateView(output)
                }
                else{
                    entriesPlaceholder?.text = getString(R.string.entriesNotifyEmpty)
                }
            }
        })
    }

    private fun updateView(items: List<TimeEntry>)
    {
        runOnUiThread {
            val listView = findViewById(R.id.listView) as ListView
            val adapter = TimeEntriesListViewAdapter(this, items)
            listView.adapter = adapter
            listView.setOnItemClickListener { parent, view, position, id ->
                var intent = Intent(this, AddEntryActivity::class.java)
                intent.putExtra("issue_id", issueId)
                startActivity(intent)
            }
            adapter.notifyDataSetChanged()
            entriesPlaceholder?.visibility = View.GONE
        }
    }
}