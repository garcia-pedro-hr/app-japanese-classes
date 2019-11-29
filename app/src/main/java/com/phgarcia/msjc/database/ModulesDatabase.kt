package com.phgarcia.msjc.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.phgarcia.msjc.models.Lesson
import com.phgarcia.msjc.models.Module

@Database(entities = [Module::class, Lesson::class], version = 1, exportSchema = false)
abstract class ModulesDatabase : RoomDatabase() {
    abstract val lessonsTableDao: LessonTableDAO
    abstract val modulesTableDao: ModuleTableDAO
}

private lateinit var INSTANCE: ModulesDatabase

fun getDatabase(context: Context): ModulesDatabase {
    synchronized(ModulesDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                ModulesDatabase::class.java,
                "classes")
                .fallbackToDestructiveMigration()
                .build()
        }
    }
    return INSTANCE
}