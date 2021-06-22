package com.practicaltest.mns.api.request

import com.google.gson.annotations.SerializedName

data class ClassTestStudentRequest(
    @SerializedName("student_online_exam_result_id")
    val student_online_exam_result_id: Int,
    @SerializedName("page")
    val page: Int,
)