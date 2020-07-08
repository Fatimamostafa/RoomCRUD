package com.fatimamostafa.roomcrud.ui.update

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.fatimamostafa.roomcrud.base.BaseViewModel
import com.fatimamostafa.roomcrud.database.Employee
import com.fatimamostafa.roomcrud.database.EmployeeDatabase
import com.fatimamostafa.roomcrud.ui.main.EmployeeRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class UpdateEmployeeVM @Inject constructor(private val application: Application) :
    BaseViewModel() {

    val imagePath: MutableLiveData<String> = MutableLiveData()
    val gender: MutableLiveData<String> = MutableLiveData()
    val updated: MutableLiveData<Boolean> = MutableLiveData()
    private var employeeJSON: String = ""
    private var repository: EmployeeRepository

    init {
        val employeeDao = EmployeeDatabase
            .getDatabase(application, employeeJSON, viewModelScope)
            .employeeDao()
        repository = EmployeeRepository(employeeDao)
    }


    fun addEmployee(firstName: String, lastName: String, age: String) {
        viewModelScope.launch {
            repository.addEmployee(
                Employee(
                    null,
                    firstName,
                    lastName,
                    age.toInt(),
                    gender.value!!,
                    imagePath.value!!
                )
            )
            updated.postValue(true)
        }
    }

    fun updateEmployee(id: Int, firstName: String, lastName: String, age: String) {
        viewModelScope.launch {
            repository.updateEmployee(
                Employee(
                    id,
                    firstName,
                    lastName,
                    age.toInt(),
                    gender.value!!,
                    imagePath.value!!
                )
            )
            updated.postValue(true)
        }
    }

}