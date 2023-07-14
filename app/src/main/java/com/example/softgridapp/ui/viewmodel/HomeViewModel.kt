package com.example.softgridapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.softgridapp.data.model.SplashResponse
import com.example.softgridapp.data.model.homeresponse_model.HomeResponse
import com.example.softgridapp.data.repository.HomeRepository
import com.example.softgridapp.utils.NetworkResult
import com.example.softgridapp.utils.NetworkUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository,
    private val networkUtil: NetworkUtil
) : ViewModel() {
    private val _homeResponseData = MutableLiveData<NetworkResult<HomeResponse>>()
    val homeResponseData: MutableLiveData<NetworkResult<HomeResponse>>
        get() = _homeResponseData

    private val _deviceTokenData = MutableLiveData<NetworkResult<ResponseBody>>()
    val deviceTokenData: MutableLiveData<NetworkResult<ResponseBody>>
        get() = _deviceTokenData

    init {
        fetchHomeData()
    }

    private fun fetchHomeData() {
        viewModelScope.launch {
            _homeResponseData.postValue(NetworkResult.loading())
            if (networkUtil.isNetworkConnected()) {
                homeRepository.homeData().let {
                    if (it.isSuccessful && it.body() != null) {
                        _homeResponseData.postValue(NetworkResult.success(it.body()!!))
                    } else _homeResponseData.postValue(NetworkResult.Error(it.message()))
                }
            } else _homeResponseData.postValue(NetworkResult.Error("No internet connection"))
        }
    }

      fun sendDeviceToken(token: String) {
        viewModelScope.launch {
            if (networkUtil.isNetworkConnected()) {
                homeRepository.getSendDeviceToken(token).let {
                    if (it != null) {
                        _deviceTokenData.postValue(NetworkResult.success(it))
                    } else _deviceTokenData.postValue(NetworkResult.Error(it))
                }
            }
        }
    }
}