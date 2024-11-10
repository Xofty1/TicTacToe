const cells = document.querySelectorAll(".cell");
let currentTurn = "";
const newGameButton = document.getElementById("newGameButton");
let currentGame = null;
let playerIsFirst = true;


function cellClickHandler(event) {
  if (event.target.classList.contains("cell")) {
    const cellIndex = event.target.dataset.cell;
    makeMove(cellIndex);
  }
}

document.addEventListener("DOMContentLoaded", () => {
  newGameButton.addEventListener("click", () => {
    fetch("http://localhost:8080/game/new", { method: "POST" })
      .then((response) => response.text())
      .then((data) => {
        currentGame = data;
      })
      .catch((error) => console.error("Error creating new game:", error));
      document.getElementById("board").addEventListener("click", cellClickHandler);
     cells.forEach((cell) => {cell.textContent = "";});
     document.getElementById("status").textContent = "Let's play!";

  });


  document.getElementById("gameMode").addEventListener("change", (event) => {
    gameMode = event.target.value;
    console.log("Game Mode changed to:", gameMode);
  });
});


function updateStatus(data) {
  const statusElement = document.getElementById("status");
  if (data.status == ""){
    statusElement.textContent = `Current turn: ${data.turn}`;
    currentTurn = data.turn;
    }
  else{
    statusElement.textContent = data.status;
    document.getElementById("board").removeEventListener("click", cellClickHandler);
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
    const response = await fetch(
      `http://localhost:8080/game/makeMove/${currentGame}/${cellIndex}`,
      {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
      }
    );
    const data = await response.json();
    console.log(data); // Выводим ответ с новым состоянием игры
    updateData(data);
  } catch (error) {
    console.error("Error making player move:", error);
  }
}

async function computerMove() {
  try {
    const response = await fetch(
      `http://localhost:8080/game/makeMove/${currentGame}`,
      {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
      }
    );
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
