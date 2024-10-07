package com.example.cards.ui

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cards.GameViewModel
import com.example.cards.dataclass.Card

@Composable
fun GameScreen(viewModel: GameViewModel) {
    val gameState by viewModel.gameState.collectAsState()
    var selectedCard by remember { mutableStateOf<Card?>(null) }

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

            // Карты в руке игрока
            LazyRow(modifier = Modifier.padding(8.dp)) {
                items(state.currentPlayer.hand) { card ->
                    // Карты в руке (не на доске)
                    CardView(card = card, onClick = {
                        selectedCard = card // Выбор карты
                        Log.d("GameScreen", "Selected Card: $selectedCard")
                    }, isOnBoard = false) // Передаем параметр isOnBoard = false
                }
            }

            // Игровое поле
            GameBoard(
                gameState = state,
                selectedCard = selectedCard,
                onCardClick = { cell, card ->
                    // Обработка клика по карте
                },
                onDropCard = { card, x, y ->
                    if (selectedCard != null && selectedCard == card) {
                        val isWithinPlayerArea = (state.currentPlayer.id == 1 && x >= 3) || (state.currentPlayer.id == 2 && x < 2)
                        if (isWithinPlayerArea) {
                            viewModel.placeCardOnBoard(selectedCard!!, x, y, state.currentPlayer.id)
                            Log.d("GameScreen", "Placed Card: $selectedCard at x=$x, y=$y")
                            selectedCard = null
                        }
                    }
                },
                onMoveCard = { card, x, y ->
                    val currentCell = state.board.flatten().firstOrNull { it.card == card }
                    if (currentCell != null) {
                        val moveSpeed = card.moveSpeed
                        val currentX = currentCell.x
                        val currentY = currentCell.y

                        // Проверяем, что перемещение находится в пределах moveSpeed
                        if (kotlin.math.abs(currentX - x) + kotlin.math.abs(currentY - y) <= moveSpeed) {
                            viewModel.moveCard(card, x, y)
                            Log.d("GameScreen", "Moved Card: ${card.name} to ($x, $y)")
                        } else {
                            Log.d("GameScreen", "Invalid move for card: ${card.name}. Move speed: $moveSpeed")
                        }
                    } else {
                        Log.d("GameScreen", "Card not found on board.")
                    }
                }
            )

            Button(
                onClick = { viewModel.endTurn() },
                modifier = Modifier
                    .padding(16.dp)
                    .size(125.dp)
            ) {
                Text(
                    text = "Закончить ход",
                    fontSize = 15.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}



