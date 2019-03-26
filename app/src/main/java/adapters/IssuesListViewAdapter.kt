package com.example.redmineapp.adapters

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.redmineapp.R
import com.example.redmineapp.data.Issue
import com.example.redmineapp.data.Project

class IssuesListViewAdapter(private var activity: Activity, private var items: List<Issue>)
    : BaseListViewAdapter<Issue>(activity, items) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View?
        val viewHolder: ViewHolder
        if (convertView == null) {
            val inflater = activity?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.issue_item, null)
            viewHolder = ViewHolder(view)
            view?.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        var project = items[position]
        viewHolder.issueSubject?.text = project.subject
        viewHolder.issueDescription?.text = project.description
        return view as View
    }

    class ViewHolder(row: View?) {
        var issueSubject: TextView? = null
        var issueDescription: TextView? = null

        init {
            this.issueSubject = row?.findViewById(R.id.issueSubject) as TextView?
            this.issueDescription = row?.findViewById(R.id.issueDescription) as TextView?
        }
    }
}