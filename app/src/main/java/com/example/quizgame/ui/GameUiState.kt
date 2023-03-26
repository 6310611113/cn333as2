package com.example.quizgame.ui

import com.example.quizgame.data.Question

data class gameState (
    val currentQuestion: Question,
    val options: List<String>,
    val score: Int,
    val quizRound: Int
        )