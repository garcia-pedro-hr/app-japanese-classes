package com.phgarcia.msjc.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.phgarcia.msjc.database.ModulesDatabase
import com.phgarcia.msjc.models.Lesson
import com.phgarcia.msjc.models.Module
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ModulesRepository(private val database: ModulesDatabase) {

    val modules: LiveData<List<Module>> = database.modulesTableDao.getAll()
    private val lessons = MutableLiveData<List<Lesson>>()

    /**
     * Expose the MutableLiveData as a LiveData object
     */
    fun getLessonsLiveData(): LiveData<List<Lesson>> = lessons

    /**
     * Update the lessons LiveData with lessons from a specific module
     */
    suspend fun updateLessonsForModule(moduleNumber: Long) = withContext(Dispatchers.IO) {
        lessons.postValue(database.lessonsTableDao.getAllForModule(moduleNumber))
    }

}