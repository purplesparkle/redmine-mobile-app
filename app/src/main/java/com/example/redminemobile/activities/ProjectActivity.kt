package com.example.redminemobile.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.ListView
import android.widget.TextView
import com.example.redminemobile.R
import com.example.redminemobile.models.Project
import com.beust.klaxon.Klaxon
import com.example.redminemobile.adapters.ProjectsListViewAdapter
import com.example.redminemobile.services.ApiService
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException
import java.io.StringReader

class ProjectActivity : AppCompatActivity() {

    private var projectsPlaceholder: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project)
        setSupportActionBar(findViewById(R.id.toolbarTop) as Toolbar?)
        projectsPlaceholder = findViewById(R.id.projectsPlaceholder) as TextView?
        fillProjects()
    }

    private fun fillProjects()
    {
        val prefs = getSharedPreferences("Server", Context.MODE_PRIVATE)
        ApiService(prefs).getProjects("projects.json", object : Callback {
            override fun onFailure(call: Call, e: IOException) {}
            override fun onResponse(call: Call, response: Response) {
                val body = response.body()?.string()
                val klaxon = Klaxon()
                val parsed = klaxon.parseJsonObject(StringReader(body))
                val dataArray = parsed.array<Any>("projects")
                var output = dataArray?.let { klaxon.parseFromJsonArray<Project>(it) }
                if (output != null && output.count() != 0){
                    updateView(output)
                }
                else{
                    projectsPlaceholder?.text = getString(R.string.issuesNotifyEmpty)
                }
            }
        })
    }

    private fun updateView(items: List<Project>)
    {
        runOnUiThread {
            val listView = findViewById(R.id.listView) as ListView
            val adapter = ProjectsListViewAdapter(this, items)
            listView.adapter = adapter
            listView.setOnItemClickListener { parent, view, position, id ->
                val id = items[position].id
                var intent = Intent(this, IssueActivity::class.java)
                intent.putExtra("project_id", id)
                startActivity(intent)
            }
            adapter.notifyDataSetChanged()
            projectsPlaceholder?.visibility = View.GONE
        }
    }
}
