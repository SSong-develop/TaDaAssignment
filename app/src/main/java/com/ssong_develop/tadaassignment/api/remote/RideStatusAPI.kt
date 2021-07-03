package com.ssong_develop.tadaassignment.api

import com.ssong_develop.tadaassignment.api.dto.RideStatusDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface RideStatusService {
    @GET("RideStatus")
    suspend fun getRideStatus(
        @Query("rideType") rideType: String
    ): RideStatusDTO
}


