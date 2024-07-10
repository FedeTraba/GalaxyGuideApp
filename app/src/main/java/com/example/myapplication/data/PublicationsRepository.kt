package com.example.myapplication.data

import com.example.myapplication.model.Publication
import android.content.Context

class PublicationsRepository {
    private val publiDs = PublicationsDataSource()

    suspend fun getPublicationByDate(date: String): Publication? {
        return publiDs.getPublicationByDate(date)
    }

    suspend fun getPublicationsByDateRange(startDate: String, endDate: String, context: Context, localSearch: Boolean): List<Publication> {
        return publiDs.getPublicationsByDateRange(startDate, endDate, context, localSearch)
    }

    suspend fun buscarPublicationsByKeywords(startDate: String, endDate: String, keywords: String): List<Publication> {
        return publiDs.buscarPublicationsByKeywords(startDate, endDate, keywords)
    }
}