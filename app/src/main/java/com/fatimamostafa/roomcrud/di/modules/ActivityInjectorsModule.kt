package com.fatimamostafa.roomcrud.di.modules

import com.fatimamostafa.roomcrud.ui.main.MainActivity
import com.fatimamostafa.roomcrud.ui.main.MainModule
import com.fatimamostafa.roomcrud.ui.update.UpdateEmployeeActivity
import com.fatimamostafa.roomcrud.ui.update.UpdateEmployeeModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityInjectorsModule {
    @ContributesAndroidInjector(modules = [MainModule::class])
    abstract fun mainActivityInjector(): MainActivity

    @ContributesAndroidInjector(modules = [UpdateEmployeeModule::class])
    abstract fun updateEmployeeActivityInjector(): UpdateEmployeeActivity
}