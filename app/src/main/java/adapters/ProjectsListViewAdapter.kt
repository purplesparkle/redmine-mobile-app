package com.example.redmineapp.adapters

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.redmineapp.R
import com.example.redmineapp.data.Project

class ProjectsListViewAdapter(private var activity: Activity, private var items: List<Project>)
    : BaseListViewAdapter<Project>(activity, items) {

   override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
       val view: View?
       val viewHolder: ViewHolder
       if (convertView == null) {
           val inflater = activity?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
           view = inflater.inflate(R.layout.project_item, null)
           viewHolder = ViewHolder(view)
           view?.tag = viewHolder
       } else {
           view = convertView
           viewHolder = view.tag as ViewHolder
       }

       var project = items[position]
       viewHolder.projectSubject?.text = project.name
       viewHolder.projectDescription?.text = project.description

       return view as View
   }

   class ViewHolder(row: View?) {
       var projectSubject: TextView? = null
       var projectDescription: TextView? = null

       init {
           this.projectSubject = row?.findViewById(R.id.projectName) as TextView?
           this.projectDescription = row?.findViewById(R.id.projectDescription) as TextView?
       }
   }
}