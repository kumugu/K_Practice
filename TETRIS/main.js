document.addEventListener("DOMContentLoaded", () => {

    // HTML에서 canvas 요소를 가져옴
    const canvas = document.getElementById("gameCanvas");
    // 2D 그리기 컨텍스트를 가져옴
    const ctx = canvas.getContext("2d");

    // =====================
    // 게임 보드 크기 설정
    const rows = 20; // 행의 개수
    const cols = 10; // 열의 개수
    const blockSize = 30; // 각 칸의 크기 

    // 그리드를 그리는 함수 정의
    function drawGrid() {
        // 회색 선으로 그리드의 경계선을 그린다
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
});

