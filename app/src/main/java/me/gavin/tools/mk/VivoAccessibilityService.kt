package me.gavin.tools.mk

import android.accessibilityservice.AccessibilityService
import android.os.Bundle
import android.os.Handler
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo


class VivoAccessibilityService : AccessibilityService() {

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
                println("TYPE_WINDOW_STATE_CHANGED - ${event.className}")
                when (event.className.toString()) {
                    "com.bbk.appstore.ui.AppStoreTabActivity" -> onMainOpen()
                    "com.bbk.appstore.ui.search.SearchActivity" -> onSearchOpen()
                }
            }
            AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED -> {
                println("TYPE_WINDOW_CONTENT_CHANGED - ${event.className}")
                onQuerySuccess()
            }
        }
    }

    private fun onMainOpen() {
        handler.postDelayed({
            println("首页 START")
            rootInActiveWindow?.apply {
                findAccessibilityNodeInfosByViewId("com.bbk.appstore:id/search_keys_layout")
                        .firstOrNull()?.apply {
                            println("找到搜索页入口 尝试打开搜索页")
                            performAction(AccessibilityNodeInfo.ACTION_CLICK)
                        } ?: println("找不到搜索页入口")
            }
        }, 500)
    }

    private fun onSearchOpen() {
        handler.postDelayed({
            println("搜索页 START")
            rootInActiveWindow?.apply {
                findAccessibilityNodeInfosByViewId("com.bbk.appstore:id/search_input")
                        .firstOrNull()?.apply {
                            println("找到搜索框 尝试输入搜索词")
                            val arguments = Bundle()
                            arguments.putCharSequence(AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, "android")
                            performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, arguments)
                        } ?: println("找不到搜索框")
            }
        }, 500)
    }

    private fun onQuerySuccess() {
        handler.postDelayed({
            println("尝试寻找搜索结果")
            rootInActiveWindow?.apply {
                findAccessibilityNodeInfosByViewId("com.bbk.appstore:id/package_list_item_layout")
                        .firstOrNull {
                            it.findAccessibilityNodeInfosByViewId("com.bbk.appstore:id/package_list_item_app_title")
                                    .firstOrNull()?.text.toString() == "Android"
                        }?.apply {
                            findAccessibilityNodeInfosByViewId("com.bbk.appstore:id/download_btn_layout")
                                    ?.firstOrNull()?.apply {
                                        if (this.getChild(0).text.toString() == "下载") {
                                            performAction(AccessibilityNodeInfo.ACTION_CLICK)
                                        } else {
                                            println("找到下载按钮 但状态错误")
                                        }
                                    } ?: println("没找到下载项")
                        } ?: println("没找到搜索结果")
            }
        }, 500)
    }

}