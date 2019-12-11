package com.phgarcia.msjc.unit

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.phgarcia.msjc.database.ModulesDatabase
import com.phgarcia.msjc.repository.ModulesRepository
import com.phgarcia.msjc.utils.getOrAwaitValue
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class ModulesRepositoryUnitTests {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var modulesDatabase: ModulesDatabase
    private lateinit var modulesRepository: ModulesRepository

    @Before
    fun createDatabase() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        modulesDatabase =
            Room.inMemoryDatabaseBuilder(context, ModulesDatabase::class.java)
                .allowMainThreadQueries()
                .build()
        modulesRepository = ModulesRepository(modulesDatabase)
    }

    @After
    @Throws(IOException::class)
    fun closeDatabase() = modulesDatabase.close()

    @Test
    @Throws(Exception::class)
    fun modulesRepository_refreshModules_completed() {
        runBlocking { modulesRepository.refreshModules() }
        Assert.assertFalse(modulesRepository.modules.getOrAwaitValue().isEmpty())
    }

    @Test
    @Throws(Exception::class)
    fun modulesRepository_refreshLessons_completed() {
        runBlocking {
            modulesRepository.refreshModules()
            modulesRepository.refreshLessons()
        }
        Assert.assertFalse(modulesDatabase.lessonsTableDao.getAll().isEmpty())
    }

    @Test
    @Throws
    fun modulesRepository_updateLessonsForModule_completed() {
        runBlocking {
            modulesRepository.refreshModules()
            modulesRepository.refreshLessons()
            modulesRepository.updateLessonsForModule(0)
        }
        // Be aware that the solution's number os lessons may be different,
        // thus this test may fail even though the code is working alright
        Assert.assertEquals(modulesRepository.getLessonsLiveData().getOrAwaitValue().size, 3)
    }

}