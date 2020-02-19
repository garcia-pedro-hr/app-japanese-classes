package com.phgarcia.msjc.ui.main

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.phgarcia.msjc.R
import com.phgarcia.msjc.database.getModulesDatabase
import com.phgarcia.msjc.models.Lesson
import com.phgarcia.msjc.models.Module
import com.phgarcia.msjc.repository.ModulesRepository
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val PREFERENCE_FILE_KEY = application.resources.getString(R.string.preference_file_key)
    private val LAST_SELECTED_INDEX_KEY = application.resources.getString(R.string.last_selected_module_key)

    private val modulesRepository = ModulesRepository(getModulesDatabase(application))
    private val sharedPref = application.getSharedPreferences(PREFERENCE_FILE_KEY, Context.MODE_PRIVATE)

    val selectedModule: MutableLiveData<Module> = MutableLiveData()
    val moduleList: LiveData<List<Module>> = modulesRepository.modules
    val lessonList: LiveData<List<Lesson>> = modulesRepository.getLessonsLiveData()

    fun selectModule(module: Module) = viewModelScope.launch {
        selectedModule.postValue(module)
        modulesRepository.refreshLessons(module.moduleNumber)
    }

    fun retrieveLastSelectedPosition(): Int = sharedPref.getInt(LAST_SELECTED_INDEX_KEY, 0)

    fun storeLastSelectedPosition(position: Int) = with(sharedPref.edit()) {
        putInt(LAST_SELECTED_INDEX_KEY, position)
        apply()
    }

}
