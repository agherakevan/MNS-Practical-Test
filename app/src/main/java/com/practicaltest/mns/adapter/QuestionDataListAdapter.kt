package com.practicaltest.mns.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.practicaltest.mns.api.response.GetClassTestStudentResponse
import com.practicaltest.mns.databinding.ItemQuestionDataBinding

/**
 * Created by kevan on 26/11/2020.
 */

class QuestionDataListAdapter(
    private val context: Context,
    private var listContactUs:
    MutableList<GetClassTestStudentResponse.DataX>,
    private val onClickListener: OnItemClickListener,
) :
    RecyclerView.Adapter<QuestionDataListAdapter.MyViewHolder>() {

    interface OnItemClickListener {
        fun onItemCLick(item: GetClassTestStudentResponse.DataX)
    }

    class MyViewHolder internal constructor(v: ItemQuestionDataBinding) :
        RecyclerView.ViewHolder(v.root.rootView) {
        val view: ItemQuestionDataBinding = v

        internal fun bind(
            item: GetClassTestStudentResponse.DataX,
            listener: OnItemClickListener
        ) {
            listener.onItemCLick(item)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding: ItemQuestionDataBinding = ItemQuestionDataBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, listPosition: Int) {

        val contactData: GetClassTestStudentResponse.DataX = listContactUs[listPosition]

        holder.view.tvQuestion.text = contactData.description
        when (contactData.questionAnswers.size) {
            1 -> {
                holder.view.rbOption1.text = contactData.questionAnswers[0].questionOption
            }
            2 -> {
                holder.view.rbOption1.text = contactData.questionAnswers[0].questionOption
                holder.view.rbOption2.text = contactData.questionAnswers[1].questionOption
            }
            3 -> {
                holder.view.rbOption1.text = contactData.questionAnswers[0].questionOption
                holder.view.rbOption2.text = contactData.questionAnswers[1].questionOption
                holder.view.rbOption3.text = contactData.questionAnswers[2].questionOption
            }
            4 -> {
                holder.view.rbOption1.text = contactData.questionAnswers[0].questionOption
                holder.view.rbOption2.text = contactData.questionAnswers[1].questionOption
                holder.view.rbOption3.text = contactData.questionAnswers[2].questionOption
                holder.view.rbOption4.text = contactData.questionAnswers[3].questionOption
            }
        }

        if (contactData.status == "Correct") {
            holder.view.tvAnswerStatus.setTextColor(Color.GREEN)
        } else {
            holder.view.tvAnswerStatus.setTextColor(Color.RED)
        }

        holder.view.tvAnswerStatus.text = contactData.status

        holder.itemView.setOnClickListener {
            holder.bind(listContactUs[listPosition], onClickListener)
        }
    }

    override fun getItemCount(): Int {
        return listContactUs.size
    }

    fun add(items: MutableList<GetClassTestStudentResponse.DataX>) {
        listContactUs.addAll(items)
        notifyDataSetChanged()
    }

    fun notifyData(items: MutableList<GetClassTestStudentResponse.DataX>) {
        listContactUs.clear()
        listContactUs.addAll(items)
        notifyDataSetChanged()
    }

}