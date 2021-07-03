package com.ssong_develop.tadaassignment.api.dto

import com.google.gson.annotations.SerializedName
import com.ssong_develop.tadaassignment.domain.RideStatus

data class RideStatusDTO(
    @SerializedName("statusMessage") val statusMessage: String
)

fun RideStatusDTO.asDomainRideStatus(): RideStatus {
    return RideStatus(
        statusMessage = this.statusMessage
    )
}