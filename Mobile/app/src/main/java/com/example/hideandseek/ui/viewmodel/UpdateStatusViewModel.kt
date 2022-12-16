package com.example.hideandseek.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hideandseek.data.api.PostData
import com.example.hideandseek.data.api.ResponseData
import com.example.hideandseek.data.repository.ApiRepository
import com.hadilq.liveevent.LiveEvent
import kotlinx.coroutines.launch
import retrofit2.Response

class UpdateStatusViewModel: ViewModel() {
    private val apiRepository = ApiRepository.instance

    private val _errorMessage = LiveEvent<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    private val _postResponse = LiveEvent<Response<ResponseData.ResponseUpdateStatus>>()
    // val postResponse = LiveData<Response<ResponseData.ResponseUpdateStatus>> get() = _postResponse

    private val _isUpdated = LiveEvent<Int>()
    val isUpdated: LiveData<Int> get() = _isUpdated

    fun postUpdateStatus(status: Int) {
        viewModelScope.launch {
            try {
                val postData = PostData.UpdateStatus(myId, status)
                val response = apiRepository.updateStatus(postData)
                Log.d("postData", "$postData")

                _postResponse.postValue(response)
                if (response.isSuccessful) {
                    Log.d(
                        "UpdateStatusSuccess",
                        "${response}\n${response.body()}"
                    )
                } else {
                    Log.d("UpdateStatusFailure", "$response")
                }
            } catch (e: Exception) {
                _errorMessage.postValue(e.message)
                e.printStackTrace()
            }
        }
    }
}