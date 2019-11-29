package com.phgarcia.msjc.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.phgarcia.msjc.models.Module

@Dao
interface ModuleTableDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(module: Module): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(modules: List<Module>)

    @Update
    fun update(module: Module)

    @Query("SELECT * FROM module_table WHERE module_number = :key")
    fun get(key: Long): Module?

    @Query("SELECT * FROM module_table ORDER BY module_number")
    fun getAll(): LiveData<List<Module>>
}