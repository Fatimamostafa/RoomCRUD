package com.fatimamostafa.roomcrud.di

import android.content.Context
import com.fatimamostafa.roomcrud.App
import com.fatimamostafa.roomcrud.di.modules.ActivityInjectorsModule
import com.fatimamostafa.roomcrud.di.modules.AppModule
import com.fatimamostafa.roomcrud.di.modules.FragmentInjectorsModule
import com.fatimamostafa.roomcrud.di.modules.NetworkModule
import dagger.BindsInstance

import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ActivityInjectorsModule::class,
        FragmentInjectorsModule::class,
        NetworkModule::class,
        AppModule::class
        //UtilsModule::class
    ]
)
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: App): Builder
        fun build(): AppComponent
    }

    fun inject(app: App)
    fun inject(context: Context)
}