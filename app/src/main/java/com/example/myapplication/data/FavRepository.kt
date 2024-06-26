import com.example.myapplication.model.FavPublication
import com.example.myapplication.model.Publication

class FavRepository {
    private val favDataSource = FavDataSource()

    suspend fun savePublicationFav(publication: Publication) {
        favDataSource.savePublication(publication)
    }
    suspend fun removePublicationFav(publication: Publication) {
        favDataSource.removeFavorite(publication)
    }
    suspend fun getFavorites(): List<Publication> {
        val favs = favDataSource.getFavorites()
        return favs.map {
            Publication(
                date = it.date,
                explanation = it.explanation,
                hdurl = it.hdurl,
                title = it.title,
                url = it.url,
                copyright = it.copyright
            )
        }
    }


}
