package com.algebra.androidroom.database

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class AppExecutors private constructor(
    private val diskIO     : Executor,
    private val networkIO  : Executor,
    private val mainThread : Executor
) {

    fun diskIO( )     : Executor = diskIO
    fun mainThread( ) : Executor = mainThread
    fun networkIO( )  : Executor = networkIO

    private class MainThreadExecutor : Executor {
        private val mainThreadHandler = Handler(Looper.getMainLooper())
        override fun execute( command : Runnable ) {
            mainThreadHandler.post( command )
        }
    }

    companion object {
        private val LOCK                     = Any()
        private var sInstance: AppExecutors? = null

        val instance: AppExecutors?
            get( ) {
                synchronized( LOCK ) {
                    if (sInstance==null) {
                        sInstance = AppExecutors(
                            Executors.newSingleThreadExecutor( ),
                            Executors.newFixedThreadPool( 3 ),
                            MainThreadExecutor( )
                        )
                    }
                }
                return sInstance
            }
    }
}