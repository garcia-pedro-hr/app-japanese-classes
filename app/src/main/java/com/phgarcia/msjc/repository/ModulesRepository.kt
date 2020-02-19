package com.phgarcia.msjc.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.phgarcia.msjc.database.ModulesDatabase
import com.phgarcia.msjc.models.Lesson
import com.phgarcia.msjc.models.Module
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class ModulesRepository(private val database: ModulesDatabase) {

    val modules: LiveData<List<Module>> = database.modulesTableDao.getAll()

    private val lessonsLiveData = MutableLiveData<List<Lesson>>()

    /**
     * Expose the MutableLiveData as a LiveData object
     */
    fun getLessonsLiveData(): LiveData<List<Lesson>> = lessonsLiveData

    /**
     * Update the lessons LiveData with lessons from a specific module
     */
    suspend fun refreshLessons(moduleNumber: Long) = withContext(Dispatchers.IO) {
        val lessons: List<Lesson> = database.lessonsTableDao.getAllForModule(moduleNumber)
        lessonsLiveData.postValue(lessons)
        Timber.d("Updated lessons LiveData with ${lessons.size} entries for module $moduleNumber")
    }

    /**
     * Returns a LiveData object containing the Module which moduleNumber is the given Id
     */
    fun findModule(moduleId: Long): Module? = database.modulesTableDao.get(moduleId)

}