package com.example.myapplication.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.PublicationsRepository
import com.example.myapplication.model.Publication

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlin.coroutines.CoroutineContext

class PublicationViewModel : ViewModel() {
    val publiRepo: PublicationsRepository = PublicationsRepository()
    var publication = MutableLiveData<Publication?>()

    private val coroutineContext: CoroutineContext = newSingleThreadContext("publivs")
    private val scope = CoroutineScope(coroutineContext)

    fun init(date: String){
        scope.launch {
            kotlin.runCatching {
                publiRepo.getPublicationByDate(date)
            }.onSuccess {
                publication.postValue(it ?: Publication())
            }.onFailure {
                val publi = Publication()
                publi.title = "ERRORRR"
                publication.postValue(Publication())
            }
        }
    }


}