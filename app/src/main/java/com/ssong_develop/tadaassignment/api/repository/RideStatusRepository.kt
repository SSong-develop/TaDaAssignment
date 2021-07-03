package com.ssong_develop.tadaassignment.api.repository

import com.ssong_develop.tadaassignment.api.RideStatusService
import com.ssong_develop.tadaassignment.api.dto.asDomainRideStatus

class RideStatusRepository(
    private val rideStatusService: RideStatusService
) {
    suspend fun fetchRideStatus(rideType : String) = rideStatusService.getRideStatus(rideType).asDomainRideStatus()
}