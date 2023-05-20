package com.example.voiceassistant

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import java.net.URL


data class JsonDataBase(val email: String, val id: Int, val login: String, val password:String, val name: String )

class AuthActivity : AppCompatActivity() {
    private lateinit var loginEditText: EditText
    private lateinit var passEditText: EditText

    private lateinit var submitButton: Button

    var pref : SharedPreferences? = null

    lateinit var userList: List<JsonDataBase>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        pref = getSharedPreferences("TABLE", Context.MODE_PRIVATE)

        var login:String = pref?.getString("login", "")!!
        var password:String = pref?.getString("password", "")!!


        if (Build.VERSION.SDK_INT > 9) {
            val gfgPolicy = StrictMode.ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(gfgPolicy)
        }

        submitButton = findViewById(R.id.submitButton)
        loginEditText = findViewById(R.id.userNameTextField)
        passEditText = findViewById(R.id.passwordTextField)

        if(login != "" && password != "")
            setContentView(R.layout.activity_main)

        submitButton.setOnClickListener{

            if(loginEditText.text != null && passEditText.text != null){

                userList = GetRequest()

                for ( i in userList){
                    if(loginEditText.text.toString() == i.login && passEditText.text.toString() == i.password){
                        saveData(loginEditText.text.toString(), passEditText.text.toString())

                        setContentView(R.layout.activity_main)
                    }
                }
            }
        }
    }

    fun saveData(login: String, password: String)
    {
        val editor = pref?.edit()
        editor?.putString("login", login)
        editor?.putString("password", password)
        editor?.apply()

    }

    fun deleteAll()
    {  //Что такое искуственный интелект?
        val editor = pref?.edit()
        editor?.clear()
        editor?.apply()
    }

    fun signUP(view: View?){
        val myIntent = Intent(view!!.context, RegistrationActivity::class.java)
        startActivityForResult(myIntent, 0)
    }

    private fun GetRequest(): List<JsonDataBase> {
        val webPage = URL("http://192.168.0.106:5000")
        val data = webPage.readText()

        val gson = Gson()
        val userList = gson.fromJson(data, Array<JsonDataBase>::class.java).asList()

        return userList
    }
}