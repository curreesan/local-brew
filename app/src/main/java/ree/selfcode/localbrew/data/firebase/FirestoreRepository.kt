package ree.selfcode.localbrew.data.firebase

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import ree.selfcode.localbrew.data.model.Cafe
import ree.selfcode.localbrew.data.model.Profile

class FirestoreRepository(
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
) {
    fun observeFavorites(uid: String): Flow<List<Cafe>> = callbackFlow {
        val listenerRegistration = firestore.collection("users")
            .document(uid)
            .collection("favorites")
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                val cafes = snapshot?.documents?.mapNotNull { doc ->
                    doc.toObject(Cafe::class.java)
                } ?: emptyList()
                trySend(cafes)
            }

        awaitClose { listenerRegistration.remove() }
    }

    suspend fun isFavorited(uid: String, cafeId: String): Boolean {
        val snapshot = firestore.collection("users")
            .document(uid)
            .collection("favorites")
            .document(cafeId)
            .get()
            .await()
        return snapshot.exists()
    }

    suspend fun getProfile(uid: String): Profile {
        val snapshot = firestore.collection("users")
            .document(uid)
            .collection("profile")
            .document("info")
            .get()
            .await()
        return snapshot.toObject(Profile::class.java) ?: Profile()
    }

    suspend fun saveProfile(uid: String, profile: Profile) {
        firestore.collection("users")
            .document(uid)
            .collection("profile")
            .document("info")
            .set(profile)
            .await()
    }

    suspend fun addFavorite(uid: String, cafe: Cafe) {
        firestore.collection("users")
            .document(uid)
            .collection("favorites")
            .document(cafe.id)
            .set(cafe)
            .await()
    }

    suspend fun removeFavorite(uid: String, cafeId: String) {
        firestore.collection("users")
            .document(uid)
            .collection("favorites")
            .document(cafeId)
            .delete()
            .await()
    }
}
