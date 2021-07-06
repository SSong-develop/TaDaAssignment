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
import com.ssong_develop.tadaassignment.local.SharedPref
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(
    private val rideEstimationRepository: RideEstimationRepository,
    private val rideStatusRepository: RideStatusRepository,
    private val sharedPref: SharedPref
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
                rideEstimationRepository.fetchRideEstimation("")
            }.onSuccess {
                Log.d("viewModel", it.toString())
                _rideEstimationData.postValue(it.rideEstimationList)
            }.onFailure {
                _errorHandleData.postValue(it)
            }
        }
    }

    fun fetchRideStatusData() {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
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

    fun fetchRideEstimationData(couponName: String) {
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

    fun setCouponName(couponName: String) {
        _couponName.value = couponName
    }

    // 단 한번만 실행하도록 하는 함수
    fun setSingleInvoke() {
        sharedPref.onSingleInvoke()
    }

    // 단 한번만 실행을 했는지 안했는지를 확인하는 함수
    fun isSingleInvoke(): Boolean = sharedPref.isAlreadySingleInvoke()

    // 단 한번만 실행했던 것을 해제해서 다시 호출할 수 있도록 하는 함수
    fun releaseSingleInvoke() {
        sharedPref.releaseSingleInvoke()
    }
}