package com.fatimamostafa.roomcrud.ui.update

import com.fatimamostafa.roomcrud.di.InjectionViewModelProvider
import com.fatimamostafa.roomcrud.di.qualifiers.ViewModelInjection
import dagger.Module
import dagger.Provides

@Module
class UpdateEmployeeModule {

    @Provides
    @ViewModelInjection
    fun provideUpdateEmployeeVM(
        activity: UpdateEmployeeActivity,
        viewModelProvider: InjectionViewModelProvider<UpdateEmployeeVM>
    ) = viewModelProvider.get(activity, UpdateEmployeeVM::class)


}