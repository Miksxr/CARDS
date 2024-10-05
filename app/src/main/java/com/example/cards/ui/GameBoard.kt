package com.example.cards.ui

import androidx.compose.foundation.background
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
import com.example.cards.dataclass.GameState

@Composable
fun GameBoard(gameState: GameState, onCardClick: (Card) -> Unit) {
    Column {
        gameState.board.forEach { row ->
            Row {
                row.forEach { cell ->
                    Box(
                        modifier = Modifier
                            .padding(2.dp)
                            .size(70.dp)
                            .background(if (cell.card == null) Color.LightGray else Color.Green)
                    ) {
                        cell.card?.let { card ->
                            CardView(card = card, onClick = { onCardClick(card) })
                        }
                    }
                }
            }
        }
    }
}


