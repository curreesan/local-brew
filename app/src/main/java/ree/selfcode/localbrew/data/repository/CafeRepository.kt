package ree.selfcode.localbrew.data.repository

import ree.selfcode.localbrew.BuildConfig
import ree.selfcode.localbrew.data.local.CafeDao
import ree.selfcode.localbrew.data.model.Cafe
import ree.selfcode.localbrew.data.remote.GeoapifyApiService
import ree.selfcode.localbrew.data.remote.RetrofitInstance
import ree.selfcode.localbrew.di.Graph
import ree.selfcode.localbrew.util.distanceMetersBetween

private const val CAFE_CATEGORY = "catering.cafe"

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
                categories = CAFE_CATEGORY,
                filter = "circle:$lon,$lat,$radiusMeters",
                apiKey = BuildConfig.GEOAPIFY_API_KEY,
                name = name
            )
            val cafes = response.features.map { feature ->
                val props = feature.properties
                Cafe(
                    id = props.place_id,
                    name = props.name ?: "Unknown",
                    address = props.formatted ?: "",
                    lat = props.lat,
                    lon = props.lon,
                    distanceMeters = distanceMetersBetween(lat, lon, props.lat, props.lon)
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
