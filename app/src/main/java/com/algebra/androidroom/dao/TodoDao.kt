package com.algebra.androidroom.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.algebra.androidroom.model.TodoEntity

@Dao
interface TodoDao {

    @Query( "SELECT * FROM todo_items" )
    fun getAll( ) : List< TodoEntity >

    @Insert
    fun insert( vararg todo : TodoEntity )

}