
package com.example.quizgame.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quizgame.data.Question


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
                modifier = Modifier.fillMaxSize().padding(16.dp),

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
                            quizNum = uiState.quizRound,
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
    SelectedAnswer: (String) -> Unit)
{
    Column(
        modifier = Modifier.padding(16.dp)
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),

        ) {

        Spacer(modifier = Modifier.height(50.dp))
        Row(
            modifier = Modifier
                .background(color = Color.LightGray)
                .fillMaxWidth()
                .padding(all = 20.dp),

            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(text = question.question,
                fontSize = 20.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        choices.forEach { choice ->
            Button(
                shape = RoundedCornerShape(30.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 5.dp),


                onClick = { SelectedAnswer(choice) },

                ) {
                Text(text = choice,
                    fontSize = 18.sp)
                }
        }
        Spacer(modifier = Modifier.height(50.dp))

        Row(
            modifier = Modifier
                .background(color = Color.LightGray)
                .fillMaxSize()
                .padding(all = 10.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            Text(text = "ROUND: $quizNum/10",
                fontSize = 18.sp)

            Text(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentWidth(Alignment.End),
                text = "Your score: $score",
                fontSize = 18.sp)
        }


    }
}

@Composable
fun SummaryScreen(score: Int, playMore: () -> Unit) {
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