package com.example.myapplication.data.dbLocal

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.coroutineScope


abstract class AppDatabase : RoomDatabase() {
    abstract fun publicationsDao() : PublicationsDAO

    companion object {
        @Volatile // con esto se puede acceder desde multiples hilos de ejecuccion
        private var _instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase = _instance ?: synchronized(this){
            _instance ?: buildDatabase(context)
        }

        private fun buildDatabase(context: Context) : AppDatabase = Room.databaseBuilder(context, AppDatabase::class.java, "app_database")
            .fallbackToDestructiveMigration()
            .build()


        suspend fun  clean(context: Context) = coroutineScope {
            launch(Dispatchers.IO){
                getInstance(context).clearAllTables()
            }
        }
    }

}