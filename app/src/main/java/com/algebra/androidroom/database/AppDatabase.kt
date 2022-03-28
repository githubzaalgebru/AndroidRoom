package com.algebra.androidroom.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.algebra.androidroom.dao.TodoDao
import com.algebra.androidroom.model.TodoEntity


@Database( entities = arrayOf( TodoEntity::class ), version = 1 )
abstract class AppDatabase : RoomDatabase( ) {

    abstract fun todoDao( ) : TodoDao

    companion object {
        @Volatile private var instance : AppDatabase? = null
        private val LOCK = Any( )

        operator  fun invoke( context : Context ) = instance ?: synchronized( LOCK ) {
            instance ?: buildDatabase( context ).also{ instance = it }
        }

        private fun buildDatabase( context : Context ) = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "todos.db"
                )
                .allowMainThreadQueries( )
                .build( )
    }
}