package com.example.productivitylauncher

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.productivitylauncher.models.AppInfo


class AppsRecycler(context: Context) : RecyclerView.Adapter<AppsRecycler.ViewHolder>() {
    val appsList: MutableList<AppInfo>

    init {
        val packageManager: PackageManager = context.packageManager
        val intent = Intent(Intent.ACTION_MAIN, null)
        intent.addCategory(Intent.CATEGORY_LAUNCHER)
        val allApps = packageManager.queryIntentActivities(intent, 0)

        appsList = ArrayList()
        for (resolveInfo in allApps) {

            var appTimer: AppTimer? = null

            for (app in BlockListHandler.appBlockList) {
                if (app.packageName == resolveInfo.activityInfo.packageName) {
                    appTimer = app.allowedTime?.let { AppTimer(it) }
                }
            }

            appsList.add(
                AppInfo(
                    resolveInfo.loadLabel(packageManager),
                    resolveInfo.activityInfo.packageName,
                    resolveInfo.activityInfo.loadIcon(packageManager),
                    appTimer,
                    packageManager.getApplicationInfo(resolveInfo.activityInfo.packageName, 0).uid
                )
            )
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        var textView: TextView
        var imageView: ImageView

        init {
            textView = itemView.findViewById(R.id.app_name_text)
            imageView = itemView.findViewById(R.id.app_icon) as ImageView
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            val pos = adapterPosition
            val context: Context = v.context
            val launchIntent: Intent? = context.packageManager.getLaunchIntentForPackage(
                appsList[pos].packageName.toString()
            )
            context.startActivity(launchIntent)
            Toast.makeText(v.context, appsList[pos].label.toString(), Toast.LENGTH_LONG).show()
        }
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        val appLabel = appsList[i].label.toString()
        appsList[i].packageName.toString()
        val appIcon = appsList[i].icon
        val textView = viewHolder.textView
        textView.text = appLabel
        val imageView: ImageView = viewHolder.imageView
        imageView.setImageDrawable(appIcon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(R.layout.app_drawer_row, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = appsList.size
}
