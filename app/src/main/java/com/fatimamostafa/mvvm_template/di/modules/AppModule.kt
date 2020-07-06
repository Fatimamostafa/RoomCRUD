package com.fatimamostafa.mvvm_template.di.modules

import android.app.Application
import android.content.Context
import com.fatimamostafa.mvvm_template.App
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideApplication(app: App): Application = app

    @Provides
    @Singleton
    fun provideApplicationContext(app: App): Context = app.applicationContext

}