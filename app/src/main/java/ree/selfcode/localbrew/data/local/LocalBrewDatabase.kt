package ree.selfcode.localbrew.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import ree.selfcode.localbrew.data.model.Cafe

@Database(entities = [Cafe::class], version = 1)
abstract class LocalBrewDatabase : RoomDatabase() {
    abstract fun cafeDao(): CafeDao
}
