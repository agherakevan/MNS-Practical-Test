package com.practicaltest.mns.api

import com.practicaltest.mns.api.request.ClassTestStudentRequest
import com.practicaltest.mns.api.response.GetClassTestStudentResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {

    @POST("get-class-test-student-question-paper-assessment")
    suspend fun getClassTestStudent(
        @Body request: ClassTestStudentRequest,
        @Header("Authorization") authHeader: String
    ): Response<GetClassTestStudentResponse>
}