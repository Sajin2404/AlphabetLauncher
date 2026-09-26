package com.sajin.alphabetlauncher.data

import android.content.Context
import android.content.Intent

class AppRepository(
    private val context: Context
) {

    fun getLaunchableApps(): List<AppInfo> {

        val packageManager = context.packageManager

        val intent = Intent(Intent.ACTION_MAIN).apply {
            addCategory(Intent.CATEGORY_LAUNCHER)
        }

        val activities = packageManager.queryIntentActivities(
            intent,
            0
        )

        return activities
            .mapNotNull { resolveInfo ->

                val activityInfo = resolveInfo.activityInfo
                    ?: return@mapNotNull null

                val packageName = activityInfo.packageName

                val appName = resolveInfo.loadLabel(packageManager)
                    ?.toString()
                    ?.trim()
                    ?: return@mapNotNull null

                val icon = resolveInfo.loadIcon(packageManager)

                AppInfo(
                    name = appName,
                    packageName = packageName,
                    icon = icon
                )
            }
            .distinctBy { it.packageName }
            .sortedBy { it.name.lowercase() }
    }

    fun launchApp(app: AppInfo) {

        val intent = context.packageManager
            .getLaunchIntentForPackage(app.packageName)

        intent?.let {
            it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(it)
        }
    }
}