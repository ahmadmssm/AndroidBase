package com.ams.androiddevkit.baseClasses.roomDB

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

@Suppress("unused")
abstract class BaseDatabaseClient<DB>(private val context: Context, private val klazz: Class<DB>) where DB: RoomDatabase {

    open fun getDatabase() = getDatabaseBuilder().build()

    protected open fun getDatabaseBuilder() = Room.databaseBuilder(
        context,
        klazz,
        getDatabaseName()
    ).fallbackToDestructiveMigration()

    protected abstract fun getDatabaseName(): String
}