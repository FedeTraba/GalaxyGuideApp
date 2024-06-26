import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.model.FavPublication
import com.example.myapplication.model.Publication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlin.coroutines.CoroutineContext

class FavoritesViewModel : ViewModel() {
    private val repoFav: FavRepository = FavRepository()

    private val coroutineContextFavorites: CoroutineContext = newSingleThreadContext("publivsFavorites")
    private val scopeFavorites = CoroutineScope(coroutineContextFavorites)

    private val _favorites = MutableLiveData<List<Publication>>()
    val favorites: MutableLiveData<List<Publication>> get() = _favorites

    init {
        cargarFavorites()
    }

    private fun cargarFavorites() {
        scopeFavorites.launch {
            try {
                val fav = repoFav.getFavorites()
                _favorites.postValue(fav)
                Log.i("TPO-LOG", "Get favorites: $favorites")
            } catch (e: Exception) {
                Log.e("TPO-LOG", "Error $e")
            }
        }
    }

    fun cambiarFavorite(publication: Publication) {
        scopeFavorites.launch {
            try {
                val currentFavorites = _favorites.value ?: emptyList()
                if (currentFavorites.any { it.date == publication.date }) {
                    repoFav.removePublicationFav(publication)
                } else {
                    repoFav.savePublicationFav(publication)
                }
                cargarFavorites()
            } catch (e: Exception) {
                Log.e("TPO-LOG", "Error $e")
            }
        }
    }
}





