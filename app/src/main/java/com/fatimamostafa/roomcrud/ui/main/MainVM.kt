package com.fatimamostafa.roomcrud.ui.main

import Employee
import EmployeeDatabase
import android.app.Application
import android.os.Environment
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fatimamostafa.roomcrud.base.BaseViewModel
import com.fatimamostafa.roomcrud.database.EmployeeModel
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.nio.channels.FileChannel
import javax.inject.Inject

class MainVM @Inject constructor(
    private var repository: EmployeeRepository,
    private var application: Application
) : BaseViewModel() {
    val fileResponseLiveData: MutableLiveData<String> = MutableLiveData()
    val employeeListLiveData: MutableLiveData<MutableList<EmployeeModel>> = MutableLiveData()
    private var employeeJSON: String = ""


    fun getAllEmployees(): LiveData<List<EmployeeModel>> {
        return repository.getAllEmployees()
    }


    fun exportJSON(path: File?) {
        val data: File = Environment.getDataDirectory()
        var source: FileChannel? = null
        var destination: FileChannel? = null
        val currentDBPath =
            "/data/com.fatimamostafa.roomcrud/shared_prefs/player.json"
        val backupDBPath: String = "employeeJSON"
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

    fun importJSON(uri: String) {
        try {
            Log.d("MV", uri)
            val data = Environment.getDataDirectory()

            val currentDBPath =
                "/data/com.fatimamostafa.roomcrud/shared_prefs/player.json"
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
        }
    }

    fun employeeJson(jsonString: String) {
        employeeJSON = jsonString

        val employeeDao = EmployeeDatabase
            .getDatabase(application, employeeJSON)
            .employeeDao()
        repository = EmployeeRepository(employeeDao)
    }


}