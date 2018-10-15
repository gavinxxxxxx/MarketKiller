package me.gavin.tools.mk.wx

import android.widget.Toast
import me.gavin.tools.mk.App


fun shortToast(msg: String) = Toast.makeText(App.instance, msg, Toast.LENGTH_SHORT).show()

fun longToast(msg: String) = Toast.makeText(App.instance, msg, Toast.LENGTH_LONG).show()