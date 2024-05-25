package com.vavilon.di

import android.content.Context
import com.vavilon.MainActivity
import com.vavilon.di.modules.FinanceModule
import com.vavilon.di.modules.RoomModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [FinanceModule::class, RoomModule::class])
interface AppComponent {
    @Component.Factory
    interface  Factory{
        fun create(@BindsInstance context: Context): AppComponent
    }
    fun injectMainActivity(mainActivity: MainActivity)

}