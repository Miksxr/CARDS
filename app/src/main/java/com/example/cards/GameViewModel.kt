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

class GameViewModel : ViewModel() {
    private val repository = GameRepository() // Создаем экземпляр репозитория

    private val _gameState = MutableStateFlow<GameState?>(null)
    val gameState: StateFlow<GameState?> = _gameState.asStateFlow()

    init {
        viewModelScope.launch {
            val allCards = repository.loadCards()

            val player1Deck = allCards.shuffled().take(10).map { it.copy(ownerId = 1) }.toMutableList() // Колода игрока 1
            val player2Deck = allCards.shuffled().take(10).map { it.copy(ownerId = 2) }.toMutableList() // Колода игрока 2

            val player1 = Player(id = 1, name = "Player 1", deck = player1Deck)
            val player2 = Player(id = 2, name = "Player 2", deck = player2Deck)

            // Раздача по 5 случайных карт каждому игроку
            player1.hand.addAll(player1.deck.shuffled().take(5))
            player2.hand.addAll(player2.deck.shuffled().take(5))

            val board = List(5) { x -> List(5) { y -> Cell(x, y) } }

            _gameState.value = GameState(
                currentPlayer = player1,
                opponentPlayer = player2,
                board = board
            )
        }
    }

    fun moveCard(card: Card, newX: Int, newY: Int) {
        val currentState = _gameState.value ?: return  // Если null, выходим из функции
        val currentPosition = currentState.board.flatten().firstOrNull { it.card == card }

        // Добавьте логи
        Log.d("GameScreen", "Trying to move card: ${card.name} from (${currentPosition?.x}, ${currentPosition?.y}) to ($newX, $newY)")

        // Если карта найдена и новая позиция допустима
        if (currentPosition != null && isValidMove(currentPosition.x, currentPosition.y, newX, newY, card.moveSpeed)) {
            // Удаляем карту из текущей клетки
            currentPosition.card = null

            // Устанавливаем карту в новую клетку
            val newCell = currentState.board[newX][newY]
            newCell.card = card

            // Обновляем состояние игры
            _gameState.value = currentState.copy(board = currentState.board)
            Log.d("GameScreen", "Moved card: ${card.name} to new position ($newX, $newY)")
        } else {
            Log.d("GameScreen", "Move not valid for card: ${card.name}. Current position: ${currentPosition}, New position: ($newX, $newY)")
        }
    }

    private fun isValidMove(currentX: Int, currentY: Int, newX: Int, newY: Int, moveSpeed: Int): Boolean {
        val isWithinBoard = newX in 0 until 5 && newY in 0 until 5 // Убедитесь, что новая позиция в пределах поля
        val isWithinMoveSpeed = (kotlin.math.abs(currentX - newX) + kotlin.math.abs(currentY - newY) <= moveSpeed)
        return isWithinBoard && isWithinMoveSpeed
    }

    // Логика выложить карту на доску
    fun placeCardOnBoard(card: Card, x: Int, y: Int, playerId: Int) {
        val currentState = _gameState.value ?: return
        val currentPlayer = currentState.currentPlayer

        if (currentPlayer.mana >= card.cost && currentState.board[x][y].card == null) {
            currentState.board[x][y].card = card
            currentState.board[x][y].ownerId = playerId // Сохраняем ID владельца карты
            currentPlayer.mana -= card.cost
            currentPlayer.hand.remove(card)

            // После размещения карты добавляем новую карту
            val newCard = currentPlayer.deck.shuffled().firstOrNull()
            if (newCard != null) {
                currentPlayer.hand.add(newCard)
            }

            // Обновляем состояние игры
            _gameState.value = currentState
        }
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
        val dy = kotlin.math.abs(cell1.y - cell2.y)
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

