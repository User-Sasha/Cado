package com.example.cado

import android.app.Application
import android.content.Context

class MyApp: Application() {
    companion object{
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}