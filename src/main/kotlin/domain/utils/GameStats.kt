package domain.utils

enum class TURN(val type: Int) {
    O(-1),
    X(1),
    NONE(0)
}

enum class STATUS(val result: String) {
    X_WIN("X WON"),
    O_WIN("O WON"),
    DRAW("DRAW"),
    NONE("")
}

