package com.practicaltest.mns.api.response


import com.google.gson.annotations.SerializedName

data class GetClassTestStudentResponse(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Int
) {
    data class Data(
        @SerializedName("questions")
        val questions: Questions,
        @SerializedName("subject_image_url")
        val subjectImageUrl: String,
        @SerializedName("subject_name")
        val subjectName: String
    )

    data class QuestionAnswer(
        @SerializedName("id")
        val id: Int,
        @SerializedName("is_correct")
        val isCorrect: Int,
        @SerializedName("question_id")
        val questionId: Int,
        @SerializedName("question_option")
        val questionOption: String
    )

    data class DataX(
        @SerializedName("comprehensive_description")
        val comprehensiveDescription: String,
        @SerializedName("comprehensive_label_text")
        val comprehensiveLabelText: String,
        @SerializedName("description")
        val description: String,
        @SerializedName("id")
        val id: Int,
        @SerializedName("question_answers")
        val questionAnswers: List<QuestionAnswer>,
        @SerializedName("question_level")
        val questionLevel: Int,
        @SerializedName("question_mark")
        val questionMark: String,
        @SerializedName("question_type")
        val questionType: Int,
        @SerializedName("status")
        val status: String,
        @SerializedName("status_id")
        val statusId: Int,
        @SerializedName("student_answer")
        val studentAnswer: String,
        @SerializedName("student_answer_documents")
        val studentAnswerDocuments: List<Any>,
        @SerializedName("student_mark")
        val studentMark: String
    )

    data class Questions(
        @SerializedName("current_page")
        val currentPage: Int,
        @SerializedName("data")
        val `data`: MutableList<DataX>,
        @SerializedName("first_page_url")
        val firstPageUrl: String,
        @SerializedName("from")
        val from: Int,
        @SerializedName("last_page")
        val lastPage: Int,
        @SerializedName("last_page_url")
        val lastPageUrl: String,
        @SerializedName("next_page_url")
        val nextPageUrl: String,
        @SerializedName("path")
        val path: String,
        @SerializedName("per_page")
        val perPage: Int,
        @SerializedName("prev_page_url")
        val prevPageUrl: Any,
        @SerializedName("to")
        val to: Int,
        @SerializedName("total")
        val total: Int
    )
}