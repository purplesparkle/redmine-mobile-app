package com.example.redminemobile.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.example.redminemobile.R
import com.example.redminemobile.services.ApiService
import com.example.redminemobile.services.StorageService
import okhttp3.Call
import okhttp3.Response
import java.io.IOException
import com.beust.klaxon.Klaxon
import com.example.redminemobile.models.UserData
import java.io.StringReader
import java.lang.Exception


class MainActivity : AppCompatActivity() {

    private var host: TextView? = null
    private var login: TextView? = null
    private var password: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.login = findViewById(R.id.authLogin) as TextView?
        this.password = findViewById(R.id.authPassword) as TextView?
        this.host = findViewById(R.id.host) as TextView?
        setDefaultValues()
    }

    private fun setDefaultValues()
    {
        val prefs = getSharedPreferences("Server", Context.MODE_PRIVATE)
        val keys = listOf("login", "password", "host")
        val values = StorageService().fetchData(prefs, keys)

        this.login?.text = values["login"]
        this.password?.text = values["password"]
        this.host?.text = values["host"]
    }

    fun singIn(view: View)
    {
        val prefs = getSharedPreferences("Server", Context.MODE_PRIVATE)
        val host = this.host?.text.toString()
        val log = this.login?.text.toString()
        val pass = this.password?.text.toString()

        val uriString = validURI(host)

        if (uriString != null && log.trim() != "" && pass.trim() != "")
        {
            ApiService(prefs).requestAuth(log, pass, host, object : okhttp3.Callback {
                override fun onFailure(call: Call, e: IOException){
                    notify(e.message.toString())
                }
                override fun onResponse(call: Call, response: Response){
                    when {
                        response.code() == 401 -> notify(getString(R.string.authError))
                        response.code() == 404 -> notify(getString(R.string.hostError))
                        else -> {
                            try {
                                val body = response.body()?.string()
                                val klaxon = Klaxon()
                                val parsed = klaxon.parseJsonObject(StringReader(body))
                                val dataObj = parsed.obj("user")
                                var output = UserData(
                                    id = dataObj?.int("id")!!.toInt(),
                                    apiKey = dataObj?.string("api_key")!!.toString()
                                )

                                val map: HashMap<String, String> = hashMapOf(
                                    "apiKey" to output.apiKey,
                                    "user_id" to output.id.toString(),
                                    "host" to host,
                                    "login" to log,
                                    "password" to pass)
                                StorageService().storeData(prefs, map)

                                var intent = Intent(this@MainActivity, ProjectActivity::class.java)
                                startActivity(intent)
                            }
                            catch (ex: Exception){
                                notify(getString(R.string.incorrectHost))
                            }
                        }
                    }
                }
            })
        }
        else
        {
            notify(getString(R.string.urlError))
        }
    }

    private fun notify(text: String){
        runOnUiThread{
            Toast.makeText(this,text, Toast.LENGTH_SHORT).show()
        }
    }

    private fun validURI(uriText: String?): String?{

        val regex = Regex(getString(R.string.urlPattern))
        val foundGroups = regex.find(uriText.toString())
        return if (foundGroups != null) foundGroups.groups[0]?.value
        else null
    }
}
