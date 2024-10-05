package com.example.cards.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cards.GameViewModel

@Composable
fun GameScreen(viewModel: GameViewModel) {
    val gameState by viewModel.gameState.collectAsState()

    gameState?.let { state ->
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                fontSize = 30.sp,
                text = "Ход: ${state.currentPlayer.name}"
            )
            Text(
                fontSize = 25.sp,
                text = "Мана: ${state.currentPlayer.mana}"
            )

            // Отображение карт на руке игрока
            LazyRow(modifier = Modifier.padding(8.dp)) {
                items(state.currentPlayer.hand) { card ->
                    CardView(card = card) {
                        // Логика для размещения карты
                        // Например, откроем диалог для выбора клетки
                        viewModel.placeCardOnBoard(card, x, y) // Здесь x и y должны быть заданы в зависимости от выбора клетки
                    }
                }
            }

            GameBoard(gameState = state) { card ->
                // Логика атаки или передвижения карты
                viewModel.attackCard(attackerCard = card, targetCard = /* карта, на которую будет атаковано */)
            }

            Button(
                onClick = { viewModel.endTurn() },
                modifier = Modifier
                    .padding(16.dp)
                    .size(125.dp)
            ) {
                Text(
                    text = "Закончить ход",
                    fontSize = 15.sp,
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}


