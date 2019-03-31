package com.example.redminemobile.adapters

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter

open class BaseListViewAdapter<T>(private var activity: Activity, private var items: List<T>): BaseAdapter(){

    override fun getItem(i: Int): T {
        return items[i]
    }

    override fun getItemId(i: Int): Long {
        return i.toLong()
    }

    override fun getCount(): Int {
        return items.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return convertView as View
    }
}