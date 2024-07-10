package com.example.myapplication.data

import android.content.Context
import android.util.Log
import com.example.myapplication.data.dbLocal.AppDatabase
import com.example.myapplication.data.dbLocal.toPublication
import com.example.myapplication.data.dbLocal.toPublicationList
import com.example.myapplication.data.dbLocal.toPublicationLocal
import com.example.myapplication.data.dbLocal.toPublicationLocalList
import com.example.myapplication.model.Publication
import kotlinx.coroutines.delay

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PublicationsDataSource {

    val API_KEY = "1ReKnh1IPAXzXzTT3Jbn8KQpsRIAHhVjIkuYvCkQ"
    val API_BASE_URL = "https://api.nasa.gov/"
    val api: PublicationsAPI



    init {
        api = Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PublicationsAPI::class.java)
    }


    suspend fun getPublicationByDate(date: String,): Publication? {
        val result = api.getPublicationByDate(API_KEY, date)
        return if (result.isSuccessful) {
            Log.d("DEMO_APIS", "Publications DataSource Resultado Exitoso")
            result.body()
        } else {
            Log.d("DEMO_APIS", "Publications DataSource Resultado Fallido")
            null
        }
    }


    suspend fun getPublicationsByDateRange(startDate: String, endDate: String, context: Context, localSearch: Boolean): List<Publication> {

        // Recupero la info localmente si existe
        var db = AppDatabase.getInstance(context)
        var publicationsLocal = db.publicationsDao().getAll()
        if (publicationsLocal.size > 0 && localSearch){
            Log.d("DEMO_APIS", "Devuelvo lista local")
            return publicationsLocal.toPublicationList() as ArrayList<Publication>
        }
        delay(5000)


        // Recupero la info de la api via retrofit
        var result = api.getPublicationsByDateRange(API_KEY,startDate,endDate)
        return if (result.isSuccessful) {
            Log.d("DEMO_APIS","Publications DataSource Resultado Exitoso")
            var publiList = result.body() ?: ArrayList<Publication>()
            if(publiList.size > 0){
                db.publicationsDao().save(*publiList.toPublicationLocalList().toTypedArray())
            }
            publiList

        } else {
            Log.d("DEMO_APIS","Publications DataSource Resultado Exitoso")
            ArrayList<Publication>()
        }
    }

    suspend fun buscarPublicationsByKeywords(startDate: String, endDate: String, keywords: String): List<Publication> {
        val result = api.getPublicationsByDateRange(API_KEY, startDate, endDate)

        return if (result.isSuccessful) {
            val publications = result.body() ?: ArrayList()
            publications.filter { it.title.contains(keywords, ignoreCase = true) || it.explanation.contains(keywords, ignoreCase = true) }
        } else {
            Log.d("DEMO_APIS", "Publications DataSource Resultado Fallido")
            ArrayList()
        }
    }
}