package com.ssong_develop.tadaassignment.domain


data class ResponseRideEstimation(
    val rideEstimationList: List<RideEstimation>
)

data class RideEstimation(
    val rideType: RideType,
    val estimateCost: Int,
    val originalCost: Int
)

data class RideType(
    val rideTypeValue: String,
    val rideTypeImageUrl: RideTypeImage,
    val rideTypeName: String,
    val rideTypeDescription: String
)

data class RideTypeImage(
    val rideTypeImageUrl: String,
    val rideTypeImageWidth: Int,
    val rideTypeImageHeight: Int
)