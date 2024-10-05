package com.example.cards.ui

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
    var showPlacementDialog by remember { mutableStateOf(false) }
    var selectedCard by remember { mutableStateOf<Card?>(null) }
    var selectedCell by remember { mutableStateOf<Pair<Int, Int>?>(null) }

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

            LazyRow(modifier = Modifier.padding(8.dp)) {
                items(state.currentPlayer.hand) { card ->
                    CardView(card = card) {
                        selectedCard = card
                        showPlacementDialog = true
                    }
                }
            }

            GameBoard(gameState = state) { cell, card ->
                if (selectedCard != null && selectedCard?.moveSpeed != 0) {
                    viewModel.attackCard(attackerCard = selectedCard!!, targetCard = card)
                    selectedCard = null
                } else {
                    selectedCard = card
                    selectedCell = Pair(cell.x, cell.y)
                    showPlacementDialog = true
                }
            }

            if (showPlacementDialog && selectedCard != null) {
                PlacementDialog(
                    onDismiss = { showPlacementDialog = false },
                    onConfirm = { x, y ->
                        viewModel.placeCardOnBoard(selectedCard!!, x, y)
                        selectedCard = null
                        selectedCell = null
                        showPlacementDialog = false
                    },
                    selectedCell = selectedCell
                )
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
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun PlacementDialog(
    onDismiss: () -> Unit,
    onConfirm: (Int, Int) -> Unit,
    selectedCell: Pair<Int, Int>?
) {
    var x by remember { mutableStateOf(selectedCell?.first ?: 0) }
    var y by remember { mutableStateOf(selectedCell?.second ?: 0) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Выберите клетку") },
        text = {
            Column {
                Row {
                    Text("X:")
                    TextField(value = x.toString(), onValueChange = { x = it.toIntOrNull() ?: 0 })
                }
                Row {
                    Text("Y:")
                    TextField(value = y.toString(), onValueChange = { y = it.toIntOrNull() ?: 0 })}
            }
        },
        confirmButton = {
            Button(onClick = { onConfirm(x, y) }) {
                Text("Подтвердить")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Отмена")
            }
        }
    )
}
