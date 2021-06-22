package com.practicaltest.mns.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.paginate.Paginate
import com.practicaltest.mns.R
import com.practicaltest.mns.adapter.QuestionDataListAdapter
import com.practicaltest.mns.api.request.ClassTestStudentRequest
import com.practicaltest.mns.api.response.GetClassTestStudentResponse
import com.practicaltest.mns.databinding.ActivityMainBinding
import com.practicaltest.mns.util.NetworkHelper
import com.practicaltest.mns.viewmodel.ViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), Paginate.Callbacks {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: QuestionDataListAdapter
    private lateinit var offerViewModel: ViewModel
    private var page = 0
    private var totalPages: Int = 0
    private var paginate: Paginate? = null
    private var loading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this

        setupRecyclerView()
        setupViewModel()
        setupPagination()

        binding.btnPrevious.setOnClickListener {
            binding.rvQuestionList.scrollToPosition((this.binding.rvQuestionList.layoutManager as LinearLayoutManager?)!!.findFirstVisibleItemPosition() - 1)
        }
        binding.btnNext.setOnClickListener {
            binding.rvQuestionList.scrollToPosition((this.binding.rvQuestionList.layoutManager as LinearLayoutManager?)!!.findFirstVisibleItemPosition() + 1)
        }

    }

    private fun setupRecyclerView() {
        adapter = QuestionDataListAdapter(
            this,
            mutableListOf(),
            object : QuestionDataListAdapter.OnItemClickListener {
                override fun onItemCLick(item: GetClassTestStudentResponse.DataX) {

                }
            })
        binding.rvQuestionList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvQuestionList.adapter = adapter

    }

    private fun setupViewModel() {

        offerViewModel = ViewModelProvider(this).get(ViewModel::class.java)
        binding.viewModel = offerViewModel

        offerViewModel.offerData.observe(this, myBookingDataObserver)
        offerViewModel.mShowNetworkError.observe(this, mShowNetworkError)
        offerViewModel.mShowProgressBar.observe(this, mShowProgressBar)
        val request = ClassTestStudentRequest(
            219,
            page
        )

        offerViewModel.getOfferList(
            page,
            request
        )

    }

    private val myBookingDataObserver = Observer<GetClassTestStudentResponse> {
        if (it != null && it.status == 1) {

            totalPages = it.data.questions.total

            if (it.data.questions.data.isNotEmpty()) {

                adapter.add(it.data.questions.data)

            } else {
                Toast.makeText(
                    this,
                    it.message,
                    Toast.LENGTH_SHORT
                ).show()
            }
            loading = false
        }
    }

    private val mShowProgressBar = Observer<Boolean> { bt ->
        if (bt) {
            //binding.pbLoading.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private val mShowNetworkError = Observer<Boolean> {
        AlertDialog.Builder(this).setMessage("No Internet Connection").show()
    }

    override fun onLoadMore() {

        loading = true

        if (NetworkHelper.isOnline(this)) {
            val request = ClassTestStudentRequest(
                219,
                page
            )

            page++
            offerViewModel.getOfferList(
                page,
                request
            )
        } else {
            loading = false
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show()
        }

    }

    override fun isLoading(): Boolean {
        return loading
    }

    override fun hasLoadedAllItems(): Boolean {
        return page == totalPages
    }

    private fun setupPagination() {
        // If RecyclerView was recently bound, unbind
        if (paginate != null) {
            paginate!!.unbind()
        }
        loading = false
        page = 0
        paginate = Paginate.with(binding.rvQuestionList, this)
            .setLoadingTriggerThreshold(4)
            .addLoadingListItem(true)
            .build()
    }
}