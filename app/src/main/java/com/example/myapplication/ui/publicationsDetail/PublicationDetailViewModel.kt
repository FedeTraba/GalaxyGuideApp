package com.example.myapplication.ui.publicationsDetail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.PublicationsRepository
import com.example.myapplication.model.Publication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlin.coroutines.CoroutineContext

class PublicationDetailViewModel : ViewModel(){
    val publiRepo: PublicationsRepository = PublicationsRepository()

    private val coroutineContext: CoroutineContext = newSingleThreadContext("publivs")
    private val scope = CoroutineScope(coroutineContext)

    private val _publication = MutableLiveData<Publication?>()
    val publication: MutableLiveData<Publication?> get() = _publication

    fun init(date: String) {
        scope.launch {
            try{
                var publi = publiRepo.getPublicationByDate(date)
                _publication.postValue(publi)
                Log.d("TPO-LOG","Existoso"+ publi.toString())
            }catch (e: Exception){
                Log.e("TPO-LOG", "MainViewModel:$e" )
            }

        }
    }
}