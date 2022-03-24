package com.algebra.androidroom.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity( tableName="todo_items" )
class TodoEntity(
    @PrimaryKey(autoGenerate = true)
    var id          : Int,
    @ColumnInfo(name = "title")   var title       : String,
    @ColumnInfo(name = "content") var description : String
) {
    override fun toString(): String {
        return "{\"id\":$id, \"title\":\"$title\", \"description\":\"$description\" }"
    }
}