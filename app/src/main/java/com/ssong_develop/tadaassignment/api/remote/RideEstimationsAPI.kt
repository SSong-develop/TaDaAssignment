package com.ssong_develop.tadaassignment.api

import com.ssong_develop.tadaassignment.api.dto.ResponseRideEstimationDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface RideEstimationService{
    @GET("ListRideEstimations")
    suspend fun getRideEstimations(
        @Query("coupon") coupon : String
    ) : ResponseRideEstimationDTO
}

