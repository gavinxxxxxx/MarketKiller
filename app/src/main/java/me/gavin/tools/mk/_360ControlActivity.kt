package me.gavin.tools.mk

import android.os.Bundle
import android.support.v7.app.AppCompatActivity


class _360ControlActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println("onCreate - ${javaClass.name}")
    }
}