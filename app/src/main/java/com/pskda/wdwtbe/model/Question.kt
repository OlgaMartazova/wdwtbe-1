package com.pskda.wdwtbe.model

data class Question(
    val name: String,
    val isExtra: Boolean,
    val answers: Array<String>,
    val indexOfRightAnswer: Int
)
