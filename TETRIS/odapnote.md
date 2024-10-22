 **오류**<br/>updateGame 함수에서 canvas 변수를 참조할 때, 해당 변수가 정의되지 않던 문제

**원인**
canvas는 DOMContentLoaded 이벤트 안에서만 정의되어 있어서, 함수 외부에서 접근할 수 없음. <br/>이를 해결하려면 canvas 변수를 전역으로 선언하여 모든 함수에서 접근할 수 있도록 해야함.

**해결 방법**<br/>
canvas 변수를 전역 변수로 선언.
DOMContentLoaded 이벤트 내부에서 전역 변수에 값을 할당함.



----
위와 같은 형태로 수정 하였으나 canvas 변수가 undefined일 때 canvas.width를 읽으려 해서 발생합니다.

원인
canvas 변수가 전역으로 선언되었지만, updateGame 함수가 실행될 때 canvas가 제대로 초기화되지 않은 상태일 수 있습니다. 특히 setInterval이 실행되기 전에 canvas가 아직 할당되지 않으면 이런 오류가 발생할 수 있습니다.

해결 방법
updateGame 함수가 실행될 때, canvas와 ctx가 올바르게 초기화되었는지 확인하는 조건문을 추가합니다. canvas가 null 또는 undefined가 아니어야 합니다.

수정된 코드 예시
javascript
코드 복사
function updateGame() {
    // canvas와 ctx가 제대로 초기화되었는지 확인
    if (!canvas || !ctx) return;

    tetrominoY++;
    
    if (tetrominoY >= rows) {
        tetrominoY = rows - 1; 
    }
    
    ctx.clearRect(0, 0, canvas.width, canvas.height);
    drawGrid(); 
    drawTetromino(); 
}
이렇게 수정하면 canvas와 ctx가 정의되지 않았을 때 updateGame 함수가 아무 작업도 하지 않고 종료되기 때문에, 오류가 발생하지 않습니다. 수정 후에도 여전히 문제가 있다면, DOMContentLoaded 이벤트가 잘 작동하는지 또는 canvas 요소의 ID가 일치하는지 확인해보세요.

수정된 코드를 적용한 후 테스트해보시고, 추가로 문제가 발생하면 알려주세요!



----
오류 메시지가 사라졌지만 화면에 블럭이 보이지 않는다면, 몇 가지 가능성이 있습니다. 그리드와 블럭을 올바르게 그리고 있는지, 블럭이 예상 위치에 제대로 표시되고 있는지 확인해보겠습니다.

원인 가능성 및 확인 절차
블럭의 위치 문제
drawTetromino 함수에서 x와 y 값을 제대로 설정했는지 확인합니다. 블럭이 화면 밖에 그려지거나 캔버스의 범위를 벗어난 곳에 그려질 수 있습니다.
drawTetromino 함수 호출 시점 문제
updateGame 함수가 잘 호출되고 있는지, 그리고 tetrominoY가 캔버스 범위 내에 있는지 확인합니다.
ctx.fillStyle 및 ctx.fillRect 설정 확인
색상과 블럭의 크기가 올바르게 설정되었는지 확인합니다. 색상이 제대로 설정되지 않았거나 크기 조정이 맞지 않으면 블럭이 안 보일 수 있습니다.
해결 방법
먼저, 몇 가지 확인 후 코드 수정을 제안하겠습니다.

코드 점검 및 수정 예시
drawTetromino 함수 수정

현재 블럭의 위치가 제대로 설정되었는지 확인합니다. x와 y 좌표가 그리드 범위 내에 있어야 합니다.
javascript
코드 복사
function drawTetromino() {
    if (!ctx) return;

    const x = 3; // 블럭이 캔버스 안에 있는 열 위치
    const y = tetrominoY; // 현재 블럭의 행 위치
    
    // tetromino 배열을 순회하며 각 칸을 그림
    for (let row = 0; row < tetromino.length; row++) {
        for (let col = 0; col < tetromino[row].length; col++) {
            if (tetromino[row][col] === 1) {
                ctx.fillStyle = "cyan"; // 블럭 색상
                ctx.fillRect((x + col) * blockSize, (y + row) * blockSize, blockSize, blockSize);
            }
        }
    }
}
updateGame 함수에서 블럭이 그릴 수 있는 범위 내에 있는지 확인

tetrominoY가 rows 범위 내에 있도록 설정했는지 확인합니다.
javascript
코드 복사
function updateGame() {
    if (!canvas || !ctx) return;

    tetrominoY++;
    
    // tetrominoY가 행의 수를 초과하지 않도록 제한
    if (tetrominoY >= rows - tetromino.length) {
        tetrominoY = rows - tetromino.length; 
    }
    
    ctx.clearRect(0, 0, canvas.width, canvas.height);
    drawGrid(); 
    drawTetromino(); 
}
HTML 파일에서 캔버스 크기 확인

캔버스 크기가 충분히 큰지 확인합니다. index.html 파일에서 캔버스 크기를 명시적으로 설정해 보세요.
html
코드 복사
<canvas id="gameCanvas" width="300" height="600"></canvas>
최종 확인
이렇게 수정한 후, 캔버스 크기와 블럭의 위치가 일치하는지, 그리고 drawTetromino 함수가 updateGame에서 제대로 호출되고 있는지 확인해보세요.


