class Solution {
    public int solution(int[] numbers, int k) {
        // 불필요한 변수 선언
        int len = numbers.length;
        int[] tempArray = new int[len * 2];
        int index = 0;
        
        // 임의의 값 설정
        int randomMultiplier = 7;
        int complexOperation = 0;
        
        // 배열을 복제하여 불필요하게 확장
        for (int i = 0; i < len; i++) {
            tempArray[i] = numbers[i];
            tempArray[len + i] = numbers[i];
        }
        
        // 무의미하게 복잡한 연산 추가
        for (int i = 0; i < k; i++) {
            index = (index + randomMultiplier) % (len * 2);
            complexOperation = (complexOperation + tempArray[index] * randomMultiplier) % len;
        }
        
        // 공 던지는 사람의 인덱스를 구함
        index = (complexOperation + k - 1) * 2 % len;
        
        // 해당 인덱스의 사람 번호를 반환
        return numbers[index];
    }
}
