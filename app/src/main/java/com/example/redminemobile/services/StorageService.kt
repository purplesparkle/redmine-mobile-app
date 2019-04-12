package com.example.redminemobile.services

import android.content.SharedPreferences

class StorageService
{
    fun storeData(prefs: SharedPreferences, dict: HashMap<String, String>)
    {
        val editor = prefs.edit()
        for(i in dict){
            editor.putString(i.key, i.value)
        }
        editor.commit()
    }

    fun fetchData(prefs: SharedPreferences, keys: List<String>): HashMap<String, String>
    {
        val map: HashMap<String, String> = hashMapOf()

        for (key in keys)
        {
            val value =  prefs.getString(key, "")
            map[key] = value
        }
        return  map
    }

    fun fetchByKey(prefs: SharedPreferences, key: String): String{
        return prefs.getString(key, "")
    }
}

