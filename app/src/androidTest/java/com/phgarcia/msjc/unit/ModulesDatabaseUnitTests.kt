package com.phgarcia.msjc.unit

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.phgarcia.msjc.database.LessonTableDAO
import com.phgarcia.msjc.database.ModuleTableDAO
import com.phgarcia.msjc.database.ModulesDatabase
import com.phgarcia.msjc.models.Lesson
import com.phgarcia.msjc.models.Module
import com.phgarcia.msjc.utils.getOrAwaitValue
import org.junit.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class ModulesDatabaseUnitTests {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var lessonsTableDao: LessonTableDAO
    private lateinit var modulesTableDao: ModuleTableDAO
    private lateinit var modulesDatabase: ModulesDatabase

    @Before
    fun createDatabase() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        modulesDatabase =
            Room.inMemoryDatabaseBuilder(context, ModulesDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        modulesTableDao = modulesDatabase.modulesTableDao
        lessonsTableDao = modulesDatabase.lessonsTableDao
    }

    @After
    @Throws(IOException::class)
    fun closeDatabase() = modulesDatabase.close()

    @Test
    @Throws(Exception::class)
    fun modulesDao_insertAndGetModule() {
        val module = Module(0L, "", "")
        modulesTableDao.insert(module)
        assertEquals(module, modulesTableDao.get(module.moduleNumber))
    }

    @Test
    @Throws(Exception::class)
    fun modulesDao_updateAndGetModule() {
        val module = Module(0L, "", "")
        modulesTableDao.insert(module)
        assertEquals(false, modulesTableDao.get(module.moduleNumber)?.finished)

        module.finished = true
        modulesTableDao.update(module)
        assertEquals(true, modulesTableDao.get(module.moduleNumber)?.finished)
    }

    @Test
    @Throws(Exception::class)
    fun modulesDao_insertAndGetMultipleModules() {
        val module0 = Module(0L, "", "")
        val module1 = Module(1L, "", "")
        val module2 = Module(2L, "", "")
        modulesTableDao.insertAll(listOf(module0, module1, module2))

        val modules: List<Module> = modulesTableDao.getAll().getOrAwaitValue()
        assertEquals(module0, modules[0])
        assertEquals(module1, modules[1])
        assertEquals(module2, modules[2])
    }

    @Test
    @Throws(Exception::class)
    fun lessonsDao_insertAndGetLesson() {
        val module = Module(0L, "", "")
        val lesson = Lesson(1L, module.moduleNumber, "", "")
        modulesTableDao.insert(module)

        val id: Long = lessonsTableDao.insert(lesson)
        assertEquals(lesson, lessonsTableDao.get(id))
    }

    @Test
    @Throws(Exception::class)
    fun lessonsDao_updateAndGetLesson() {
        val module = Module(0L, "", "")
        val lesson = Lesson(1L, module.moduleNumber, "", "")
        modulesTableDao.insert(module)

        val id: Long = lessonsTableDao.insert(lesson)
        assertEquals(false, lessonsTableDao.get(id)?.finished)

        lesson.id = id
        lesson.finished = true
        lessonsTableDao.update(lesson)
        assertEquals(true, lessonsTableDao.get(id)?.finished)
    }

    @Test
    @Throws(Exception::class)
    fun lessonsDao_insertAndGetAllLessonsForModule() {
        val module = Module(0L, "", "")
        modulesTableDao.insert(module)

        val lesson1 = Lesson(1L, module.moduleNumber, "", "")
        val lesson2 = Lesson(2L, module.moduleNumber, "", "")
        val lesson3 = Lesson(3L, module.moduleNumber, "", "")
        lessonsTableDao.insertAll(listOf(lesson1, lesson2, lesson3))

        val lessons: List<Lesson> = lessonsTableDao.getAllForModule(0L).getOrAwaitValue()
        assertEquals(lesson1, lessons[0])
        assertEquals(lesson2, lessons[1])
        assertEquals(lesson3, lessons[2])
    }

}

