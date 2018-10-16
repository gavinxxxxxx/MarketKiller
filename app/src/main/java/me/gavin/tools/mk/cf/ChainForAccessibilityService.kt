package me.gavin.tools.mk.cf

import android.accessibilityservice.AccessibilityService
import android.graphics.Rect
import android.os.Handler
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import java.io.DataOutputStream

class ChainForAccessibilityService : AccessibilityService() {

    private val TAG = javaClass.simpleName

    private lateinit var su: Process
    private val handler = Handler()

    override fun onCreate() {
        super.onCreate()
        su = Runtime.getRuntime().exec("su")
    }

    override fun onInterrupt() {}

    override fun onAccessibilityEvent(event: AccessibilityEvent) {
        event.logD(TAG)
        when (event.eventType) {
            AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED -> {
                event.className.logD(TAG)
                when (event.className.toString()) {
                    "com.chainfor.finance.app.main.MainActivity" -> onMainOpen()
                    "com.chainfor.finance.app.account.LoginActivity" -> onLoginOpen()
                }
            }
        }
    }

    private fun onMainOpen() {
        handler.postDelayed({
            rootInActiveWindow.findAccessibilityNodeInfosByViewId("com.app.lianxiang.n.debug:id/icon")
                    ?.lastOrNull()?.apply {
                        "底部导航找到 尝试点击".logE(TAG)
                        val rect = Rect()
                        getBoundsInScreen(rect)
                        val cmd = "adb shell input tap ${rect.centerX()} ${rect.bottom}".also { it.logE(TAG) }

                        val dataOutputStream = DataOutputStream(su.outputStream)
                        dataOutputStream.writeBytes(cmd)
                        dataOutputStream.flush()

                    } ?: "底部导航未找到".logE(TAG)
        }, 1000)
    }

    private fun onLoginOpen() {
        handler.postDelayed({
            rootInActiveWindow.findAccessibilityNodeInfosByViewId("com.app.lianxiang.n.debug:id/etAccount")
                    ?.firstOrNull()?.apply {
                        "底部导航找到 尝试点击".logE(TAG)
                        this.performAction(AccessibilityNodeInfo.ACTION_FOCUS)
                        val cmd = "adb shell input text 132abc"

                        val dataOutputStream = DataOutputStream(su.outputStream)
                        dataOutputStream.writeBytes(cmd)
                        dataOutputStream.flush()
                    } ?: "底部导航未找到".logE(TAG)
        }, 1000)
    }

}