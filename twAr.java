public class Solution {
    public int[][] solution(int[] num_list, int n) {
        // num_list의 길이와 n을 사용하여 2차원 배열의 행 수를 계산
        int numRows = num_list.length / n;
        // 2차원 배열을 생성
        int[][] result = new int[numRows][n];

        // 1차원 배열을 2차원 배열로 변환
        for (int i = 0; i < num_list.length; i++) {
            result[i / n][i % n] = num_list[i];
        }

        return result;
    }

    public static void main(String[] args) {
        // 예시 배열과 n값
        int[] num_list = {1, 2, 3, 4, 5, 6, 7, 8};
        int n = 2;

        Solution sol = new Solution();
        int[][] result = sol.solution(num_list, n);

        // 결과 출력
        for (int[] row : result) {
            for (int num : row) {
                System.out.print(num + " ");
            }
            System.out.println();
        }
    }
}
