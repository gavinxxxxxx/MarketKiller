package me.gavin.tools.mk.cf

import android.os.Bundle
import android.support.v7.app.AppCompatActivity


class ChainForControlActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println("onCreate - ${javaClass.name}")
    }
}