package com.example.sokogarden

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    lateinit var signupBtn: Button
    lateinit var signinBtn: Button
    lateinit var welcomeText: TextView
    lateinit var logoutBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // ✅ Single unified findViewById calls using class-level variables
        signupBtn = findViewById(R.id.signupbtn)
        signinBtn = findViewById(R.id.signinbtn)
        welcomeText = findViewById(R.id.welcomeText)
        logoutBtn = findViewById(R.id.logoutBtn)

        val prefs = getSharedPreferences("user_session", MODE_PRIVATE)
        val username = prefs.getString("username", null)

        // ✅ Show/hide UI based on login state
        if (username != null) {
            welcomeText.text = "Welcome $username"
            welcomeText.visibility = View.VISIBLE
            logoutBtn.visibility = View.VISIBLE
            signupBtn.visibility = View.GONE
            signinBtn.visibility = View.GONE
        } else {
            signupBtn.visibility = View.VISIBLE
            signinBtn.visibility = View.VISIBLE
            welcomeText.visibility = View.GONE
            logoutBtn.visibility = View.GONE
        }

        // Navigate to Signup
        signupBtn.setOnClickListener {
            startActivity(Intent(applicationContext, Signup::class.java))
        }

        // Navigate to Signin
        signinBtn.setOnClickListener {
            startActivity(Intent(applicationContext, Signin::class.java))
        }

        // 🔓 Logout logic
        logoutBtn.setOnClickListener {
            prefs.edit().clear().apply()
            Toast.makeText(this, "Logged out", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

//        find the recycle bin and progress bar by use of there id
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val progressBar = findViewById<ProgressBar>(R.id.progressbar)

//        specify the api url for fetching the product (always data)
        val url = "https://slyney2248.alwaysdata.net/api/get_products"

//        import the api helper
        val helper = ApiHelper(applicationContext)

//        inside of the helper class access the function load products
        helper.loadProducts(url, recyclerView, progressBar)

    }
}