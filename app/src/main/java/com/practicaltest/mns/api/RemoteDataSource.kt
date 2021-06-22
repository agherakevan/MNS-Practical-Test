package com.practicaltest.mns.api

import androidx.lifecycle.MutableLiveData
import com.practicaltest.mns.api.request.ClassTestStudentRequest
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val characterService: ApiService
) : BaseDataSource() {

    suspend fun getClassTestStudent(request: ClassTestStudentRequest) =
        getResult { characterService.getClassTestStudent(request, Config.token) }

}