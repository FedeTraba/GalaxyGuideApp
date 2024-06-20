package com.example.myapplication.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.PublicationsRepository
import com.example.myapplication.model.Publication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlin.coroutines.CoroutineContext

class MainViewModel : ViewModel() {

    val publiRepo: PublicationsRepository = PublicationsRepository()

    val publications: MutableLiveData<ArrayList<Publication>> = MutableLiveData<ArrayList<Publication>>()

    var dateStart = "2022-02-02"
    var dateend = "2022-03-02"
    private val coroutineContext: CoroutineContext = newSingleThreadContext("publivs")
    private val scope = CoroutineScope(coroutineContext)

    fun init() {
        scope.launch {
            kotlin.runCatching {
                publiRepo.getPublicationsByDateRange(dateStart,dateend)
            }.onSuccess {
                Log.d("DEMO_APIS", "Universities onSuccess")
                publications.postValue(it as ArrayList<Publication>?)
                Log.d("DEMO_APIS", it.toString())
            }.onFailure {
                Log.e("DEMO_APIS", "Universities Error: " + it)
                publications.postValue(ArrayList<Publication>())
            }
        }
    }
}