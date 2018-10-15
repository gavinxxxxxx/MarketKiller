package me.gavin.tools.mk

import android.accessibilityservice.AccessibilityService
import android.graphics.Rect
import android.os.Handler
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import java.io.OutputStream

class _360AccessibilityService : AccessibilityService() {

    private lateinit var su: OutputStream
    private val handler = Handler()

    override fun onCreate() {
        super.onCreate()
        su = Runtime.getRuntime().exec("su").outputStream
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
            rootInActiveWindow?.apply {
                findAccessibilityNodeInfosByViewId("com.qihoo.appstore:id/btn_search")
                        .forEach {
                            println("找到了 - $it")
                            val rect = Rect()
                            it.getBoundsInScreen(rect)
                            val text = "adb shell input tap  ${rect.centerX()} ${rect.centerY()}"
                            println(text)
                            su.write(text.toByteArray())
                        }

                findAccessibilityNodeInfosByText("下载")
                        .forEach {
                            println("找到了 - $it")
                            it.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                            val text = "adb shell input swipe 50 250 250 250 500"
                            su.write(text.toByteArray())
                        }
            }
        }, 500)
    }

}