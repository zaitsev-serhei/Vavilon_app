package com.vavilon

import android.app.Application
import com.vavilon.di.DaggerAppComponent

open class VavilonApplication : Application() {
    open val appComponent by lazy {
    DaggerAppComponent.factory().create(applicationContext)
    }
}