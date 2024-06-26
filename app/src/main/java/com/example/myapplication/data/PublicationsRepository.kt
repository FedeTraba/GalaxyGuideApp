package com.example.myapplication.data

import com.example.myapplication.model.Publication
import com.google.api.Context

class PublicationsRepository {
    private val publiDs = PublicationsDataSource()

    suspend fun getTodayPublication(): Publication? {
        return publiDs.getTodayPublication()
    }

    suspend fun getPublicationByDate(date: String): Publication? {
        return publiDs.getPublicationByDate(date)
    }

    suspend fun getPublicationsByDateRange(startDate: String, endDate: String): List<Publication> {
        return publiDs.getPublicationsByDateRange(startDate, endDate)
    }
}