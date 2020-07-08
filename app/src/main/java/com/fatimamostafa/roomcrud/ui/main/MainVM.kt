package com.fatimamostafa.roomcrud.ui.main

import android.app.Application
import android.os.Environment
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.fatimamostafa.roomcrud.base.BaseViewModel
import com.fatimamostafa.roomcrud.database.EmployeeDatabase
import com.fatimamostafa.roomcrud.database.EmployeeModel
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.nio.channels.FileChannel
import javax.inject.Inject

class MainVM @Inject constructor(
    private var application: Application
) : BaseViewModel() {
    val fileResponseLiveData: MutableLiveData<String> = MutableLiveData()
    val employeeListLiveData: MutableLiveData<MutableList<EmployeeModel>> = MutableLiveData()
    private var employeeJSON: String = ""

    private var repository: EmployeeRepository

    init {
        Log.d("DATABASE: ", "init")
        val employeeDao = EmployeeDatabase
            .getDatabase(application, employeeJSON, viewModelScope)
            .employeeDao()
        repository = EmployeeRepository(employeeDao)
    }

    fun getAllEmployees(): LiveData<List<EmployeeModel>> {
        return repository.getAllEmployees()
    }


    fun exportJSON(path: File?) {
        val data: File = Environment.getDataDirectory()
        var source: FileChannel? = null
        var destination: FileChannel? = null
        val currentDBPath =
            "/data/com.fatimamostafa.roomcrud/databases/employees_database"
        val backupDBPath: String = "employees_database"
        val currentDB = File(data, currentDBPath)
        val backupDB = File(path, backupDBPath)
        try {
            source = FileInputStream(currentDB).channel
            destination = FileOutputStream(backupDB).channel
            destination.transferFrom(source, 0, source.size())
            source.close()
            destination.close()
            Log.d("VM", "Exported$path")
            fileResponseLiveData.postValue("DB exported to Download Folder")
            //Toast.makeText(this, "DB Exported!", Toast.LENGTH_LONG).show()
        } catch (e: IOException) {
            e.printStackTrace()
            fileResponseLiveData.postValue("DB exported failed")
        }
    }

    fun importJSON(employeeJSON: String) {
        val employeeDao = EmployeeDatabase
            .getDatabase(application, employeeJSON, viewModelScope)
            .employeeDao()
        repository = EmployeeRepository(employeeDao)

        /* try {
             Log.d("MV", uri)
             val data = Environment.getDataDirectory()

             val currentDBPath =
                 "/data/com.fatimamostafa.roomcrud/databases/employees_database"
             val backupDBPath = ""
             val backupDB = File(data, currentDBPath)
             val currentDB = File(uri.replace(backupDBPath, ""), backupDBPath)

             val src =
                 FileInputStream(currentDB).channel
             val dst =
                 FileOutputStream(backupDB).channel
             dst.transferFrom(src, 0, src.size())
             src.close()
             dst.close()
             Log.d("VM", "imported")
             fileResponseLiveData.postValue("DB imported")
             getAllEmployees()

         } catch (e: Exception) {
             Log.d("VM", "imported Failed" + e.localizedMessage)
             fileResponseLiveData.postValue("DB imported Failed")
         }*/

    }

    fun employeeJson(jsonString: String) {
        employeeJSON = jsonString
    }

}