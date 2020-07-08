package com.fatimamostafa.roomcrud.ui.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.fatimamostafa.roomcrud.base.BaseViewModel
import com.fatimamostafa.roomcrud.database.Employee
import com.fatimamostafa.roomcrud.database.EmployeeDatabase
import com.fatimamostafa.roomcrud.database.EmployeeModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.launch
import java.io.File
import java.io.IOException
import javax.inject.Inject

class MainVM @Inject constructor(
    private var application: Application
) : BaseViewModel() {
    val response: MutableLiveData<String> = MutableLiveData()
    private var employeeJSON: String = ""

    private var repository: EmployeeRepository

    init {
        val employeeDao = EmployeeDatabase
            .getDatabase(application, employeeJSON, viewModelScope)
            .employeeDao()
        repository = EmployeeRepository(employeeDao)
    }

    fun getAllEmployees(): LiveData<List<EmployeeModel>> {
        return repository.getAllEmployees()
    }

    fun exportJSON(
        employeeList: List<EmployeeModel>,
        path: String?
    ) {

        val json = Gson().toJson(employeeList)

        Log.d("MAINVM", employeeList.toString())
        Log.d("MAINVM", path!!)
        try {
            File(path, "employee.json").writeText(json.toString())
            response.postValue("Json file exported to Download Folder")

        } catch (e: IOException) {
            e.printStackTrace()
            response.postValue("Json file exported failed")

        }


    }

    fun importJSON(employeeJSON: String) {
        val typeToken = object : TypeToken<List<Employee>>() {}.type
        val employees = Gson().fromJson<List<Employee>>(employeeJSON, typeToken)
        if (employees != null)
            viewModelScope.launch {
                repository.insertAllEmployee(employees)
                getAllEmployees()
            }


    }

    fun delete(item: EmployeeModel) {
        viewModelScope.launch {
            repository.deleteEmployee(
                Employee(
                    item.id,
                    item.firstName,
                    item.lastName,
                    item.age,
                    item.gender,
                    item.imageUrl
                )
            )
        }
    }


}