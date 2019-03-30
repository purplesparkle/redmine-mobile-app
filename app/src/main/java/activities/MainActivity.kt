package com.example.redmineapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.example.redmineapp.services.ApiService
import com.example.redmineapp.services.StorageService
import okhttp3.Call
import okhttp3.Response
import java.io.IOException
import javax.security.auth.callback.Callback
import android.widget.ListView
import com.beust.klaxon.Klaxon
import data.AuthEntity
import java.io.StringReader


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

        val uriString = validURI(host)

        if (uriString != null)
        {
            var self = this
            //вписать здесь свои данные с изменённой формы
            ApiService(prefs).requestAuth("n.filatov", "qwerty123", object : okhttp3.Callback {
                override fun onFailure(call: Call, e: IOException){
                    //в этом месте почему-то падает ошибка, хоть это и обработка падения запроса (не ясно...)
                    val badURIToast = Toast.makeText(self, e.message, Toast.LENGTH_SHORT)
                    badURIToast.show()
                }
                override fun onResponse(call: Call, response: Response){
                    val body = response.body()?.string()
                    val klaxon = Klaxon()
                    val parsed = klaxon.parseJsonObject(StringReader(body))
                    val dataObj = parsed.obj("user")
                    var cred = AuthEntity(
                        id = dataObj?.int("id")!!.toInt(),
                        apiKey = dataObj?.string("api_key")!!.toString()
                    )

                    val map: HashMap<String, String> = hashMapOf("apiKey" to key.toString(), "host" to uriString)
                    StorageService().storeData(prefs, map)

                    var intent = Intent(self, ProjectActivity::class.java)
                    startActivity(intent)
                }
            })
        }
        else
        {
            val badURIToast = Toast.makeText(this,"Требуется указать протокол для URI (http(s)://)", Toast.LENGTH_SHORT)
            badURIToast.show()
        }
    }

    fun validURI(uriText: CharSequence?): String?{
        val regex = Regex("""http(s)?://.*""")
        val foundGroups = regex.find(uriText.toString())
        if (foundGroups != null) return foundGroups.groups[0]?.value
        else return null
    }

    fun checkConnection(apiKey: String, host: String){
        NotImplementedError()
    }
}
