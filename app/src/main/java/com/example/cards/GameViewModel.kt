package com.example.cards

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cards.dataclass.Card
import com.example.cards.dataclass.Cell
import com.example.cards.dataclass.GameState
import com.example.cards.dataclass.Player
import com.example.cards.dependencies.GameRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val repository: GameRepository
) : ViewModel() {

    private val _gameState = MutableStateFlow<GameState?>(null)
    val gameState: StateFlow<GameState?> = _gameState.asStateFlow()

    init {
        viewModelScope.launch {
            val allCards = repository.loadCards()
            Log.d("LOG", allCards.toString())
            val player1Deck = allCards.shuffled().take(10).toMutableList() // Колода игрока 1
            val player2Deck = allCards.shuffled().take(10).toMutableList() // Колода игрока 2

            val player1 = Player(id = 1, name = "Player 1", deck = player1Deck)
            val player2 = Player(id = 2, name = "Player 2", deck = player2Deck)

            // Раздача по 5 случайных карт каждому игроку
            player1.hand.addAll(player1.deck.shuffled().take(5))
            player2.hand.addAll(player2.deck.shuffled().take(5))

            val board = List(5) { x -> List(5) { y -> Cell(x, y) } } // 5x5 поле

            _gameState.value = GameState(
                currentPlayer = player1,
                opponentPlayer = player2,
                board = board
            )
        }
    }


    // Логика выложить карту на доску
    fun placeCardOnBoard(card: Card, x: Int, y: Int) {
        val currentState = _gameState.value ?: return
        val currentPlayer = currentState.currentPlayer

        if (currentPlayer.mana >= card.cost && currentState.board[x][y].card == null) {
            currentState.board[x][y].card = card
            currentPlayer.mana -= card.cost
            currentPlayer.hand.remove(card)

            // После размещения карты добавляем новую карту
            val newCard = currentPlayer.deck.shuffled().firstOrNull() // Берем новую карту из колоды
            if (newCard != null) {
                currentPlayer.hand.add(newCard) // Добавляем новую карту в руку
            }
        }

        _gameState.value = currentState
    }


    // Передача хода и добавление маны
    fun endTurn() {
        val currentState = _gameState.value ?: return

        // Переход хода к следующему игроку
        _gameState.value = currentState.copy(
            currentPlayer = currentState.opponentPlayer,
            opponentPlayer = currentState.currentPlayer,
            turnNumber = currentState.turnNumber + 1 // Увеличиваем номер хода
        )

        // Проверяем, завершился ли раунд (два хода)
        if (currentState.turnNumber % 2 == 0) {
            // Увеличение маны на количество раундов
            val roundsCompleted = currentState.turnNumber / 2
            currentState.currentPlayer.mana += roundsCompleted
            currentState.opponentPlayer.mana += roundsCompleted
        }
    }

    // Атака карты на другую карту
    fun attackCard(attackerCard: Card, targetCard: Card) {
        val currentState = _gameState.value ?: return

        // Проверка на соседние клетки
        val attackerCell = currentState.board.flatten().firstOrNull { it.card == attackerCard }
        val targetCell = currentState.board.flatten().firstOrNull { it.card == targetCard }

        if (attackerCell != null && targetCell != null &&
            areCellsAdjacent(attackerCell, targetCell)
        ) {
            // Уменьшение здоровья у цели
            targetCard.health -= attackerCard.attack

            // Если здоровье цели <= 0, удаляем её с поля
            if (targetCard.health <= 0) {
                currentState.board.flatten().firstOrNull { it.card == targetCard }?.card = null
            }

            _gameState.value = currentState
        }
    }

    // Проверка, являются ли клетки соседними
    private fun areCellsAdjacent(cell1: Cell, cell2: Cell): Boolean {
        val dx = kotlin.math.abs(cell1.x - cell2.x)
        val dy =kotlin.math.abs(cell1.y - cell2.y)
        return (dx == 1 && dy == 0) || (dx == 0 && dy == 1)
    }


    // Проверка победителя
    fun checkVictory(): Player? {
        val currentState = _gameState.value ?: return null
        return when {
            currentState.currentPlayer.health <= 0 -> currentState.opponentPlayer
            currentState.opponentPlayer.health <= 0 -> currentState.currentPlayer
            else -> null
        }
    }
}

