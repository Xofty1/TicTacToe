package domain.model

data class GameBoard(
    var board: Array<IntArray> = Array(3) { IntArray(3) { 0 } }
)