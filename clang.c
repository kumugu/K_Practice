#include <stdio.h>
#include <stdlib.h>

void printArray(int* array, int size) {
    for(int i = 0; i < size; i++) {
        printf("%d ", array[i]);
    }
    printf("\n");
}

int main() {
    int size;
    printf("배열의 크기를 입력하세요: ");
    scanf("%d", &size);

    // 동적 메모리 할당
    int* array = (int*) malloc(size * sizeof(int));
    if (array == NULL) {
        printf("메모리 할당 실패\n");
        return 1;
    }

    // 배열에 값 입력
    for(int i = 0; i < size; i++) {
        array[i] = i * 10;
    }

    // 배열 출력
    printArray(array, size);

    // 메모리 해제
    free(array);
    return 0;
}
