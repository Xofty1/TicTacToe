package domain.service

import datasource.repository.GameStorage
import domain.model.Game
import domain.model.GameBoard
import domain.utils.RESULT
import domain.utils.TURN
import java.util.*


data class Move(var row: Int, var col: Int, val score: Int)
object TicTacToeService : GameService {

    override fun getNextMove(game: Game, turn: TURN): Pair<Int, Int> {
        val isMaximizing = (turn.type == TURN.X.type)
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
    fun isBoardFull(board: Array<IntArray>): Boolean {
        return board.all { row -> row.all { it != TURN.NONE.type } }
    }


    override fun validateBoard(game: Game): Boolean {
        return true
    }

    fun makeMove(game: Game, move: Move, turn: TURN): RESULT {
        if (!isBoardFull(game.board.board)) {
            game.board.board[move.row][move.col] = turn.type
        }
        if (checkWin(game.board.board, turn.type)) {
            println("Победил $turn")
            return RESULT.WIN
        }
        if (isBoardFull(game.board.board)) {
            return RESULT.DRAW
        }
        return RESULT.CONTINUE
    }


    // В файле GameService.kt в domain
    fun updateGame(game: Game): Game {

        this.getNextMove(game, game.turn)
        return game.apply {
            this.turn = if (this.turn == TURN.X) TURN.O else TURN.X
        }
    }

    fun createNewGame(): Game {
        val game = Game(
            id = UUID.randomUUID(),
            board = GameBoard(),
            turn = TURN.X
        )
        GameStorage.saveGame(game)
        return game
    }
}


//fun main() {
//    val game = Game(
//        UUID(5, 5),
//        GameBoard(),
//        TURN.X
//    )
//
//    fun printGameBoard(game: Game) {
//        for (i in 0..2) {
//            for (j in 0..2) {
//                print("${game.board.board[i][j]} ")
//            }
//            println()
//        }
//        println()
//    }
//
//    val tttService = TicTacToeService()
//    println("Выберете за кого играть :")
//    if (readLine() == "X") {
//        println("Делайте ход")
//        var res = RESULT.CONTINUE
//        while (res == RESULT.CONTINUE) {
//            val (x, y) = readLine()!!.split(" ").map { it.toInt() }
//
//            res = tttService.makeMove(game, Move(x, y, 0), TURN.X)
//            printGameBoard(game)
//            if (res == RESULT.CONTINUE) {
//                val comp = tttService.getNextMove(game, turn = TURN.O)
//                res = tttService.makeMove(game, Move(comp.first, comp.second, 0), TURN.O)
//                printGameBoard(game)
//            } else break
//        }
//    }
//
//}

