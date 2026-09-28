package ree.selfcode.localbrew.di

import android.content.Context
import androidx.room.Room
import ree.selfcode.localbrew.data.firebase.AuthRepository
import ree.selfcode.localbrew.data.firebase.FirestoreRepository
import ree.selfcode.localbrew.data.local.LocalBrewDatabase
import ree.selfcode.localbrew.data.repository.CafeRepository

object Graph {
    private lateinit var appContext: Context

    fun provide(context: Context) {
        appContext = context
    }

    val authRepository: AuthRepository by lazy { AuthRepository() }
    val cafeRepository: CafeRepository by lazy { CafeRepository() }
    val firestoreRepository: FirestoreRepository by lazy { FirestoreRepository() }

    val database: LocalBrewDatabase by lazy {
        Room.databaseBuilder(appContext, LocalBrewDatabase::class.java, "local_brew_db").build()
    }
}
