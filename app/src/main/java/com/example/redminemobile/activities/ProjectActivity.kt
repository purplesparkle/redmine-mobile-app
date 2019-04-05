package com.example.redminemobile.activities

import android.content.Context
import android.content.Intent
import android.opengl.Visibility
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.Button
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
    private var loadMoreButton: Button? = null
    private var listView: ListView? = null
    private var quantity = 3
    private var offset = 0
    private var isAdapterCreated = false
    private val mSwipeRefreshLayout: android.support.v4.widget.SwipeRefreshLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project)
        setSupportActionBar(findViewById(R.id.toolbarTop) as Toolbar?)
        loadMoreButton = findViewById(R.id.loadMoreButton) as Button?
        projectsPlaceholder = findViewById(R.id.projectsPlaceholder) as TextView?
        listView = findViewById(R.id.listView) as ListView?
        fillProjects()
        val mSwipeRefreshLayout = findViewById(R.id.swipeRefreshProject) as SwipeRefreshLayout
        mSwipeRefreshLayout.setOnRefreshListener{
            fillProjects()
            mSwipeRefreshLayout.isRefreshing = false
        }
    }

    private fun fillProjects()
    {
        val prefs = getSharedPreferences("Server", Context.MODE_PRIVATE)
        ApiService(prefs).requestGet("projects.json",offset, quantity, callback =  object : Callback {
            override fun onFailure(call: Call, e: IOException) {}
            override fun onResponse(call: Call, response: Response) {
                val body = response.body()?.string()
                val klaxon = Klaxon()
                val parsed = klaxon.parseJsonObject(StringReader(body))
                val dataArray = parsed.array<Any>("projects")
                var output = dataArray?.let { klaxon.parseFromJsonArray<Project>(it) }
                if (output != null && output.count() != 0){
                    updateView(ArrayList(output))
                }
                else if (offset == 0){
                    projectsPlaceholder?.text = getString(R.string.issuesNotifyEmpty)
                }
            }
        })
    }

    private fun updateView(items: ArrayList<Project>)
    {
        runOnUiThread{
            var localItems: ArrayList<Project> = items
            if (items.count() == quantity){
                offset += quantity -1
                localItems = ArrayList(items.dropLast(1))
            }
            else
            {
                loadMoreButton?.visibility = View.GONE
            }

            if (isAdapterCreated){
                addItemsToAdapter(localItems)
            }
            else{
                createAdapter(localItems)
            }
        }
    }

    private fun createAdapter(items: ArrayList<Project>){
        val adapter = ProjectsListViewAdapter(this, items)
        listView?.adapter = adapter
        listView?.setOnItemClickListener { parent, view, position, id ->
            val id = items[position].id
            var intent = Intent(this, IssueActivity::class.java)
            intent.putExtra("project_id", id)
            startActivity(intent)
        }
        adapter.notifyDataSetChanged()
        projectsPlaceholder?.visibility = View.GONE
        isAdapterCreated = true

    }

    private fun addItemsToAdapter(projects: ArrayList<Project>){
        val adapter = listView?.adapter as ProjectsListViewAdapter
        adapter.addItems(projects)
    }

    fun loadMore(view: View?){
        fillProjects()
    }
}
