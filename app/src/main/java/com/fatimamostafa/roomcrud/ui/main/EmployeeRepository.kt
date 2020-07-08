package com.fatimamostafa.roomcrud.ui.main

import Employee
import EmployeeDao
import EmployeeDatabase
import android.content.Context
import androidx.lifecycle.LiveData
import com.fatimamostafa.roomcrud.database.EmployeeModel


class EmployeeRepository (private  val employeeDao: EmployeeDao) {

    fun getAllEmployees(): LiveData<List<EmployeeModel>> {
        return employeeDao.getAllEmployees()
    }


    suspend fun updateEmployee(player: Employee) {
        employeeDao.updateEmployee(player)
    }

    suspend fun deleteEmployee(player: Employee) {
        employeeDao.deleteEmployee(player)
    }

}
