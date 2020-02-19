package com.phgarcia.msjc.unit

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.phgarcia.msjc.database.ModulesDatabase
import com.phgarcia.msjc.models.Lesson
import com.phgarcia.msjc.models.Module
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
    @Throws
    fun modulesRepository_getModules_notEmpty() {
        runBlocking {
            val module = Module(0L, "", "")
            modulesDatabase.modulesTableDao.insert(module)
        }
        Assert.assertFalse(modulesRepository.modules.getOrAwaitValue().isEmpty())
    }

    @Test
    @Throws
    fun modulesRepository_updateLessonsForModule_completed() {
        runBlocking {
            val module = Module(0L, "", "")
            modulesDatabase.modulesTableDao.insert(module)
            val lesson1 = Lesson(1L, module.moduleNumber, "", "")
            val lesson2 = Lesson(2L, module.moduleNumber, "", "")
            val lesson3 = Lesson(3L, module.moduleNumber, "", "")
            modulesDatabase.lessonsTableDao.insertAll(listOf(lesson1, lesson2, lesson3))
            modulesRepository.refreshLessons(0)
        }
        // Be aware that the solution's number os lessons may be different,
        // thus this test may fail even though the code is working alright
        Assert.assertEquals(modulesRepository.getLessonsLiveData().getOrAwaitValue().size, 3)
    }

}