package com.ssong_develop.tadaassignment.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssong_develop.tadaassignment.api.repository.RideEstimationRepository
import com.ssong_develop.tadaassignment.api.repository.RideStatusRepository
import com.ssong_develop.tadaassignment.domain.RideEstimation
import com.ssong_develop.tadaassignment.domain.RideStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(
    private val rideEstimationRepository: RideEstimationRepository,
    private val rideStatusRepository: RideStatusRepository
) : ViewModel() {

    private val _rideEstimationData = MutableLiveData<List<RideEstimation>>()
    val rideEstimationData: LiveData<List<RideEstimation>>
        get() = _rideEstimationData

    private val _errorHandleData = MutableLiveData<Throwable>()
    val errorHandleData: LiveData<Throwable>
        get() = _errorHandleData

    private val _lastSelectedPosition = MutableLiveData<Int?>()
    val lastSelectedPosition: LiveData<Int?>
        get() = _lastSelectedPosition

    private val _rideStatusData = MutableLiveData<RideStatus?>()
    val rideStatusData: LiveData<RideStatus?>
        get() = _rideStatusData

    private val _couponName = MutableLiveData<String>()
    val couponName: LiveData<String>
        get() = _couponName

    init {
        initializeFetchRideEstimationData()
    }

    private fun initializeFetchRideEstimationData() {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                rideEstimationRepository.fetchRideEstimation("타다프리미엄웰컴쿠폰")
            }.onSuccess {
                Log.d("viewModel", it.toString())
                _rideEstimationData.postValue(it.rideEstimationList)
            }.onFailure {
                // 다시 한번 api를 호출하는 방법도 있으니까 이 점을 다시한번 고민해보는 것도??
                _errorHandleData.postValue(it)
            }
        }
    }

    fun fetchRideStatusData() {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                // 이 데이터 호출하는 부분이 좀 역겹긴 함;;;
                // select 된 데이터를 담아두고 있는게 좋을지 안좋을지에 대해서 한번 생각해보는 것도...
                _lastSelectedPosition.value?.let {
                    _rideEstimationData.value?.get(
                        it
                    )?.rideType?.rideTypeName
                }?.let { rideStatusRepository.fetchRideStatus(it) }
            }.onSuccess {
                _rideStatusData.postValue(it)
            }.onFailure {
                _errorHandleData.postValue(it)
            }
        }
    }

    fun fetchRideEstimationData(couponName : String) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                rideEstimationRepository.fetchRideEstimation(couponName)
            }.onSuccess {
                Log.d("viewModel", it.toString())
                _rideEstimationData.postValue(it.rideEstimationList)
            }.onFailure {
                _errorHandleData.postValue(it)
            }
        }
    }

    fun setLastSelectedPosition(position: Int) {
        _lastSelectedPosition.value = position
    }

    fun setCouponeName(couponName : String) {
        _couponName.value = couponName
    }
}