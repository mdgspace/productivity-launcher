package com.example.productivitylauncher

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val appsButton = findViewById<TextView>(R.id.apps_button)
        appsButton.setOnClickListener {
            val intent = Intent(this, AppDrawer::class.java)
            startActivity(intent)
        }
    }
}
