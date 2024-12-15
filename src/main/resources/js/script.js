const cells = document.querySelectorAll(".cell");
let currentTurn = "";
const newGameButton = document.getElementById("newGameButton");
let currentGame = null;
let playerIsFirst = true;
const statusField = document.getElementById("status");
const boardElement = document.getElementById("board");
let gameMode = "vsPlayer";
let gameStatus = "";

function cellClickHandler(event) {
  if (event.target.classList.contains("cell")) {
    const cellIndex = event.target.dataset.cell;
    makeMove(cellIndex);
  }
}

document.addEventListener("DOMContentLoaded", () => {
  newGameButton.addEventListener("click", () => {
    fetch("/game/new", { method: "POST" })
      .then((response) => response.text())
      .then((data) => {
        currentGame = data;
      })
      .catch((error) => console.error("Error creating new game:", error));
    boardElement.addEventListener("click", cellClickHandler);
    cells.forEach((cell) => {
      cell.textContent = "";
    });
    statusField.textContent = "Let's play!";
  });

  document.getElementById("gameMode").addEventListener("change", (event) => {
    gameMode = event.target.value;
    console.log("Game Mode changed to:", gameMode);
  });

  const selectedGameId = localStorage.getItem("selectedGameId");
  if (selectedGameId) {
    loadGame(selectedGameId);
    localStorage.removeItem("selectedGameId");
  }
});

function getGameStatus(status) {
  if (status === "X WON") {
    return "X winning";
  } else if (status === "O WON") {
    return "O winning";
  } else if (status === "DRAW") {
    return "Draw";
  } else {
    return "Unknown result";
  }
}

function updateStatus(data) {
  if (data.status === "") {
    statusField.textContent = `Current turn: ${data.turn}`;
    currentTurn = data.turn;
  } else {
    gameStatus = getGameStatus(data.status);
    statusField.textContent = gameStatus;
    boardElement.removeEventListener("click", cellClickHandler);
  }
}

function updateData(data) {
  updateBoard(data.board);
  updateStatus(data);
}

async function makeMove(cellIndex) {
  if (gameMode === "vsComputer") {
    if (playerIsFirst) {
      await playerMove(cellIndex);
      await computerMove();
    } else {
      await computerMove();
      await playerMove(cellIndex);
    }
  } else {
    await playerMove(cellIndex);
  }
}

async function playerMove(cellIndex) {
  try {
    const response = await fetch(`/game/makeMove/${currentGame}/${cellIndex}`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
    });
    const data = await response.json();
    console.log(data); // Выводим ответ с новым состоянием игры
    updateData(data);
  } catch (error) {
    console.error("Error making player move:", error);
  }
}

async function computerMove() {
  try {
    const response = await fetch(`/game/makeMove/${currentGame}`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
    });
    const data = await response.json();
    console.log(data); // Выводим ответ с новым состоянием игры
    updateData(data);
  } catch (error) {
    console.error("Error making computer move:", error);
  }
}

function updateBoard(board) {
  board.forEach((row, rowIndex) => {
    row.forEach((cell, colIndex) => {
      const cellIndex = rowIndex * 3 + colIndex;
      const cellElement = cells[cellIndex];

      if (cell === 1) {
        cellElement.textContent = "X";
      } else if (cell === -1) {
        cellElement.textContent = "O";
      } else {
        cellElement.textContent = ""; // Если клетка пустая, очищаем текст
      }
    });
  });
}

// Загружаем игру по ID
async function loadGame(gameId) {
  try {
    const response = await fetch(`/game/${gameId}`, {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
      },
    });
    currentGame = gameId;

    if (response.ok) {
      const game = await response.json();
      renderGame(game, gameId);
    } else {
      console.error(
        `Game with ID ${gameId} not found. Status: ${response.status}`
      );
    }
  } catch (error) {
    console.error(`Error loading game with ID ${gameId}:`, error);
  }
}

// Отображение игры на поле
function renderGame(game, id) {
  updateBoard(game.board);
  if (game.status === "NONE") {
    statusField.textContent = `Current turn: ${game.turn}`;
    currentTurn = game.turn;
  } else {
    gameStatus = getGameStatus(game.status);
    statusField.textContent = gameStatus;
  }
  boardElement.addEventListener("click", cellClickHandler);
}
