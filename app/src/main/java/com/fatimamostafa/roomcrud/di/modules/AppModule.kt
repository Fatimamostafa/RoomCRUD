package com.fatimamostafa.roomcrud.di.modules

import android.app.Application
import android.content.Context
import com.fatimamostafa.roomcrud.App
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