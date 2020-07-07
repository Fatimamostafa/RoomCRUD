package com.fatimamostafa.roomcrud.base

import androidx.lifecycle.ViewModel
import com.fatimamostafa.roomcrud.di.modules.DaggerViewModelInjectorModule
import com.fatimamostafa.roomcrud.di.modules.NetworkModule
import com.fatimamostafa.roomcrud.di.modules.ViewModelInjectorModule


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