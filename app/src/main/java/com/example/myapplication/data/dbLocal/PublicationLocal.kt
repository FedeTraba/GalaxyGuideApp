package com.example.myapplication.data.dbLocal

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "publicaciones")
data class PublicationLocal (
    @PrimaryKey var date: String,
    var explanation: String,
    var hdurl: String? = null,
    var title: String,
    var url: String,
    var copyright: String? = null,
)