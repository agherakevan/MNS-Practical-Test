package com.practicaltest.mns.api

import com.practicaltest.mns.api.request.ClassTestStudentRequest
import com.practicaltest.mns.api.response.GetClassTestStudentResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

/**
 * Created by kevan on 26/11/2020.
 */

interface ApiInterface {

    @POST("get-class-test-student-question-paper-assessment")
    fun getClassData(
        @Body request: ClassTestStudentRequest,
        @Header("Authorization") authHeader: String
    ): Call<GetClassTestStudentResponse>


}