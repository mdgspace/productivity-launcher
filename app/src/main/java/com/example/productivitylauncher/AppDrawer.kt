package com.example.productivitylauncher

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class AppDrawer : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.app_drawer)
        val recyclerView = findViewById<RecyclerView>(R.id.apps_list)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = AppsRecycler(this)
    }
}
