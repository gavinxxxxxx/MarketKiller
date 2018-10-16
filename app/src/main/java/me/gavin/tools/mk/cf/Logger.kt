package me.gavin.tools.mk.cf

import android.util.Log
import me.gavin.tools.mk.BuildConfig


private const val TAG = "_LOGGER_"
private const val NULL = "_NULL_"

private val level = if (BuildConfig.DEBUG) Log.VERBOSE else Log.ERROR


fun Any?.logV(tag: String = TAG) {
    if (level <= Log.VERBOSE) Log.v(tag, this?.toString() ?: NULL)
}

fun Any?.logD(tag: String = TAG) {
    if (level <= Log.DEBUG) Log.d(tag, this?.toString() ?: NULL)
}

fun Any?.logI(tag: String = TAG) {
    if (level <= Log.INFO) Log.i(tag, this?.toString() ?: NULL)
}

fun Any?.logW(tag: String = TAG) {
    if (level <= Log.WARN) Log.w(tag, this?.toString() ?: NULL)
}

fun Any?.logE(tag: String = TAG) {
    if (level <= Log.ERROR) Log.e(tag, this?.toString() ?: NULL)
}


fun Throwable?.logW(tag: String = TAG) {
    if (level <= Log.WARN) Log.w(tag, this?.message, this)
}

fun Throwable?.logE(tag: String = TAG) {
    if (level <= Log.ERROR) Log.e(tag, this?.message, this)
}
