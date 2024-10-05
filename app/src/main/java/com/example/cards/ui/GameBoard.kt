package com.example.cards.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.cards.dataclass.Card
import com.example.cards.dataclass.Cell
import com.example.cards.dataclass.GameState

@Composable
fun GameBoard(gameState: GameState, onCardClick: (Cell, Card) -> Unit) {
    Column {
        gameState.board.forEach { row ->
            Row {
                row.forEach { cell ->
                    Box(
                        modifier = Modifier
                            .padding(2.dp)
                            .size(60.dp)
                            .background(if (cell.card == null) Color.LightGray else Color.Green)
                            .clickable {
                                cell.card?.let { card ->
                                    onCardClick(cell, card)
                                }
                            }
                    ) {
                        cell.card?.let { card ->
                            CardView(card = card, onClick = { onCardClick(cell, card) })
                        }
                    }
                }
            }
        }
    }
}


