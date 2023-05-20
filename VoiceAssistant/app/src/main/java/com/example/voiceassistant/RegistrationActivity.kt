package com.example.voiceassistant

import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.github.kittinunf.fuel.core.extensions.jsonBody
import com.github.kittinunf.fuel.httpPost

class RegistrationActivity : AppCompatActivity() {

    private lateinit var loginEditText: EditText
    private lateinit var passEditText: EditText
    private lateinit var nameTextField: EditText
    private lateinit var emailTextField: EditText
    private lateinit var submitButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        if (Build.VERSION.SDK_INT > 9) {
            val gfgPolicy = StrictMode.ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(gfgPolicy)
        }

        loginEditText = findViewById(R.id.userNameTextField)
        passEditText = findViewById(R.id.passwordTextField)
        nameTextField = findViewById(R.id.nameTextField)
        emailTextField = findViewById(R.id.emailTextField)
        submitButton = findViewById(R.id.submitButton)

        submitButton.setOnClickListener{
            if(loginEditText.text != null && passEditText.text != null && nameTextField.text != null && emailTextField.text != null){

                    val (_, _, result) = "http://192.168.0.102:5000/db".httpPost()
                        .jsonBody("{\"email\":\"${emailTextField.text}\",\"login\":\"${loginEditText.text}\",\"name\":\"${nameTextField.text}\",\"password\":\"${passEditText.text}\"}")
                        .responseString()

                setContentView(R.layout.activity_auth)
            }
        }
    }
}