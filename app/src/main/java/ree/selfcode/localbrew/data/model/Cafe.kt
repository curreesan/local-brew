package ree.selfcode.localbrew.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "cafes")
@Parcelize
data class Cafe(
    @PrimaryKey val id: String = "",
    val name: String = "",
    val address: String = "",
    val lat: Double = 0.0,
    val lon: Double = 0.0,
    val distanceMeters: Float = 0f
) : Parcelable
