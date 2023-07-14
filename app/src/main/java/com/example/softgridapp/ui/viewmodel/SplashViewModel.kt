package com.example.softgridapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.softgridapp.data.model.SplashResponse
import com.example.softgridapp.data.repository.SplashRepository
import com.example.softgridapp.utils.NetworkResult
import com.example.softgridapp.utils.NetworkUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val splashRepository: SplashRepository,
    private val networkUtil: NetworkUtil
) : ViewModel() {

    private val _splashResponseData = MutableLiveData<NetworkResult<SplashResponse>>()
    val splashResponseData: MutableLiveData<NetworkResult<SplashResponse>>
        get() = _splashResponseData

    init {
        fetchSplashData()
    }

    private fun fetchSplashData() {
        viewModelScope.launch {
            _splashResponseData.postValue(NetworkResult.loading())
            if (networkUtil.isNetworkConnected()) {
                splashRepository.getSplashData().let {
                    if (it.isSuccessful && it.body() != null) {
                        _splashResponseData.postValue(NetworkResult.success(it.body()!!))
                    } else _splashResponseData.postValue(NetworkResult.Error(it.message()))
                }
            } else _splashResponseData.postValue(NetworkResult.Error("No internet connection"))
        }
    }
}