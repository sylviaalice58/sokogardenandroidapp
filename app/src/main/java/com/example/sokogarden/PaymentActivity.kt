package com.example.sokogarden

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.loopj.android.http.RequestParams

class PaymentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_payment)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

//        find the veiws by use of the id
        val txtName = findViewById<TextView>(R.id.txtProductName)
        val txtCost = findViewById<TextView>(R.id.txtProductCost)
        val imgProduct = findViewById<ImageView>(R.id.imgProduct)

//        retrive the data passed from the previous activity
        val name =intent.getStringExtra("product_name")
        val cost = intent.getIntExtra("product_cost", 0)
        val product_photo = intent.getStringExtra("product_photo")

//        update the text views with the data first from the previous activity
        txtName.text = name
        txtCost.text = "Kes $cost"

//        specify the image URL
        val imageUrl = "https://slyney2248.alwaysdata.net/static/images/$product_photo"

        Glide.with(this)
            .load(imageUrl )
            .placeholder(R.drawable.ic_launcher_background) // Make sure you have a placeholder image
            .into(imgProduct)

        //find the edit text and paynow button by use of there ids
        val phone = findViewById<EditText>(R.id.phone)
        val btnpay = findViewById<Button>(R.id.pay)

        //set click listener on the paynow button
        btnpay.setOnClickListener {
            //specify the api endpoint for making payment
            val api = "https://slyney2248.alwaysdata.net/api/mpesa_payment"

//            create a request params
            val data = RequestParams()

            //insert data into the request params
            data.put("amount",cost)
            data.put("phone", phone.text.toString())

//            import the helper class
            val helper = ApiHelper(applicationContext)

//            axccees the post function inside of the helper class
            helper.post(api, data)

            phone.text.clear()

        }
    }
}