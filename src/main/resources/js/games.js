const gameListElement = document.getElementById("gameList");
const backToGameButton = document.getElementById("backToGame");

// Загружаем список игр
async function fetchGameList() {
  const response = await fetch("/games");
  const games = await response.json();
  renderGameList(games);
}

// Отображаем список игр
function renderGameList(games) {
  gameListElement.innerHTML = "";
  Object.entries(games).forEach(([id, game]) => {
    const li = document.createElement("li");

    let gameStatus = "";

    if (game.status === "X_WIN") {
      gameStatus = "X winning";
    } else if (game.status === "O_WIN") {
      gameStatus = "O winning";
    } else if (game.status === "DRAW") {
      gameStatus = "Draw";
    } else {
      gameStatus = "Unknown result";
    }

    li.innerHTML = `Game ID: ${id}<br>Game status: ${gameStatus}`;

    li.dataset.id = id;
    li.addEventListener("click", () => selectGame(id));
    gameListElement.appendChild(li);
  });
}

// При выборе игры
function selectGame(gameId) {
  // Сохраняем ID игры в localStorage
  localStorage.setItem("selectedGameId", gameId);
  // Переход на главную страницу
  window.location.href = "index.html";
}

// Возврат к главной странице
backToGameButton.addEventListener("click", () => {
  window.location.href = "/";
});

// Загружаем список игр при загрузке страницы
document.addEventListener("DOMContentLoaded", fetchGameList);
