import com.example.myapplication.model.FavPublication
import com.example.myapplication.model.Publication
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FavDataSource {
    private val db = FirebaseFirestore.getInstance()
    private val collection = "publicaciones"

    suspend fun savePublication(publication: Publication) {
        val user = FirebaseAuth.getInstance().currentUser
        val favPublication = FavPublication(
            date = publication.date,
            explanation = publication.explanation,
            hdurl = publication.hdurl,
            title = publication.title,
            url = publication.url,
            copyright = publication.copyright,
            usuarioID = user?.email
        )
        db.collection(collection)
            .add(favPublication).await()
    }

    suspend fun getFavorites(): List<FavPublication> {
        val user = FirebaseAuth.getInstance().currentUser
        val result = db.collection(collection).whereEqualTo("usuarioID", user?.email).get().await()
        return result.documents.mapNotNull { it.toObject(FavPublication::class.java) }
    }

    suspend fun removeFavorite(publication: Publication) {
        val user = FirebaseAuth.getInstance().currentUser
        val querySnapshot = db.collection(collection)
            .whereEqualTo("usuarioID", user?.email)
            .whereEqualTo("url", publication.url)
            .get()
            .await()

        for (document in querySnapshot.documents) {
            db.collection(collection).document(document.id).delete().await()
        }
    }
}