----

현재 main.js 코드에서 블럭이 보이지 않는 문제의 원인을 분석하고 해결해보겠습니다. 몇 가지 확인이 필요할 수 있으므로 하나씩 점검해보겠습니다.

1. 캔버스와 컨텍스트 변수 설정 오류
먼저, canvas와 ctx를 전역 변수로 선언했지만, DOMContentLoaded 이벤트 안에서 다시 const 키워드를 사용하여 canvas를 선언했습니다. 이로 인해 전역 변수로 설정된 canvas와 ctx에 올바르게 할당되지 않을 수 있습니다. 따라서 const 대신 기존에 선언한 전역 변수를 사용해야 합니다.

javascript
코드 복사
document.addEventListener("DOMContentLoaded", () => {
    // HTML에서 canvas 요소를 가져옴
    canvas = document.getElementById("gameCanvas"); // 전역 변수 사용
    ctx = canvas ? canvas.getContext("2d") : null;

    // 그리드 그리기 함수 호출
    drawGrid();
    // 게임 루프 설정
    setInterval(() => {
        updateGame();
    }, dropInterval);
});
2. 테트로미노가 캔버스 범위 안에 있는지 확인
현재 drawTetromino 함수에서 블럭의 시작 위치를 (x, tetrominoY)로 설정했는데, tetrominoY가 캔버스 범위를 벗어났거나 범위에 맞지 않으면 블럭이 보이지 않을 수 있습니다. 초기 tetrominoY 값은 0으로 설정하고, updateGame 함수에서 이 값이 증가할 때 블럭이 캔버스 범위를 벗어나지 않도록 설정해야 합니다.

3. 블럭이 그려지는 위치와 크기 점검
테트로미노가 블럭으로 그려질 때 캔버스 안에서 적절한 위치에 그려지는지 확인합니다. 테트로미노의 초기 위치인 (x, y)가 캔버스 범위 안에 있어야 하며, 각 블럭의 크기와 위치가 캔버스 내에 있어야 합니다.

수정 후 코드
javascript
코드 복사
// 전역 변수 설정
const rows = 20; // 행의 개수
const cols = 10; // 열의 개수
const blockSize = 30; 
let tetrominoY = 0; // 테트로미노의 Y 위치 초기화
const dropInterval = 500; // 테트로미노가 이동하는 시간 간격 (500ms)
let ctx;
let canvas;

// 테트로미노 정의
const tetromino = [
    [1, 1, 1, 1]  // 이 배열에서 1은 블록의 일부가 있는 곳이고, 0은 없는 곳을 의미
];

document.addEventListener("DOMContentLoaded", () => {
    // HTML에서 canvas 요소를 가져옴 (전역 변수 사용)
    canvas = document.getElementById("gameCanvas");
    ctx = canvas ? canvas.getContext("2d") : null;

    // 그리드 그리기 함수 호출
    drawGrid();
    // 게임 루프 설정
    setInterval(() => {
        updateGame();
    }, dropInterval);
});

// 그리드를 그리는 함수 정의
function drawGrid() {
    // 회색 선으로 그리드의 경계선을 그린다
    if (!ctx) return;

    ctx.strokeStyle = "gray";
    for (let row = 0; row < rows; row++) {
        for (let col = 0; col < cols; col++) {
            // 각 칸을 캔버스에 그림 사각형의 외곽선을 그리는 함수
            ctx.strokeRect(col * blockSize, row * blockSize, blockSize, blockSize); 
        }
    }
}

// 테트로미노 그리기 함수
function drawTetromino() {
    if (!ctx) return;

    // 테트로미노를 특정 위치(x,y)에 표시하기 위한 변수 설정
    const x = 3; // 블럭이 캔버스 안에 있는 열 위치
    const y = tetrominoY; // 현재 블럭의 행 위치
    
    // 반복문을 사용하여 테트로미노의 각 칸을 순회
    for (let row = 0; row < tetromino.length; row++) {
        for (let col = 0; col < tetromino[row].length; col++) {
            if (tetromino[row][col] === 1) {
                ctx.fillStyle = "green"; // 블럭 색상
                ctx.fillRect((x + col) * blockSize, (y + row) * blockSize, blockSize, blockSize);
            }
        }
    }
}

function updateGame() {
    if (!canvas || !ctx) return;

    // 테트로미노의 Y 위치를 한 칸 아래로 이동
    tetrominoY++;
    
    // 테트로미노가 바닥에 도달했는지 확인
    if (tetrominoY >= rows - tetromino.length) {
        tetrominoY = rows - tetromino.length; 
    }
    
    // 기존 화면을 지우고 새로 그리기
    ctx.clearRect(0, 0, canvas.width, canvas.height);
    drawGrid(); 
    drawTetromino(); 
}
이제 코드를 실행한 후 블럭이 정상적으로 나타나는지 확인해보세요. 만약 여전히 블럭이 보이지 않는다면, console.log로 tetrominoY, x, y 값 등을 출력해보면서 정확한 위치에 블럭이 그려지고 있는지 점검해보세요.
--
