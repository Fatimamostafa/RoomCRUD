package com.fatimamostafa.roomcrud.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Database(version = 1, entities = [Employee::class], exportSchema = false)
abstract class EmployeeDatabase : RoomDatabase() {

    abstract fun employeeDao(): EmployeeDao

    private class EmployeeDatabaseCallback(
        private val employeeJson: String,
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {


        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    val employeeDao = database.employeeDao()
                    prePopulateDatabase(employeeDao)
                }
            }
        }


        private suspend fun prePopulateDatabase(employeeDao: EmployeeDao) {
            if (employeeJson != "") {
                val typeToken = object : TypeToken<List<Employee>>() {}.type
                val employees = Gson().fromJson<List<Employee>>(employeeJson, typeToken)
                if (employees != null)
                    employeeDao.insertAllEmployees(employees)
            }

        }
    }


    companion object {

        @Volatile
        private var INSTANCE: EmployeeDatabase? = null

        fun getDatabase(
            context: Context,
            employeeJson: String,
            coroutineScope: CoroutineScope
        ): EmployeeDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                Log.d("synchronized: ", "EMP")
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    EmployeeDatabase::class.java,
                    "employees_database"
                )
                    .addCallback(EmployeeDatabaseCallback(employeeJson, coroutineScope))
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }


}
