package com.example.cards.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cards.R
import com.example.cards.dataclass.Card
import com.example.cards.dataclass.Player

@Composable
fun CardView(card: Card, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .padding(8.dp)
            .size(100.dp)
            .clickable { onClick() }
            .background(Color.Gray),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = card.name)
            Text(text = "HP: ${card.health}")
            Text(text = "Atk: ${card.attack}")
            Text(text = "Cost: ${card.cost}")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CardViewPreview() {
    // Создаем фейковую карту для превью
    val fakeCard = Card(
        name = "Igor",
        attack = 8,
        health = 4,
        moveSpeed = 2,
        cost = 5,
        imageResId = R.drawable.card_igor // Или заменить на placeholder, если превью изображения не требуется
    )

    // Передаем фейковую карту в CardView
    CardView(card = fakeCard, onClick = {})
}

