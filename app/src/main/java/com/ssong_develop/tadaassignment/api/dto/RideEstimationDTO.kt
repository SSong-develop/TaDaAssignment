package com.ssong_develop.tadaassignment.api.dto

import androidx.lifecycle.Transformations.map
import com.google.gson.annotations.SerializedName
import com.ssong_develop.tadaassignment.domain.ResponseRideEstimation
import com.ssong_develop.tadaassignment.domain.RideEstimation
import com.ssong_develop.tadaassignment.domain.RideType
import com.ssong_develop.tadaassignment.domain.RideTypeImage

data class ResponseRideEstimationDTO(
    @SerializedName("rideEstimations") val rideEstimationList : List<RideEstimationDTO>
)

data class RideEstimationDTO(
    @SerializedName("rideType") val rideType : RideTypeDTO,
    @SerializedName("estimatedCost") val estimatedCost : Int,
    @SerializedName("originalCost") val originalCost : Int
)

data class RideTypeDTO(
    @SerializedName("value") val rideTypeValue : String,
    @SerializedName("image") val rideTypeImageUrl : RideTypeImageDTO,
    @SerializedName("name") val rideTypeName : String,
    @SerializedName("description") val rideTypeDescription : String
)

data class RideTypeImageDTO(
    @SerializedName("url") val rideTypeImageUrl : String,
    @SerializedName("width") val rideTypeImageWidth : Int,
    @SerializedName("height") val rideTypeImageHeight : Int
)

fun ResponseRideEstimationDTO.asDomainResponse() : ResponseRideEstimation {
    return ResponseRideEstimation(
        rideEstimationList = rideEstimationList.asDomainRideEstimation()
    )
}

fun List<RideEstimationDTO>.asDomainRideEstimation() : List<RideEstimation> {
    return map {
        RideEstimation(
            rideType = it.rideType.asDomainRideType(),
            estimateCost = it.estimatedCost,
            originalCost = it.originalCost
        )
    }
}

fun RideTypeImageDTO.asDomainRideTypeImage() : RideTypeImage {
    return RideTypeImage(
        rideTypeImageUrl = this.rideTypeImageUrl,
        rideTypeImageWidth = this.rideTypeImageWidth,
        rideTypeImageHeight = this.rideTypeImageHeight
    )
}

fun RideTypeDTO.asDomainRideType() : RideType {
    return RideType(
        rideTypeValue = this.rideTypeValue,
        rideTypeImageUrl = this.rideTypeImageUrl.asDomainRideTypeImage(),
        rideTypeName = this.rideTypeName,
        rideTypeDescription = this.rideTypeDescription
    )
}