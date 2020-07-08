

import androidx.lifecycle.LiveData
import androidx.room.*
import com.fatimamostafa.roomcrud.database.EmployeeModel

@Dao
interface EmployeeDao {

  @Insert(onConflict = OnConflictStrategy.IGNORE)
  suspend fun insertAllEmployees(employees: List<Employee>)

  @Query("SELECT id, firstName, lastName, age, gender, imageUrl FROM employees")
  fun getAllEmployees(): LiveData<List<EmployeeModel>>

  @Update
  suspend fun updateEmployee(player: Employee)

  @Delete
  suspend fun deleteEmployee(player: Employee)
}
