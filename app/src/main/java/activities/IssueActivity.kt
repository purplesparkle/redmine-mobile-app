package com.example.redmineapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ListView
import android.widget.TextView
import com.beust.klaxon.Klaxon
import com.example.redmineapp.adapters.IssuesListViewAdapter
import com.example.redmineapp.data.Issue
import com.example.redmineapp.services.ApiService
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException
import java.io.StringReader

class IssueActivity : AppCompatActivity() {

    private var entriesPlaceholder: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_issue)
        val projectId = intent.getIntExtra("project_id",-1)
        entriesPlaceholder = findViewById(R.id.entriesPlaceholder) as TextView?
        getIssues(projectId)
    }

    private fun getIssues(projectId: Int)
    {
        val prefs = getSharedPreferences("Server", Context.MODE_PRIVATE)
        ApiService(prefs).getProjects("issues.json?project_id=$projectId", object : Callback {
            override fun onFailure(call: Call, e: IOException) {}
            override fun onResponse(call: Call, response: Response) {
                val body = response.body()?.string()
                val klaxon = Klaxon()
                val parsed = klaxon.parseJsonObject(StringReader(body))
                val dataArray = parsed.array<Any>("issues")
                var output = dataArray?.let { klaxon.parseFromJsonArray<Issue>(it) }
                if (output != null && output.count() != 0){
                    updateView(output)
                }
                else{
                    entriesPlaceholder?.text = getString(R.string.issuesNotifyEmpty)
                }
            }
        })
    }

    private fun updateView(items: List<Issue>)
    {
        runOnUiThread {
            val listView = findViewById(R.id.listView) as ListView
            val adapter = IssuesListViewAdapter(this, items)
            listView.adapter = adapter
            listView.setOnItemClickListener { parent, view, position, id ->
                val id = items[position].id
                var intent = Intent(this, TimeEntryActivity::class.java)
                intent.putExtra("issue_id", id)
                startActivity(intent)
            }
            adapter.notifyDataSetChanged()
            entriesPlaceholder?.visibility = View.GONE
        }
    }
}
