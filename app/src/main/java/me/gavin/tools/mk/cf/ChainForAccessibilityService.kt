package me.gavin.tools.mk.cf

import android.accessibilityservice.AccessibilityService
import android.view.accessibility.AccessibilityEvent

class ChainForAccessibilityService : AccessibilityService() {

    override fun onInterrupt() {}

    override fun onAccessibilityEvent(event: AccessibilityEvent) {
        println(event.toString())
        when (event.eventType) {
            AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED -> {
                println(event.className)
                when (event.className.toString()) {
                    "com.chainfor.finance.app.main.MainActivity" -> onMainOpen()
                }
            }
        }
    }

    private fun onMainOpen() {
        rootInActiveWindow?.apply {

        }
    }

}