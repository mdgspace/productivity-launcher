package com.example.productivitylauncher

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class AppDrawer : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.app_drawer)
        val recyclerView = findViewById<RecyclerView>(R.id.apps_list)
        recyclerView.layoutManager = GridLayoutManager(this,4)
        recyclerView.adapter = AppsRecycler(this).also {
            it.appsList.sortWith { o1, o2 ->
                o1?.label.toString().compareTo(o2?.label.toString(), true)
            }
        }
    }
}
