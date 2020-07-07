package com.fatimamostafa.roomcrud.di.modules

import dagger.Component
import javax.inject.Singleton


/**
 * Component providing inject() methods for presenters.
 */
@Singleton
@Component(modules = [
    NetworkModule::class
])
interface ViewModelInjectorModule {


    @Component.Builder
    interface Builder {
        fun build(): ViewModelInjectorModule
        fun networkModule(networkModule: NetworkModule): Builder
    }
}