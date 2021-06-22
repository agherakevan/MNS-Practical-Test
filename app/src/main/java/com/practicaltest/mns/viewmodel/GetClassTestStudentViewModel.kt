package com.practicaltest.mns.viewmodel

import androidx.lifecycle.ViewModel
import com.practicaltest.mns.api.request.ClassTestStudentRequest
import com.practicaltest.mns.repo.GetStudentTestRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GetClassTestStudentViewModel @Inject constructor(
    repository: GetStudentTestRepository,
) : ViewModel() {

    private var page : Int =0

    fun setRequest(page: Int) {
        this.page = page
    }

    private var request = ClassTestStudentRequest(219, page)

    val classTestStudent = repository.getAvailableRouteApiData(request)

}
