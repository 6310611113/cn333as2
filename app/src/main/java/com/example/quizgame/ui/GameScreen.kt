package com.example.quizgame.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Share
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quizgame.data.Question
import com.google.rpc.Help


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun QuizScreen(gameViewModel: GameViewModel, playMore: () -> Unit) {

    val uiState by gameViewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Quiz Game") }
            )
        },
        content = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                ,

                ) {
                when (uiState.quizRound) {
                    10 -> {
                        SummaryScreen(
                            score = uiState.score,
                            playMore = playMore
                        )
                    }
                    else -> {
                        QuestionScreen(
                            question = uiState.currentQuestion,
                            choices = uiState.options,
                            score = uiState.score,
                            quizNum = uiState.quizRound+1,
                            answer = uiState.currentQuestion.answer,
                            SelectedAnswer = gameViewModel::answerQuestion
                        )
                    }
                }
            }
        }
    )
}
@Composable
fun QuestionScreen(
    question: Question,
    choices: List<String>,
    score: Int,
    quizNum: Int,
    answer: String,
    SelectedAnswer: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxSize()
    ) {
        Box(

            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Row(
                modifier = Modifier.align(Alignment.CenterStart),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "$quizNum/10",
                    color = MaterialTheme.colors.primary,
                    style = MaterialTheme.typography.h6
                )
                Text(
                    text = "Score: $score",
                    color = MaterialTheme.colors.primary,
                    style = MaterialTheme.typography.h6
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = question.question,
                color = MaterialTheme.colors.onBackground,
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
            choices.forEach { choice ->
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    shape = RoundedCornerShape(24.dp),
                    onClick = { SelectedAnswer(choice) },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray)
                ) {
                    Text(
                        text = choice,
                        fontSize = 18.sp
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}


@Composable

fun SummaryScreen(score: Int,
                  playMore: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Congratulations!!! Your score is $score",
            fontSize = 20.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            shape = RoundedCornerShape(30.dp),
            onClick = playMore,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Play Again",
                fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(8.dp))

    }
}