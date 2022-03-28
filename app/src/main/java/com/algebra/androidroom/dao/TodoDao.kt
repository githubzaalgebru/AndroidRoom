package com.algebra.androidroom.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.algebra.androidroom.model.TodoEntity

@Dao
interface TodoDao {

    @Query( "SELECT * FROM todo_items" )
    fun getAll( ) : LiveData< List< TodoEntity > >

    @Query( "SELECT * FROM todo_items" )
    fun getAllAsList( ): List< TodoEntity >

    @Query( "SELECT * FROM todo_items WHERE title LIKE '%' || :param || '%'" )
    fun getByTitle( param : String? ): List< TodoEntity >

    @Query( "SELECT * FROM todo_items WHERE content LIKE '%' || :param || '%'" )
    fun getByDesc( param : String? ): List< TodoEntity >

    @Insert
    fun insert( vararg todo : TodoEntity )

    @Query( "DELETE FROM todo_items" )
    fun deleteAll( )

}