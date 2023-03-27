package com.example.quizgame.ui

import androidx.lifecycle.ViewModel
import com.example.quizgame.data.Question
import com.example.quizgame.data.QuestionData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.system.exitProcess


class GameViewModel : ViewModel() {

    private val quizData = QuestionData()
    private var questions = quizData.questionList.shuffled()
    private var quizRound = 0
    private var score = 0

    private val _uiState = MutableStateFlow(initialUiState())

    val uiState: StateFlow<gameState> = _uiState.asStateFlow()

    fun answerQuestion(answer: String){
        if (answer == questions[quizRound].answer) {
            score ++
        }
        quizRound ++
        if (quizRound < 10) {

            _uiState.value = gameState(
                currentQuestion = questions[quizRound],
                options = questions[quizRound].choice.shuffled(),
                score = score,
                quizRound = quizRound
            )
        }else {
            _uiState.value = gameState(
                currentQuestion = Question("", listOf(), ""),
                options = listOf(),
                score = score,
                quizRound = quizRound
            )
        }
    }

    fun resetQuestion() {
        questions = quizData.questionList.shuffled()
        quizRound = 0
        score = 0

        _uiState.value = gameState(
            currentQuestion = questions[quizRound],
            options = questions[quizRound].choice.shuffled(),
            score = score,
            quizRound = quizRound
        )
    }
    private fun initialUiState(): gameState {
       return gameState(
            currentQuestion = questions[quizRound],
            options = questions[quizRound].choice.shuffled(),
            score = score,
            quizRound = quizRound
        )
    }

}