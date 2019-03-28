package com.example.redmineapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.example.redmineapp.services.StorageService


class MainActivity : AppCompatActivity() {

    private var apiKey: TextView? = null
    private var host: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.apiKey= findViewById(R.id.apiKey) as TextView?
        this.host = findViewById(R.id.host) as TextView?
        setDefaultValues()
    }

    private fun setDefaultValues()
    {
        val prefs = getSharedPreferences("Server", Context.MODE_PRIVATE)
        val keys = listOf("apiKey", "host")
        val values = StorageService().fetchData(prefs, keys)

        this.apiKey?.text = values["apiKey"]
        this.host?.text = values["host"]
    }

    fun singIn(view: View)
    {
        val prefs = getSharedPreferences("Server", Context.MODE_PRIVATE)
        val key = if (this.apiKey != null) this.apiKey?.text else ""
        val host = if (this.host != null) this.host?.text else ""

        val regex = Regex("""http(s)?://.*""")
        val foundGroups = regex.find(host.toString())

        if (foundGroups != null)
        {
            val str = foundGroups.groups[0]?.value
            val map: HashMap<String, String> = hashMapOf("apiKey" to key.toString(), "host" to host.toString())
            StorageService().storeData(prefs, map)

            var intent = Intent(this, ProjectActivity::class.java)
            startActivity(intent)
        }
        else
        {
            val badURIToast = Toast.makeText(this,"Требуется указать протокол для URI (http(s)://)", Toast.LENGTH_SHORT)
            badURIToast.show()
        }
    }

    fun checkConnection(apiKey: String, host: String){
        NotImplementedError()
    }
}
