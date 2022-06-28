package com.example.productivitylauncher

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.productivitylauncher.models.AppInfo


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

            // Iterate over appList, check if app name is com.android.chrome, if so, start its timer
            for (app in it.appsList) {
                if ((app.packageName == "com.android.chrome") and !app.timer.mTimerRunning) {
                    app.timer.start()
                }
            }

            val handler = Handler(Looper.getMainLooper())

            val r: Runnable = object : Runnable {
                override fun run() {
                    appBlock()
                    handler.postDelayed(this, 1000)
                }

                // TODO: appBlock() should be triggered from timer.OnFinish
                //      Adapter starts again after going back on screen,
                //      thus store the list outside of here

                fun appBlock()
                {
                    it.appsList.removeIf { el: AppInfo -> (!el.timer.enabled) }
                    it.notifyDataSetChanged()
                }
            }

            handler.postDelayed(r, 1000)
        }
    }

//    Code for checking if app is running in foreground
//    TODO : Check this to pause timer and use a list instead of one app
//    private fun printForegroundTask(): String {
//        println("Working")
//        var currentApp = "NULL"
//        val usm = this.getSystemService(USAGE_STATS_SERVICE) as UsageStatsManager
//        val time = System.currentTimeMillis()
//        val appList =
//            usm.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, time - 1000 * 1000, time)
//        if (appList != null && appList.size > 0) {
//            val mySortedMap: SortedMap<Long, UsageStats> = TreeMap()
//            for (usageStats in appList) {
//                mySortedMap[usageStats.lastTimeUsed] = usageStats
//            }
//            if (!mySortedMap.isEmpty()) {
//                currentApp = mySortedMap[mySortedMap.lastKey()]?.packageName ?: "NULL"
//            }
//        }
//        Log.e("adapter", "Current App in foreground is: $currentApp")
//        return currentApp
//    }
}

