package com.example.quizgame.data

data class Question(
    val question: String,
    val choice: List<String>,
    val answer: String
)