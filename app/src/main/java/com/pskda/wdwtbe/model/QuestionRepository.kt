package com.pskda.wdwtbe.model

class QuestionRepository {

    val questions = hashMapOf(
        1 to arrayListOf(
            Question("name1", true, arrayOf("ans1", "ans2", "ans3", "ans4"), 0),
            Question("name2", true, arrayOf("ans1", "ans2", "ans3", "ans4"), 1),
            Question("name3", true, arrayOf("ans1", "ans2", "ans3", "ans4"), 1)
        ),
        2 to arrayListOf(),
        3 to arrayListOf()
    )
}