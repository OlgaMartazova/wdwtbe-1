package com.pskda.wdwtbe.model

object QuestionRepository {

    val questions = hashMapOf(
        1 to listOf(
            Question("name1", false, arrayOf("ans1", "ans2", "ans3", "ans4"), 0),
            Question("name2", false, arrayOf("ans1", "ans2", "ans3", "ans4"), 1),
            Question("name3", false, arrayOf("ans1", "ans2", "ans3", "ans4"), 1)
        ),
        2 to listOf(
            Question("name4", true, arrayOf("ans1", "ans2", "ans3", "ans4"), 2),
            Question("name5", false, arrayOf("ans1", "ans2", "ans3", "ans4"), 3),
            Question("name6", false, arrayOf("ans1", "ans2", "ans3", "ans4"), 3)
        )
    )
}