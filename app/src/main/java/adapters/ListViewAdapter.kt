package com.example.redmineapp.adapters

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.redmineapp.R
import com.example.redmineapp.data.Project

class ListViewAdapter(private var activity: Activity, private var items: List<Project>): BaseAdapter(){

    override fun getItem(i: Int): Project {
        return items[i]
    }

    override fun getItemId(i: Int): Long {
        return i.toLong()
    }

    override fun getCount(): Int {
        return items.size
    }

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

        val project = items[position]
        viewHolder.txtName?.text = project.name
        viewHolder.txtComment?.text = project.description
        return view as View
    }

    private class ViewHolder(row: View?) {
        var txtName: TextView? = null
        var txtComment: TextView? = null

        init {
            this.txtName = row?.findViewById(R.id.txtName) as TextView?
            this.txtComment = row?.findViewById(R.id.txtComment) as TextView?
        }
    }
}