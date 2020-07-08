package com.fatimamostafa.roomcrud.ui.main

import androidx.lifecycle.LiveData
import com.fatimamostafa.roomcrud.database.Employee
import com.fatimamostafa.roomcrud.database.EmployeeDao
import com.fatimamostafa.roomcrud.database.EmployeeModel


class EmployeeRepository (private  val employeeDao: EmployeeDao) {
    fun getAllEmployees(): LiveData<List<EmployeeModel>> {
        return employeeDao.getAllEmployees()
    }

    suspend fun updateEmployee(employee: Employee) {
        employeeDao.updateEmployee(employee)
    }

    suspend fun addEmployee(employee: Employee) {
        employeeDao.addEmployee(employee)
    }

    suspend fun insertAllEmployee(list: List<Employee>) {
        employeeDao.insertAllEmployees(list)
    }

    suspend fun deleteEmployee(employee: Employee) {
        employeeDao.deleteEmployee(employee)
    }

}
