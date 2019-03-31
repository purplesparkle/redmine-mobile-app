package com.example.redminemobile.adapters

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.redminemobile.R
import com.example.redminemobile.models.Issue
import com.example.redminemobile.models.issueNameLabel
import com.example.redminemobile.models.usersNamesLabel
import com.example.redminemobile.extensions.convertRedmineDateTime

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
        viewHolder.issueSubject?.text = project.issueNameLabel
        viewHolder.issueDescription?.text = project.subject
        viewHolder.issueDate?.text = project.createdOn.convertRedmineDateTime()
        viewHolder.issueNames?.text = project.usersNamesLabel
        viewHolder.issueStatus?.text = project?.status?.name
        return view as View
    }

    class ViewHolder(row: View?) {
        var issueSubject: TextView? = null
        var issueDescription: TextView? = null
        var issueNames: TextView? = null
        var issueDate: TextView? = null
        var issueStatus: TextView? = null

        init {
            this.issueSubject = row?.findViewById(R.id.issueSubject) as TextView?
            this.issueDescription = row?.findViewById(R.id.issueDescription) as TextView?
            this.issueNames = row?.findViewById(R.id.issueNames) as TextView?
            this.issueDate = row?.findViewById(R.id.issueDate) as TextView?
            this.issueStatus = row?.findViewById(R.id.issueStatus) as TextView?
        }
    }
}