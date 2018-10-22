package me.gavin.tools.mk

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.Uri

class MonitorSysReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_PACKAGE_ADDED) {
            println("Intent.ACTION_PACKAGE_ADDED - $intent")
        }
        if (intent.action == Intent.ACTION_PACKAGE_REMOVED) {
            println("Intent.ACTION_PACKAGE_REMOVED - $intent")
        }
    }

    /**
     * 卸载 APK。
     *
     * @param packageName 应用的包名
     */
    fun uninstallApk(context: Context, packageName: String) {
        val packageURI = Uri.parse("package:$packageName")
        val intent = Intent(Intent.ACTION_DELETE, packageURI)
        context.startActivity(intent)
    }

}