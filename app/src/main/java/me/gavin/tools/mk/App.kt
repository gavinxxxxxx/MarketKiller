package me.gavin.tools.mk

import android.app.Application
import android.content.Intent
import android.content.IntentFilter
import com.orhanobut.hawk.Hawk
import kotlin.properties.Delegates

/**
 * 描述：
 *
 * @author CoderPig on 2018/04/12 11:43.
 */
class App : Application() {

    companion object {
        var instance by Delegates.notNull<App>()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        Hawk.init(this).build()
    }
}