package com.example.myapplication.data.dbLocal

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PublicationsDAO {

    @Query("SELECT * FROM publicaciones ")
    fun getAll() : List<PublicationLocal>

    @Query("SELECT * FROM publicaciones WHERE date = :date LIMIT 1")
    fun getByDate(date: String) : PublicationLocal

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(vararg publication: PublicationLocal)

    @Delete
    fun delete(publication: PublicationLocal)
}