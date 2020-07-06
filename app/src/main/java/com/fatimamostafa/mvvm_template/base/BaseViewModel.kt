package com.fatimamostafa.mvvm_template.base

import androidx.lifecycle.ViewModel
import com.fatimamostafa.mvvm_template.di.modules.DaggerViewModelInjectorModule
import com.fatimamostafa.mvvm_template.di.modules.NetworkModule
import com.fatimamostafa.mvvm_template.di.modules.ViewModelInjectorModule


abstract class BaseViewModel() : ViewModel() {
   // val compositeDisposable = CompositeDisposable()
   private val injector: ViewModelInjectorModule = DaggerViewModelInjectorModule
       .builder()
       .networkModule(NetworkModule)
       //.utilsModule(UtilsModule)
       .build()

    init {
        inject()
    }
    /**
     * Injects the required dependencies
     */
    private fun inject() {

    }
}