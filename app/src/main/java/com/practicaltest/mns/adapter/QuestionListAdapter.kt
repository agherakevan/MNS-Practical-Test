package com.practicaltest.mns.adapter

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.practicaltest.mns.R
import com.practicaltest.mns.api.response.GetClassTestStudentResponse


class QuestionListAdapter(
    ctx: Activity,
    listBannerImage: List<GetClassTestStudentResponse.DataX>
) : PagerAdapter() {
    private val inflater: LayoutInflater
    private val listBannerImage: List<GetClassTestStudentResponse.DataX>
    private val con: Context

    override fun getCount(): Int {
        return listBannerImage.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view: View = inflater.inflate(R.layout.item_question_data, container, false)
        val tvQuestion = view.findViewById<TextView>(R.id.tvQuestion)

        val rbOption1 = view.findViewById<RadioButton>(R.id.rbOption1)
        val rbOption2 = view.findViewById<RadioButton>(R.id.rbOption2)
        val rbOption3 = view.findViewById<RadioButton>(R.id.rbOption3)
        val rbOption4 = view.findViewById<RadioButton>(R.id.rbOption4)


        val data: GetClassTestStudentResponse.DataX = listBannerImage[position]

        tvQuestion.text = data.description
        rbOption1.text = data.questionAnswers[0].questionOption
        rbOption2.text = data.questionAnswers[1].questionOption
        rbOption3.text = data.questionAnswers[2].questionOption
        rbOption4.text = data.questionAnswers[3].questionOption

        container.addView(view, 0)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    init {
        con = ctx
        this.listBannerImage = listBannerImage
        inflater = LayoutInflater.from(con)
    }

}