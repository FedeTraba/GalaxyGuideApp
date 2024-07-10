package com.example.myapplication.ui.filter


import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.PublicationsRepository
import com.example.myapplication.data.dbLocal.AppDatabase
import com.example.myapplication.model.Publication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlin.coroutines.CoroutineContext

class FilterViewModel : ViewModel() {
    private val publiRepo: PublicationsRepository = PublicationsRepository()
    val publicationsFiltradas: MutableLiveData<List<Publication>> = MutableLiveData()

    private val coroutineContext: CoroutineContext = newSingleThreadContext("filterPublis")
    private val scope = CoroutineScope(coroutineContext)


    fun searchPublicationsByDate(date: String) {
        scope.launch {
            try {
                val publi = publiRepo.getPublicationByDate(date)
                publicationsFiltradas.postValue(listOf(publi) as List<Publication>?)
                Log.d("TPO-LOG", "Busqueda por rango de fechas exitosa: $publi")
            } catch (e: Exception) {
                Log.e("TPO-LOG", "Error en la búsqueda por rango de fechas: $e")
            }
        }
    }
    fun searchPublicationsByDateRange(startDate: String, endDate: String, context: Context, localSearch: Boolean) {
        scope.launch {
            try {
                val publi = publiRepo.getPublicationsByDateRange(startDate, endDate, context, false)
                publicationsFiltradas.postValue(publi)
                Log.d("TPO-LOG", "Busqueda por rango de fechas exitosa: $publi")
            } catch (e: Exception) {
                Log.e("TPO-LOG", "Error en la búsqueda por rango de fechas: $e")
            }
        }
    }

    fun buscarPublicationsByKeywords(startDate: String, endDate: String, keywords: String) {
        scope.launch {
            try {
                val publi = publiRepo.buscarPublicationsByKeywords(startDate, endDate, keywords)
                publicationsFiltradas.postValue(publi)
                Log.d("TPO-LOG", "Busqueda por palabras clave exitosa: $publi")
            } catch (e: Exception) {
                Log.e("TPO-LOG", "Error en la búsqueda por palabras clave: $e")
            }
        }
    }
}
