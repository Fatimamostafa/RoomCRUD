package com.fatimamostafa.roomcrud.ui.main

import Employee
import EmployeeDao
import androidx.lifecycle.LiveData
import com.fatimamostafa.roomcrud.database.EmployeeModel


class EmployeeRepository (private  val employeeDao: EmployeeDao) {

    companion object {
        @Volatile
        private var instance: EmployeeRepository? = null

        fun getInstance(employeeDAO: EmployeeDao) = instance ?: synchronized(this) {
            instance ?: EmployeeRepository(employeeDAO).also { instance = it }
        }

        fun getInstance(): EmployeeRepository {
            return instance!!
        }
    }

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
