package com.example.redminemobile.adapters

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.redminemobile.R
import com.example.redminemobile.extensions.convertRedmineDateTime
import com.example.redminemobile.models.Project

class ProjectsListViewAdapter(private var activity: Activity, private var items: ArrayList<Project>)
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
       viewHolder.projectDescription?.text =
           if (project.description!!.trim() != "") project.description
           else "Описание отсутствует"
       viewHolder.projectDate?.text = project.createdOn?.convertRedmineDateTime()
       return view as View
   }

    class ViewHolder(row: View?) {
        var projectSubject: TextView? = null
        var projectDescription: TextView? = null
        var projectDate: TextView? = null

       init {
           this.projectSubject = row?.findViewById(R.id.projectName) as TextView?
           this.projectDescription = row?.findViewById(R.id.projectDescription) as TextView?
           this.projectDate = row?.findViewById(R.id.projectDate) as TextView?
       }
   }
}