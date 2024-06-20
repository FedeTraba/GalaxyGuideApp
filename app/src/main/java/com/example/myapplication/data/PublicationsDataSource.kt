package com.example.myapplication.data

import android.util.Log
import com.example.myapplication.model.Publication

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

    suspend fun getTodayPublication(): Publication? {
        val result = api.getTodayPublication(API_KEY)

        return if (result.isSuccessful) {
            Log.d("DEMO_APIS", "Publications DataSource Resultado Exitoso")
            result.body()
        } else {
            Log.d("DEMO_APIS", "Publications DataSource Resultado Fallido")
            null
        }
    }


    suspend fun getPublicationByDate(date: String): Publication? {
        val result = api.getPublicationByDate(API_KEY, date)

        return if (result.isSuccessful) {
            Log.d("DEMO_APIS", "Publications DataSource Resultado Exitoso")
            result.body()
        } else {
            Log.d("DEMO_APIS", "Publications DataSource Resultado Fallido")
            null
        }
    }


    suspend fun getPublicationsByDateRange(startDate: String, endDate: String): List<Publication> {
        var result = api.getPublicationsByDateRange(API_KEY,startDate,endDate)

        return if (result.isSuccessful) {
            Log.d("DEMO_APIS","Publications DataSource Resultado Exitoso")
            result.body() ?: ArrayList<Publication>()
        } else {
            Log.d("DEMO_APIS","Publications DataSource Resultado Exitoso")
            ArrayList<Publication>()
        }
    }
}