package com.phgarcia.msjc.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "lesson_table",
    foreignKeys = [
        ForeignKey(entity = Module::class,
            parentColumns = ["module_number"],
            childColumns = ["module_id"],
            onDelete = ForeignKey.CASCADE)])
data class Lesson (
    @ColumnInfo(name = "lesson_number")
    var lessonNumber: Long = -1L,

    @ColumnInfo(name = "module_id", index = true)
    var moduleId: Long = -1L,

    @ColumnInfo(name = "portuguese_title")
    var ptTitle: String = "",

    @ColumnInfo(name = "japanese_title")
    var jpTitle: String = ""
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L

    var finished: Boolean = false
}