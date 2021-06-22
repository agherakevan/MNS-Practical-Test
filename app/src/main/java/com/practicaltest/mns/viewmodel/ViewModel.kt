package com.practicaltest.mns.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.practicaltest.mns.api.request.ClassTestStudentRequest
import com.practicaltest.mns.api.response.GetClassTestStudentResponse
import com.practicaltest.mns.repo.OfferRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

class ViewModel(private val app: Application) : AndroidViewModel(app), CoroutineScope {

    var offerData: MutableLiveData<GetClassTestStudentResponse> =
        MutableLiveData<GetClassTestStudentResponse>().apply { postValue(null) }
    val mShowProgressBar = MutableLiveData(true)
    val mShowNetworkError: MutableLiveData<Boolean> = MutableLiveData()
    val mShowApiError = MutableLiveData<String>()
    private var mRepository = OfferRepository.getInstance()

    var job: Job? = null

    fun getOfferList(page: Int,request:ClassTestStudentRequest) {

        job = CoroutineScope(Dispatchers.IO).launch {
            val response =
                mRepository.getOfferList(object : OfferRepository.GetOfferListApiCallback {
                    override fun onNetworkFailure(th: String) {
                        mShowApiError.value = th
                        mShowProgressBar.value = false
                    }

                    override fun onNetworkSuccess(response: GetClassTestStudentResponse) {
                        mShowProgressBar.value = false
                        offerData.value = response
                    }
                }, request,page)
        }
    }

    override val coroutineContext: CoroutineContext
        get() = EmptyCoroutineContext

    /*if (NetworkHelper.isOnline(app.baseContext)) {
        mShowProgressBar.value = true
        mRepository.getOfferList(object : OfferRepository.GetOfferListApiCallback {
            override fun onNetworkFailure(th: String) {
                mShowApiError.value = th
                mShowProgressBar.value = false
            }

            override fun onNetworkSuccess(response: GetClassTestStudentResponse) {
                mShowProgressBar.value = false
                offerData.value = response
            }
        }, page)
    } else {
        mShowNetworkError.value = true
        mShowProgressBar.value = false
    }*/
}
