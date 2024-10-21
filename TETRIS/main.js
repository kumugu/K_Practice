// 전역 변수로 설정
let ctx;
const blockSize = 30; // 전역 변수로 설정

// 테트로미노 정의
const tetromino = [
    [1, 1, 1, 1]  // 이 배열에서 1은 블록의 일부가 있는 곳이고, 0은 없는 곳을 의미
];

// 테트로미노 그리기 함수
function drawTetromino() {
    if (!ctx) return; // ctx가 초기화되지 않을 경우 함수 종료 

    // 테트로미노를 특정 위치(x,y)에 표시하기 위한 변수 설정
    const x = 3; // 예시로 열 위치를 3으로 설정
    const y = 0; // 행 위치는 상단(0)부터 시작

    // 반복문을 사용하여 테트로미노의 각 칸을 순회
    for (let row = 0; row < tetromino.length; row++) {
        for (let col = 0; col < tetromino[row].length; col++) {
            // 테트로미노 배열에서 값이 1인 곳만 그리기
            if (tetromino[row][col] === 1) {
                ctx. fillStyle = "cyan"; // 블록 색상
                ctx.fillRect((x + col) * blockSize, (y + row) * blockSize, blockSize, blockSize);
            }
        }
    }
}



document.addEventListener("DOMContentLoaded", () => {

    // HTML에서 canvas 요소를 가져옴
    const canvas = document.getElementById("gameCanvas");
    // 2D 그리기 컨텍스트를 가져옴
    ctx = canvas ? canvas.getContext("2d") : null;

    // =====================
    // 게임 보드 크기 설정
    const rows = 20; // 행의 개수
    const cols = 10; // 열의 개수

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
    // 그리드 그리기 함수 호출
    drawGrid();
    // 테트로미노 그리기 함수 호출
    drawTetromino();
});




