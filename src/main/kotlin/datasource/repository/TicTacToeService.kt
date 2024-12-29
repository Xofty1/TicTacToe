package datasource.repository

import domain.model.Game
import domain.model.GameBoard
import domain.model.User
import domain.service.GameService
import domain.utils.STATUS
import domain.utils.TURN
import sun.security.util.Password
import java.util.*


data class Move(var row: Int, var col: Int, val score: Int)
class TicTacToeService(val repository: GameRepository, val userRepository: UserRepository) : GameService {

    override fun getNextMove(game: Game): Pair<Int, Int> {
        val isMaximizing = (game.turn.type == TURN.X.type)
        val bestMove = minimax(game.board, isMaximizing)
        return Pair(bestMove.row, bestMove.col)
    }

    // Минимакс алгоритм
    fun minimax(gameBoard: GameBoard, isMaximizing: Boolean): Move {
        val board = gameBoard.board
        val currentPlayer = if (isMaximizing) TURN.X else TURN.O
        val opponent = if (isMaximizing) TURN.O else TURN.X

        // Проверка базовых условий выигрыша или ничьей
        if (checkWin(board, TURN.X.type)) return Move(-1, -1, 1) // X выигрывает
        if (checkWin(board, TURN.O.type)) return Move(-1, -1, -1) // O выигрывает
        if (isBoardFull(board)) return Move(-1, -1, 0) // Ничья

        val bestScore = if (isMaximizing) Int.MIN_VALUE else Int.MAX_VALUE
        var bestMove = Move(-1, -1, bestScore)

        // Проход по всем клеткам
        for (row in 0..2) {
            for (col in 0..2) {
                // Если клетка пуста
                if (board[row][col] == TURN.NONE.type) {
                    // Сделать ход
                    board[row][col] = currentPlayer.type
                    // Рекурсивный вызов минимакс
                    val move = minimax(gameBoard, !isMaximizing)
                    board[row][col] = TURN.NONE.type // Отменить ход

                    // Обновить лучший ход для игрока
                    move.row = row
                    move.col = col

                    if (isMaximizing) {
                        if (move.score > bestMove.score) bestMove = move
                    } else {
                        if (move.score < bestMove.score) bestMove = move
                    }
                }
            }
        }
        return bestMove
    }

    // Проверка на выигрыш
    override fun checkWin(board: Array<IntArray>, player: Int): Boolean {
        // Проверка строк и столбцов
        for (i in 0..2) {
            if (board[i].all { it == player }) return true
            if (board.all { it[i] == player }) return true
        }
        // Проверка диагоналей
        if (board[0][0] == player && board[1][1] == player && board[2][2] == player) return true
        if (board[0][2] == player && board[1][1] == player && board[2][0] == player) return true
        return false
    }

    // Проверка, заполнено ли поле
    private fun isBoardFull(board: Array<IntArray>): Boolean {
        return board.all { row -> row.all { it != TURN.NONE.type } }
    }


    override fun validateBoard(game: Game): Boolean {
        return true
    }

    private fun makeMove(game: Game, cell: Pair<Int, Int>): Boolean {
        return if (game.board.board[cell.first][cell.second] == TURN.NONE.type &&
            game.status != STATUS.O_WIN && game.status != STATUS.X_WIN && game.status != STATUS.DRAW
        ) {
            game.board.board[cell.first][cell.second] = game.turn.type

            if (checkWin(game.board.board, game.turn.type)) {
                game.status = if (game.turn == TURN.X) STATUS.X_WIN else STATUS.O_WIN
            } else if (game.board.isFull()) {
                game.status = STATUS.DRAW
            }


            game.turn = if (game.turn == TURN.X) TURN.O else TURN.X

            true
        } else false
    }

    fun updateGame(game: Game, nextMove: Pair<Int, Int>?): Game {
        var newMove = nextMove
        if (newMove == null)
            newMove = getNextMove(game)
        val isMoveAvailable = makeMove(game, newMove) // на будущее)

        return game
    }

    fun createNewGame(login: String, secondUserLogin: String?): Game {
        return Game(
            id = UUID.randomUUID(),
            board = GameBoard(),
            turn = TURN.X,
            firstUserLogin = login,
            secondUserLogin = secondUserLogin
        )
    }

    fun cellToCoordinate(cell: Int): Pair<Int, Int> {
        return Pair((cell / 3), (cell % 3))
    }



}

