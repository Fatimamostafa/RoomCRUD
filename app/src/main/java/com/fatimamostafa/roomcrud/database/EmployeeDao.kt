package com.fatimamostafa.roomcrud.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface EmployeeDao {
  @Insert(onConflict = OnConflictStrategy.IGNORE)
  suspend fun insertAllEmployees(employees: List<Employee>)

  @Query("SELECT id, firstName, lastName, age, gender, imageUrl FROM employees")
  fun getAllEmployees(): LiveData<List<EmployeeModel>>

  @Update
  suspend fun updateEmployee(employee: Employee)

  @Delete
  suspend fun deleteEmployee(employee: Employee)

  @Insert
  suspend fun addEmployee(employee: Employee)
}
