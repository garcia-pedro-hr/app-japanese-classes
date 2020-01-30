package com.phgarcia.msjc.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.phgarcia.msjc.models.Lesson
import com.phgarcia.msjc.models.Module

@Database(entities = [Module::class, Lesson::class], version = 1, exportSchema = true)
abstract class ModulesDatabase : RoomDatabase() {
    abstract val lessonsTableDao: LessonTableDAO
    abstract val modulesTableDao: ModuleTableDAO
}

private lateinit var INSTANCE: ModulesDatabase

fun getModulesDatabase(context: Context): ModulesDatabase {
    synchronized(ModulesDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                ModulesDatabase::class.java,
                "classes")
                .createFromAsset("database/syllabus.db")
                .build()
        }
    }
    return INSTANCE
}