package com.example.cards.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cards.R
import com.example.cards.dataclass.Card
import com.example.cards.dataclass.Player

@Composable
fun CardView(card: Card, onClick: () -> Unit, isOnBoard: Boolean) {
    // Если карта на поле, уменьшаем отступы и размеры элементов
    val paddingValue = if (isOnBoard) 1.dp else 2.dp
    val imageSize = if (isOnBoard) 35.dp else 50.dp
    val textSize = if (isOnBoard) 8.sp else 10.sp
    val iconSize = if (isOnBoard) 10.dp else 12.dp

    Box(
        modifier = Modifier
            .padding(paddingValue)
            .size(if (isOnBoard) 60.dp else 80.dp) // Изменяем размер карты на поле
            .clickable { onClick() }
            .background(Color.Gray),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxSize()
        ) {
            // Верхняя строка: Имя карты слева и стоимость справа
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(paddingValue),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Имя карты слева
                Text(text = card.name, fontSize = textSize, modifier = Modifier.align(Alignment.CenterVertically))

                // Стоимость карты справа
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_cost),
                        contentDescription = "Cost Icon",
                        modifier = Modifier.size(iconSize)
                    )
                    Text(text = "${card.cost}", fontSize = textSize, modifier = Modifier.padding(start = 2.dp))
                }
            }

            // Изображение карты в центре
            Image(
                painter = painterResource(id = card.imageResId),
                contentDescription = null,
                modifier = Modifier
                    .size(imageSize)
                    .padding(2.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(Color.LightGray),
                contentScale = ContentScale.Crop
            )

            // Параметры здоровья, атаки и скорости снизу
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(paddingValue)
            ) {
                // Иконка и текст здоровья слева снизу
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_health),
                        contentDescription = "Health Icon",
                        modifier = Modifier.size(iconSize)
                    )
                    Text(text = "${card.health}", fontSize = textSize, modifier = Modifier.padding(start = 2.dp))
                }

                // Скорость передвижения по центру снизу
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_movespeed),
                        contentDescription = "MoveSpeed Icon",
                        modifier = Modifier.size(iconSize)
                    )
                    Text(text = "${card.moveSpeed}", fontSize = textSize, modifier = Modifier.padding(start = 2.dp))
                }

                // Иконка и текст атаки справа снизу
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_attack),
                        contentDescription = "Attack Icon",
                        modifier = Modifier.size(iconSize)
                    )
                    Text(text = "${card.attack}", fontSize = textSize, modifier = Modifier.padding(start = 2.dp))
                }
            }
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
        imageResId = R.drawable.card_igor // Или заменить на placeholder
    )

    // Передаем фейковую карту в CardView
    CardView(card = fakeCard, onClick = {}, isOnBoard = false) // Предпросмотр карты в руке (не на доске)
}


