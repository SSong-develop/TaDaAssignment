package com.ssong_develop.tadaassignment.api.repository

import com.ssong_develop.tadaassignment.api.RideEstimationService
import com.ssong_develop.tadaassignment.api.dto.asDomainResponse

class RideEstimationRepository(
    private val rideEstimationService: RideEstimationService
) {
    suspend fun fetchRideEstimation(coupon: String) =
        rideEstimationService.getRideEstimations(coupon).asDomainResponse()
}