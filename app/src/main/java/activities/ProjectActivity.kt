package com.example.redmineapp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ListView
import com.example.redmineapp.adapters.ListViewAdapter
import com.example.redmineapp.data.Project
import android.widget.Toast

class ProjectActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project)

        val listView = findViewById(R.id.listView) as ListView
        val items = ArrayList<Project>()
        val first = Project("Javascript project","Yet another framework", status = 1)
        val second = Project(".Net core project","Netcore scalable applications with DI", status = 2)
        items.add(first)
        items.add(second)
        val adapter = ListViewAdapter(this, items)
        listView.adapter = adapter
        listView.setOnItemClickListener { parent, view, position, id ->
            Toast.makeText(this, items[position].name, Toast.LENGTH_SHORT).show()
        }
        adapter.notifyDataSetChanged()
    }

    fun fillProjets()
    {
        throw NotImplementedError()
    }
}
