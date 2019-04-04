package com.example.redminemobile.adapters

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.redminemobile.R
import com.example.redminemobile.models.TimeEntry

class TimeEntriesListViewAdapter(private var activity: Activity, private var items: ArrayList<TimeEntry>)
    : BaseListViewAdapter<TimeEntry>(activity, items) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View?
        val viewHolder: ViewHolder
        if (convertView == null) {
            val inflater = activity?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.entry_item, null)
            viewHolder = ViewHolder(view)
            view?.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        var entry = items[position]
        viewHolder.entryHours?.text = entry.hours.toString()
        viewHolder.entryComment?.text = entry.comments

        return view as View
    }

    class ViewHolder(row: View?) {
        var entryHours: TextView? = null
        var entryComment: TextView? = null

        init {
            this.entryHours = row?.findViewById(R.id.entryHours) as TextView?
            this.entryComment = row?.findViewById(R.id.entryComment) as TextView?
        }
    }
}