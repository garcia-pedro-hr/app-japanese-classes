package com.phgarcia.msjc.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "module_table")
data class Module (
    @PrimaryKey
    @ColumnInfo(name = "module_number")
    var moduleNumber: Long = -1L,

    @ColumnInfo(name = "portuguese_title")
    var ptTitle: String = "",

    @ColumnInfo(name = "japanese_title")
    var jpTitle: String = ""
) {
    var finished: Boolean = false
}