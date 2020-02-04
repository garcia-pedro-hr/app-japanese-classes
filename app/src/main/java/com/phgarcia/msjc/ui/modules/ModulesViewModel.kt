package com.phgarcia.msjc.ui.modules

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.phgarcia.msjc.database.getModulesDatabase
import com.phgarcia.msjc.models.Module
import com.phgarcia.msjc.repository.ModulesRepository
import timber.log.Timber

class ModulesViewModel(application: Application) : AndroidViewModel(application) {

    private val modulesRepository = ModulesRepository(getModulesDatabase(application))

    val modulesLiveData: LiveData<List<Module>> = modulesRepository.modules

}
