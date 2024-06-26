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
    val publications: MutableLiveData<List<Publication>> = MutableLiveData()
    val favRepository: FavRepository = FavRepository()

    var dateStart = "2022-02-02"
    var dateend = "2022-02-12"
    private val coroutineContext: CoroutineContext = newSingleThreadContext("publivs")
    private val scope = CoroutineScope(coroutineContext)

    fun init() {
        scope.launch {
            try {
                val publi = publiRepo.getPublicationsByDateRange(dateStart, dateend)
                publications.postValue(publi)
                Log.d("TPO-LOG", "Existoso: $publi")
            } catch (e: Exception) {
                Log.e("TPO-LOG", "MainViewModel: $e")
            }
        }
    }

    private val coroutineContextFav: CoroutineContext = newSingleThreadContext("publivsFav")
    private val scopeFav = CoroutineScope(coroutineContextFav)

    fun cambiarFavorite(publication: Publication) {
        scopeFav.launch {
            try {
                val currentFavorites = favRepository.getFavorites()
                if (currentFavorites.any { it.url == publication.url }) {
                    favRepository.removePublicationFav(publication)
                } else {
                    favRepository.savePublicationFav(publication)
                }
                Log.d("TPO-LOG", "Toggle favorite successful")
            } catch (e: Exception) {
                Log.e("TPO-LOG", "Error $e")
            }
        }
    }
}
