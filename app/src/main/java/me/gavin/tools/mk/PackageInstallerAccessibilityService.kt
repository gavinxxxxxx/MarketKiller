package me.gavin.tools.mk

import android.accessibilityservice.AccessibilityService
import android.os.Handler
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import android.content.Intent
import android.net.Uri
import android.net.Uri.fromParts




class PackageInstallerAccessibilityService : AccessibilityService() {

    private val handler = Handler()

    override fun onInterrupt() {}

    override fun onAccessibilityEvent(event: AccessibilityEvent) {
        println(event.toString())
        when (event.eventType) {
            AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED -> {
                println("TYPE_WINDOW_STATE_CHANGED - ${event.className}")
                when (event.className.toString()) {
                    "com.android.packageinstaller.PackageInstallerActivity" -> onMainOpen()
                    "com.android.packageinstaller.InstallAppProgress" -> onProgressOpen()
                }
            }
            AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED -> {
                println("TYPE_WINDOW_CONTENT_CHANGED - ${event.className}")
                onInstallSuccess()
            }
            AccessibilityEvent.TYPE_VIEW_SCROLLED -> {
                println("TYPE_VIEW_SCROLLED - ${event.className}")
                onInstallSuccess2()
            }
        }
    }

    private fun onMainOpen() {
        handler.postDelayed({
            println("安装应用 START")
            rootInActiveWindow?.apply {
                findAccessibilityNodeInfosByViewId("com.android.packageinstaller:id/ok_button")
                        .firstOrNull()?.apply {
                            println("找到 下一步/安装按钮 尝试 下一步/安装")
                            performAction(AccessibilityNodeInfo.ACTION_CLICK)
                        } ?: println("找不到 下一步/安装 按钮")
            }
        }, 2000)
    }

    private fun onProgressOpen() {
        println("安装中。。。")
    }

    private fun onInstallSuccess2() {
        handler.postDelayed({
            println("安装应用 START")
            rootInActiveWindow?.apply {
                findAccessibilityNodeInfosByViewId("com.android.packageinstaller:id/ok_button")
                        .firstOrNull()?.apply {
                            println("找到 下一步/安装按钮 尝试 下一步/安装")
                            performAction(AccessibilityNodeInfo.ACTION_CLICK)
                        } ?: println("找不到 下一步/安装 按钮")
            }
        }, 1000)
    }

    private fun onInstallSuccess() {
        handler.postDelayed({
            println("尝试打开应用")
            rootInActiveWindow?.apply {
                findAccessibilityNodeInfosByViewId("com.android.packageinstaller:id/launch_button")
                        .firstOrNull()?.apply {
                            println("找到打开按钮 尝试打开应用")
                            performAction(AccessibilityNodeInfo.ACTION_CLICK)
                        } ?: println("找不到打开按钮")
            }
        }, 2000)
    }

    private fun unInstall() {
        val uri = Uri.fromParts("package", "com.example.demo", null)
        val intent = Intent(Intent.ACTION_DELETE, uri)
        startActivity(intent)
    }

}