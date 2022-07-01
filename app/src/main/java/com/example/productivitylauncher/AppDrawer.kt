package com.example.productivitylauncher

import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.productivitylauncher.models.AppInfo
import java.util.*


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

            val handler = Handler(Looper.getMainLooper())
            var currentForegroundTask = "NULL"

            val r: Runnable = object : Runnable {
                override fun run() {
                    checkTask()
                    handler.postDelayed(this, 1000)
                }

                // TODO: Adapter starts again after going back on screen,
                //      thus store the list outside of here

                fun checkTask()
                {
                    val foregroundTask = getForegroundTask()

                    if(currentForegroundTask != foregroundTask)
                    {
                        if(foregroundTask != "NULL")
                        {
                            for (app in it.appsList) {
                                if (app.packageName == foregroundTask) {
                                    app.timer?.start()
                                }
                            }
                        }
                        if(currentForegroundTask != "NULL")
                        {
                            for (app in it.appsList) {
                                if (app.packageName == currentForegroundTask) {
                                    app.timer?.pause()
                                }
                            }
                        }
                        currentForegroundTask = foregroundTask
                    }

                    if(foregroundTask != "NULL")
                    {
                        appBlock()
                    }
                }

                fun appBlock()
                {
                    for (app in it.appsList) {
                        if (app.packageName == currentForegroundTask && app.timer?.enabled == false) {
                                app.uid?.let { it1 -> android.os.Process.killProcess(it1) }
                        }
                    }

                    it.appsList.removeIf { el: AppInfo -> (el.timer!=null && !el.timer.enabled) }
                    it.notifyDataSetChanged()
                }
            }

            handler.postDelayed(r, 1000)
        }
    }

    // Code for checking if app is running in foreground
    private fun getForegroundTask(): String {
        var currentApp = "NULL"
        val usm = this.getSystemService(USAGE_STATS_SERVICE) as UsageStatsManager
        val time = System.currentTimeMillis()
        val appList =
            usm.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, time - 1000 * 1000, time)
        if (appList != null && appList.size > 0) {
            val mySortedMap: SortedMap<Long, UsageStats> = TreeMap()
            for (usageStats in appList) {
                mySortedMap[usageStats.lastTimeUsed] = usageStats
            }
            if (!mySortedMap.isEmpty()) {
                currentApp = mySortedMap[mySortedMap.lastKey()]?.packageName ?: "NULL"
            }
        }
        return currentApp
    }
}

