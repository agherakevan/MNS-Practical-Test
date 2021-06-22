package com.practicaltest.mns.repo


import com.practicaltest.mns.api.Config
import com.practicaltest.mns.api.RestClient
import com.practicaltest.mns.api.request.ClassTestStudentRequest
import com.practicaltest.mns.api.response.GetClassTestStudentResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OfferRepository private constructor() {

    private lateinit var mCallback: GetOfferListApiCallback

    companion object {
        private var mInstance: OfferRepository? = null
        fun getInstance(): OfferRepository {
            if (mInstance == null) {
                synchronized(this) {
                    mInstance = OfferRepository()
                }
            }
            return mInstance!!
        }
    }

    private lateinit var mMyBookingCall: Call<GetClassTestStudentResponse>

    fun getOfferList(
        callback: GetOfferListApiCallback,
        request: ClassTestStudentRequest,
        page: Int
    ) {
        mCallback = callback
        mMyBookingCall = RestClient.getInstance().getApiService().getClassData(
            request,Config.token
        )
        mMyBookingCall.enqueue(object : Callback<GetClassTestStudentResponse> {
            override fun onResponse(
                call: Call<GetClassTestStudentResponse>,
                response: Response<GetClassTestStudentResponse>
            ) {
                if (response.body()!!.status == 1) {
                    mCallback.onNetworkSuccess(response.body()!!)
                } else {
                    mCallback.onNetworkFailure(response.body()!!.message)
                }
            }

            override fun onFailure(call: Call<GetClassTestStudentResponse>, t: Throwable) {
                mCallback.onNetworkFailure(t.message.toString())
            }
        })
    }

    interface GetOfferListApiCallback {
        fun onNetworkSuccess(response: GetClassTestStudentResponse)
        fun onNetworkFailure(th: String)
    }
}