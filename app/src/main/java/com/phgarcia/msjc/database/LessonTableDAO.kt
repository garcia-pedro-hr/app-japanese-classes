package com.phgarcia.msjc.database

import androidx.room.*
import com.phgarcia.msjc.models.Lesson

@Dao
interface LessonTableDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(lesson: Lesson): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(lessons: List<Lesson>)

    @Update
    fun update(lesson: Lesson)

    @Query("SELECT * FROM lesson_table WHERE id = :key")
    fun get(key: Long): Lesson?

    @Query("SELECT * FROM lesson_table")
    fun getAll(): List<Lesson>

    @Query("SELECT * FROM lesson_table WHERE module_id = :key ORDER BY lesson_number")
    fun getAllForModule(key: Long): List<Lesson>
}