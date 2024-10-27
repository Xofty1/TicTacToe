package domain.utils

enum class TURN(val type: Int) {
    O(-1),
    X(1),
    NONE(0)
}

enum class RESULT(val type: Int) {
    WIN(1),
    LOSE(-1),
    DRAW(0),
    CONTINUE(2)
}

enum class DIAGONALS{
    LeftUpToRightDown,
    RigthUpToLeftDown
}

