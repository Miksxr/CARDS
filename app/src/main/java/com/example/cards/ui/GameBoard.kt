package com.example.cards.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.example.cards.dataclass.Card
import com.example.cards.dataclass.Cell
import com.example.cards.dataclass.GameState

@Composable
fun GameBoard(
    gameState: GameState,
    selectedCard: Card?,
    onCardClick: (Cell, Card) -> Unit,
    onDropCard: (Card, Int, Int) -> Unit,
    onMoveCard: (Card, Int, Int) -> Unit
) {
    Column {
        gameState.board.forEachIndexed { x, row ->
            Row {
                row.forEachIndexed { y, cell ->
                    Box(
                        modifier = Modifier
                            .padding(2.dp)
                            .size(60.dp)
                            .background(
                                when (cell.ownerId) {
                                    1 -> Color.Green  // Если карта принадлежит первому игроку
                                    2 -> Color.Red    // Если карта принадлежит второму игроку
                                    else -> Color.LightGray // Если клетка пустая
                                }
                            )
                            .clickable {
                                val card = cell.card // Явно присваиваем значение переменной

                                // Обрабатываем клик по карте
                                if (card != null) {
                                    Log.d("GameScreen", "Moving Card: ${card.name} from cell (${cell.x}, ${cell.y})")
                                    onCardClick(cell, card)
                                } else if (selectedCard != null) { // Если карта выбрана и ячейка пуста
                                    Log.d("GameScreen", "Dropping Card: ${selectedCard.name} to cell (${cell.x}, ${cell.y})")
                                    onDropCard(selectedCard, x, y)
                                }
                            }
                    ) {
                        cell.card?.let { card ->
                            // Отображение карты на доске
                            CardView(card = card, onClick = {
                                Log.d("GameScreen", "Card clicked: ${card.name}") // Логируем клик по карте
                                onCardClick(cell, card)
                            }, isOnBoard = true)
                        }
                    }
                }
            }
        }
    }
}





