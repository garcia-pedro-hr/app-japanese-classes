package com.phgarcia.msjc.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.phgarcia.msjc.database.ModulesDatabase
import com.phgarcia.msjc.models.Lesson
import com.phgarcia.msjc.models.Module
import com.phgarcia.msjc.services.SyllabusDecoder
import com.phgarcia.msjc.services.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class ModulesRepository(private val database: ModulesDatabase) {

    val modules: LiveData<List<Module>> = database.modulesTableDao.getAll()
    private val lessons = MutableLiveData<List<Lesson>>()

    /**
     * Expose the MutableLiveData as a LiveData object
     */
    fun getLessonsLiveData(): LiveData<List<Lesson>> = lessons

    /**
     * Refresh modules stored in offline cache
     */
    suspend fun refreshModules() = withContext(Dispatchers.IO) {
        try {
            val modules = SyllabusDecoder.syllabusDecoderService.getModules()
            Timber.d("Modules fetched from JSON file. Total of ${modules.size} modules")
            database.modulesTableDao.insertAll(modules.map { it.asDatabaseModel() })
        } catch (e: Exception) {
            Timber.e("Failed to fetch modules from JSON file: ${e.message}")
        }
    }

    /**
     * Refresh lessons stored in offline cache
     */
    suspend fun refreshLessons() = withContext(Dispatchers.IO) {
        try {
            val lessons = SyllabusDecoder.syllabusDecoderService.getLessons()
            Timber.d("Lessons fetched from JSON file. Total of ${lessons.size} lessons")
            database.lessonsTableDao.insertAll(lessons.map { it.asDatabaseModel() })
        } catch (e: Exception) {
            Timber.e("Failed to fetch lessons from JSON file: ${e.message}")
        }
    }

    /**
     * Update the lessons LiveData with lessons from a specific module
     */
    suspend fun updateLessonsForModule(moduleNumber: Long) = withContext(Dispatchers.IO) {
        lessons.postValue(database.lessonsTableDao.getAllForModule(moduleNumber))
    }

}