package com.aston.rickandmorty.app

import android.app.Application
import com.aston.rickandmorty.di.component.AppComponent
import com.aston.rickandmorty.di.component.DaggerAppComponent

class App : Application() {

    lateinit var component: AppComponent

    override fun onCreate() {
        super.onCreate()

        component = DaggerAppComponent.factory().create(this)
    }

}