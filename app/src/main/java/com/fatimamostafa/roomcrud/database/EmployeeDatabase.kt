package com.fatimamostafa.roomcrud.database

import Employee
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Database(version = 1, entities = [Employee::class], exportSchema = false)
abstract class EmployeeDatabase() : RoomDatabase() {

    abstract fun employeeDao(): EmployeeDao

    private class EmployeeDatabaseCallback(
        private val employeeJson: String
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                CoroutineScope(Dispatchers.IO).launch {
                    val employeeDao = database.employeeDao()
                    prePopulateDatabase(employeeDao)
                }
            }
        }

        private suspend fun prePopulateDatabase(employeeDao: EmployeeDao) {
            val typeToken = object : TypeToken<List<Employee>>() {}.type
            val employees = Gson().fromJson<List<Employee>>(employeeJson, typeToken)
            employeeDao.insertAllEmployees(employees)
        }
    }

    companion object {

        @Volatile
        private var INSTANCE: EmployeeDatabase? = null

        fun getDatabase(
            context: Context,
            employeeJson: String
        ): EmployeeDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    EmployeeDatabase::class.java,
                    "employees_database"
                )
                    .addCallback(EmployeeDatabaseCallback(employeeJson))
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
