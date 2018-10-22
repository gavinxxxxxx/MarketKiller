package me.gavin.tools.mk

import android.accessibilityservice.AccessibilityService
import android.os.Bundle
import android.os.Handler
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo


class OppoAccessibilityService : AccessibilityService() {

    private val handler = Handler()

    override fun onInterrupt() {}

    override fun onAccessibilityEvent(event: AccessibilityEvent) {
        println(event.toString())
        when (event.eventType) {
            AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED -> {
                println("TYPE_WINDOW_STATE_CHANGED - ${event.className}")
                when (event.className.toString()) {
                    "a.a.a.agk" -> onMainOpen()
                    "com.oppo.cdo.search.c" -> onSearchOpen()
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
                findAccessibilityNodeInfosByViewId("com.oppo.market:id/actionbar_content")
                        .firstOrNull()?.apply {
                            println("找到搜索页入口 尝试打开搜索页")
                            performAction(AccessibilityNodeInfo.ACTION_CLICK)
                        } ?: println("找不到搜索页入口")
            }
        }, 2000)
    }

    private fun onSearchOpen() {
        handler.postDelayed({
            println("搜索页 START")
            rootInActiveWindow?.apply {
                findAccessibilityNodeInfosByViewId("com.oppo.market:id/et_search")
                        .firstOrNull()?.apply {
                            println("找到搜索框 尝试输入搜索词")
                            val arguments = Bundle()
//                            arguments.putCharSequence(AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, "链向财经")
                            arguments.putCharSequence(AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, "计算器")
                            performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, arguments)
                        } ?: println("找不到搜索框")
            }
        }, 2000)
    }

    private fun onQuerySuccess() {
        handler.postDelayed({
            println("尝试寻找搜索结果")
            rootInActiveWindow?.apply {
                findAccessibilityNodeInfosByViewId("com.oppo.market:id/v_app_item")
                        .firstOrNull {
                            it.findAccessibilityNodeInfosByViewId("com.oppo.market:id/tv_name")
//                                    .firstOrNull()?.text.toString() == "链向财经"
                                    .firstOrNull()?.text.toString() == "OPPO计算器"
                        }?.apply {
                            findAccessibilityNodeInfosByViewId("com.oppo.market:id/bt_multifunc")
                                    ?.firstOrNull()?.apply {
                                        println("找到下载按钮 并点击")
                                        performAction(AccessibilityNodeInfo.ACTION_CLICK)
                                    } ?: println("没找到下载项")
                        } ?: println("没找到搜索结果")
            }
        }, 2000)
    }

}