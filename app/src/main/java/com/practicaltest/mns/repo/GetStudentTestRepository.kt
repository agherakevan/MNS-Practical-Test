package com.practicaltest.mns.repo

import com.practicaltest.mns.api.ApiService
import com.practicaltest.mns.api.RemoteDataSource
import com.practicaltest.mns.api.request.ClassTestStudentRequest
import com.practicaltest.mns.util.performGetOperationWithoutDbStore
import javax.inject.Inject

class GetStudentTestRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val apiService: ApiService
) {

    fun create(): GetStudentTestRepository = GetStudentTestRepository(remoteDataSource, apiService)

    /*fun getRoute(request: MutableLiveData<GetAvailableRoutesSTaxRequest>): Flow<GetAvailableRoutesSTaxResponse> =
        flow {

            val response = apiService.getAvailableRoutesSTax(request)
            emit(response)
        }.flowOn(Dispatchers.IO)*/

    fun getAvailableRouteApiData(request: ClassTestStudentRequest) =
        performGetOperationWithoutDbStore(
            networkCall = { remoteDataSource.getClassTestStudent(request) },
        )
}