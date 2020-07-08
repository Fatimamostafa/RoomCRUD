package com.fatimamostafa.roomcrud.di.modules

import com.fatimamostafa.roomcrud.ui.main.MainVM
import dagger.Component
import javax.inject.Singleton


/**
 * Component providing inject() methods for presenters.
 */
@Singleton
@Component(modules = [

])
interface ViewModelInjectorModule {
    fun inject(mainVM: MainVM)

    @Component.Builder
    interface Builder {
        fun build(): ViewModelInjectorModule
    }
}