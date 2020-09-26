package com.sangeetha.mymu_spotifyclone.data.database

import com.google.firebase.firestore.FirebaseFirestore
import com.sangeetha.mymu_spotifyclone.data.entity.SongEntity
import com.sangeetha.mymu_spotifyclone.utils.SONG_COLLECTION
import kotlinx.coroutines.tasks.await
import java.lang.Exception


class SongDatabase {

    private val fireStore = FirebaseFirestore.getInstance()

    private val songCollection = fireStore.collection(SONG_COLLECTION)

    suspend fun getAllSongs(): List<SongEntity> {
        return try {
            songCollection.get().await().toObjects(SongEntity::class.java)
        } catch (exception: Exception) {
            emptyList()
        }
    }
}