package ree.selfcode.localbrew.data.repository

import android.location.Location
import ree.selfcode.localbrew.BuildConfig
import ree.selfcode.localbrew.data.local.CafeDao
import ree.selfcode.localbrew.data.model.Cafe
import ree.selfcode.localbrew.data.remote.GeoapifyApiService
import ree.selfcode.localbrew.data.remote.RetrofitInstance
import ree.selfcode.localbrew.di.Graph

data class CafeFetchResult(
    val cafes: List<Cafe>,
    val isFromCache: Boolean
)

class CafeRepository(
    private val api: GeoapifyApiService = RetrofitInstance.api,
    private val cafeDao: CafeDao = Graph.database.cafeDao()
) {
    suspend fun getNearbyCafes(lat: Double, lon: Double, radiusMeters: Int, name: String? = null): CafeFetchResult {
        return try {
            val response = api.getNearbyCafes(
                categories = "catering.cafe",
                filter = "circle:$lon,$lat,$radiusMeters",
                apiKey = BuildConfig.GEOAPIFY_API_KEY,
                name = name
            )
            val cafes = response.features.map { feature ->
                val props = feature.properties
                val results = FloatArray(1)
                Location.distanceBetween(lat, lon, props.lat, props.lon, results)
                Cafe(
                    id = props.place_id,
                    name = props.name ?: "Unknown",
                    address = props.formatted ?: "",
                    lat = props.lat,
                    lon = props.lon,
                    distanceMeters = results[0]
                )
            }
            if (name == null) {
                cafeDao.clearAll()
                cafeDao.insertAll(cafes)
            }
            CafeFetchResult(cafes = cafes, isFromCache = false)
        } catch (e: Exception) {
            if (name == null) {
                CafeFetchResult(cafes = cafeDao.getAll(), isFromCache = true)
            } else {
                throw e
            }
        }
    }
}
