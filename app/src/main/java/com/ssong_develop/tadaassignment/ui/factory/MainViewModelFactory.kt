package com.ssong_develop.tadaassignment.ui.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ssong_develop.tadaassignment.api.repository.RideEstimationRepository
import com.ssong_develop.tadaassignment.api.repository.RideStatusRepository
import com.ssong_develop.tadaassignment.ui.viewmodel.MainViewModel

class MainViewModelFactory(
    private val rideEstimationRepository: RideEstimationRepository,
    private val rideStatusRepository: RideStatusRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        require(modelClass.isAssignableFrom(MainViewModel::class.java)) { "unknown class name" }
        return MainViewModel(
            rideEstimationRepository,
            rideStatusRepository
        ) as T
    }
}