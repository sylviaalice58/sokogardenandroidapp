package com.example.sokogarden

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.loopj.android.http.RequestParams

class Signin : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_signin)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
//        find the two edit text ,button, and a text view by use of there ids
        val email = findViewById<EditText>(R.id.email)
        val password = findViewById<EditText>(R.id.password)
        val signinButton = findViewById<Button>(R.id.signinbtn)
        val signupTextView = findViewById<TextView>(R.id.signuptxt)

//      On th text view set onclick listener such that when clicked iy navigates u o the signup page
        signupTextView.setOnClickListener {
            val intent = Intent(applicationContext, Signup :: class.java)
            startActivity(intent)
        }

//        on click of the button sign in we need to interact with out api endpoint as we pass the two datas require ei email and password
        signinButton.setOnClickListener {
//            specify the api endpoint
            val api ="https://slyney2248.alwaysdata.net/api/signin"
//            create a request that run that enable us to hold the data inform of a bundle/package
            val data = RequestParams ()

//            add/append/attach the email and the password
            data.put("email", email.text.toString())
            data.put("password",password.text.toString())

//            import the api helper
            val helper = ApiHelper(applicationContext)

//            by use of the function post_login the helper class post your data
            // 🔥 ONLY navigate after success
            helper.post(api, data) { success, message ->

                if (success) {
                    Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()

                    // ⏳ delay before navigating
                    android.os.Handler().postDelayed({

                        // clear fields
                        email.text.clear()
                        password.text.clear()

                        // navigate AFTER message is seen
                        val intent = Intent(applicationContext, MainActivity::class.java)
                        startActivity(intent)

                    }, 3000) // 2 seconds delay

                } else {
                    Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}