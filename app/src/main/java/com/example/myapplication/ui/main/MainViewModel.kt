import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.PublicationsRepository
import com.example.myapplication.model.Publication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.coroutines.CoroutineContext

class MainViewModel : ViewModel() {
    val publiRepo: PublicationsRepository = PublicationsRepository()
    val publications: MutableLiveData<List<Publication>> = MutableLiveData()
    val favRepository: FavRepository = FavRepository()

    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    var dateStart: String = LocalDate.now().minusDays(20).format(formatter)
    var dateEnd: String = LocalDate.now().format(formatter)
    private val coroutineContext: CoroutineContext = newSingleThreadContext("publivs")
    private val scope = CoroutineScope(coroutineContext)

    fun init(context: Context) {
        scope.launch {
            try {
                val publi = publiRepo.getPublicationsByDateRange(dateStart, dateEnd, context, true)
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
                if (currentFavorites.any { it.date == publication.date }) {
                    favRepository.removePublicationFav(publication)
                } else {
                    favRepository.savePublicationFav(publication)
                }
                Log.d("TPO-LOG", "cambio exitoso del favorito")
            } catch (e: Exception) {
                Log.e("TPO-LOG", "Error $e")
            }
        }
    }
}
