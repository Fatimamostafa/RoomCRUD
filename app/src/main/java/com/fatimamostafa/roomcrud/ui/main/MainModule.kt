package com.fatimamostafa.roomcrud.ui.main

import dagger.Module
import dagger.Provides
import com.fatimamostafa.roomcrud.di.qualifiers.ViewModelInjection
import com.fatimamostafa.roomcrud.di.InjectionViewModelProvider

@Module
class MainModule {

    @Provides
    @ViewModelInjection
    fun provideMainVM(
        activity: MainActivity,
        viewModelProvider: InjectionViewModelProvider<MainVM>
    ) = viewModelProvider.get(activity, MainVM::class)
}