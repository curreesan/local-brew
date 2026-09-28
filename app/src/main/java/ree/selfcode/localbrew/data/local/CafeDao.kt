package ree.selfcode.localbrew.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ree.selfcode.localbrew.data.model.Cafe

@Dao
interface CafeDao {
    @Query("SELECT * FROM cafes")
    suspend fun getAll(): List<Cafe>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(cafes: List<Cafe>)

    @Query("DELETE FROM cafes")
    suspend fun clearAll()
}
