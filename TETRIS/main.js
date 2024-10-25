// 전역 변수로 설정
const rows = 20; // 행의 개수 (전역변수로 설정)
const cols = 10; // 열의 개수 (전역변수로 설정
const blockSize = 30; 
let tetrominoY = 0; // 테트로미노의 Y 위치 초기화
let tetrominoX = 3; 
const dropInterval = 500; // 테트로미노가 이동하는 시간 간격 (500ms)
let ctx;
let canvas;

// 테트로미노 정의
let tetromino = [
    [1, 1, 1, 1]  // 이 배열에서 1은 블록의 일부가 있는 곳이고, 0은 없는 곳을 의미
];


function moveLeft() {
    if (!isCollision(tetromino, tetrominoX - 1, tetrominoY)) {
        tetrominoX--;
        updateGame();
    }
}

function moveRight() {
    if (!isCollision(tetromino, tetrominoX + 1, tetrominoY)) {
        tetrominoX++;
        updateGame();
    }
}

function moveDown() {
    if (!isCollision(tetromino, tetrominoX, tetrominoY + 1)) {
        tetrominoY++; // 한 칸 아래로 이동
        updateGame(); // 화면 갱신 
    }
}

function rotateTetromino() {
    const rotated = rotateMatrix(tetromino);
    if (!isCollision(rotated, tetrominoX, tetrominoY)) {
        tetromino = rotated;
        updateGame();
    }
}

function rotateMatrix(matrix){
    const rowLength = matrix.length;
    const colsLength = matrix[0].length;
    const rotated = [];

    for (let col = 0; col < cols; col++) {
        rotated[col] = [];
        for (let row = 0; row < rowLength; row++) {
             rotated[col][row] = matrix[rowLength - 1 - row][col];
        }
    }
    return rotated;
}

function isCollision(rotated, offsetX, offsetY) {
    for (let row = 0; row < rotated.length; row++) {
        for (let col = 0; col < rotated[row].length; col++) {
            if  (rotated[row][col] === 1)  {
                let newX = offsetX + col;
                let newY = offsetY + row;
                // 게임 보드 경게를 벗어나는지 확인
                if (newX < 0 || newX >= cols || newY >= rows) {
                    return true;
                }
                // 다른 블록과 겹치는지 확인하는 로직 추가 필요 
            }
        }
    }
    return false;
}



document.addEventListener("keydown", (event) => {
    switch (event.key) {
        case "ArrowUp" :
            rotateTetromino(); // 위쪽 방향키로 테트로미노 회전
            break;
        case "ArrowDown" :
            moveDown(); // 아래쪽 방향키로 테트로미노 빠르게 내려가기
            break;
        case "ArrowLeft" : 
            moveLeft(); // 왼쪽 방향키로 테트로미노 왼쪽 이동
            break;
        case "ArrowRight" :
            moveRight(); // 오른쪽 방향키로 테트로미노 오른족 이동
            break;
        }
});


document.addEventListener("DOMContentLoaded", () => {
    // HTML에서 canvas 요소를 가져옴
    canvas = document.getElementById("gameCanvas");
    // 2D 그리기 컨텍스트를 가져옴
    ctx = canvas ? canvas.getContext("2d") : null;

    // 그리드 그리기 함수 호출
    drawGrid();
    // 게임 루프 설정
    setInterval(updateGame, dropInterval);
});


// 그리드를 그리는 함수 정의
function drawGrid() {
    // 회색 선으로 그리드의 경계선을 그린다
    if (!ctx) return;
    ctx.strokeStyle = "gray";
    for (let row = 0; row < rows; row++) {
        for (let col = 0; col < cols; col++) {
            // 각 칸을 캔버스에 그림 사각형의 외곽선을 그리는 함수
            // ctx.strokeRect(x, y, width, height)
            ctx.strokeRect(col * blockSize, row * blockSize, blockSize, blockSize); 
        }
    }
 }


// 테트로미노 그리기 함수
function drawTetromino() {
    if (!ctx) return; // ctx가 초기화되지 않을 경우 함수 종료 

    // 테트로미노를 특정 위치(x,y)에 표시하기 위한 변수 설정
    const x = tetrominoX; // 예시로 열 위치를 3으로 설정
    const y = tetrominoY; // 행 위치는 상단(0)부터 시작

    // 반복문을 사용하여 테트로미노의 각 칸을 순회
    for (let row = 0; row < tetromino.length; row++) {
        for (let col = 0; col < tetromino[row].length; col++) {
            // 테트로미노 배열에서 값이 1인 곳만 그리기
            if (tetromino[row][col] === 1) {
                ctx.fillStyle = "#ce86c2"; // 블록 색상
                ctx.fillRect((x + col) * blockSize, (y + row) * blockSize, blockSize, blockSize);
            }
        }
    }
}


function updateGame() {
    // canvas와 ctx가 제대로 초기화 되었는지 확인
    if (!canvas || !ctx) return;
    // 테트로미노의 Y 위치를 한 칸 아래로 이동
    tetrominoY++;

    // 테트로미노가 바닥에 도달했는지 확인
    if (tetrominoY >= rows - tetromino.length) {
        tetrominoY = rows - tetromino.length; // 더 이상 이동하지 않도록 Y 위치 고정
        // 나중에 테트로미노 고정 및 새로운 테트로미노 생성 로직 추가
    }

    // 기존 화면을 지우고 새로 그리기
    ctx.clearRect(0, 0, canvas.width, canvas.height);
    drawGrid(); // 그리드 다시 그리기
    drawTetromino(); // 테트로미노 다시 그리기
}



