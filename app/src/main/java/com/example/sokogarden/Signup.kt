package com.example.sokogarden

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.loopj.android.http.RequestParams

class Signup : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_signup)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // find views
        val username = findViewById<EditText>(R.id.username)
        val email = findViewById<EditText>(R.id.email)
        val password = findViewById<EditText>(R.id.password)
        val phone = findViewById<EditText>(R.id.phone)
        val signupButton = findViewById<Button>(R.id.signupbtn)
        val signinTextView = findViewById<TextView>(R.id.signintxt)

        // navigate to signin
        signinTextView.setOnClickListener {
            val intent = Intent(applicationContext, Signin::class.java)
            startActivity(intent)
        }

        // signup button
        signupButton.setOnClickListener {

            // get values
            val usernameText = username.text.toString().trim()
            val emailText = email.text.toString().trim()
            val passwordText = password.text.toString().trim()
            val phoneText = phone.text.toString().trim()

            // 🔴 validation
            if (usernameText.isEmpty()) {
                username.error = "Username is required"
                username.requestFocus()
                return@setOnClickListener
            }

            if (emailText.isEmpty()) {
                email.error = "Email is required"
                email.requestFocus()
                return@setOnClickListener
            }

            if (passwordText.isEmpty()) {
                password.error = "Password is required"
                password.requestFocus()
                return@setOnClickListener
            }

            if (phoneText.isEmpty()) {
                phone.error = "Phone number is required"
                phone.requestFocus()
                return@setOnClickListener
            }

            // API
            val api = "https://slyney2248.alwaysdata.net/api/signup"

            val data = RequestParams()
            data.put("username", usernameText)
            data.put("email", emailText)
            data.put("password", passwordText)
            data.put("phone", phoneText)

            val helper = ApiHelper(applicationContext)

            // 🔥 ONLY navigate after success
            helper.post(api, data) { success, message ->

                if (success) {
                    Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()

                    // ⏳ delay before navigating
                    android.os.Handler().postDelayed({

                        // clear fields
                        username.text.clear()
                        email.text.clear()
                        password.text.clear()
                        phone.text.clear()

                        // navigate AFTER message is seen
                        val intent = Intent(applicationContext, Signin::class.java)
                        startActivity(intent)

                    }, 3000) // 2 seconds delay

                } else {
                    Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}