package me.gavin.tools.mk

import android.accessibilityservice.AccessibilityService
import android.os.Handler
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import java.io.OutputStream

class _360AccessibilityService : AccessibilityService() {

    private val handler = Handler()

    override fun onCreate() {
        super.onCreate()
        println("onCreate - $this")
    }

    override fun onInterrupt() {}

    override fun onAccessibilityEvent(event: AccessibilityEvent) {
        println(event.toString())
        when (event.eventType) {
            AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED -> {
                println(event.className)
                when (event.className.toString()) {
                    "com.qihoo.appstore.home.MainActivity" -> onMainOpen()
                }
            }
        }
    }

    private fun onMainOpen() {
        handler.postDelayed({
            println("首页 START")
            rootInActiveWindow?.apply {
                findAccessibilityNodeInfosByViewId("com.qihoo.appstore:id/common_title_alpha_view")
                        .firstOrNull()?.apply {
                            println(this)
                            println("找到搜索页入口 尝试打开搜索页")
                            performAction(AccessibilityNodeInfo.ACTION_CLICK)
                            parent.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                            performAction(AccessibilityNodeInfo.ACTION_FOCUS)
                        } ?: println("找不到搜索页入口")
            }
        }, 500)
    }

}