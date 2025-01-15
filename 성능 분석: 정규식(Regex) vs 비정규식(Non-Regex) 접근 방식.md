# 성능 분석: 정규식(Regex) vs 비정규식(Non-Regex) 접근 방식

## 소개
이 문서는 문자열 처리 작업에서 **정규식(Regex)**과 **비정규식(Non-Regex)** 접근 방식 간의 성능 차이를 분석한다. 결과에는 실행 시간, CPU 사용 시간, 메모리 사용량 등의 지표가 포함되고, 네 가지 테스트 케이스를 기반으로 비교를 수행한다:

1. **간단한 모음 제거**
2. **숫자 제거**
3. **대문자 제거**
4. **알파벳 및 숫자 제거**

---

## 테스트 환경
- **운영체제**: Windows 10
- **Java 버전**: JDK 17
- **실행 환경**: IntelliJ IDEA Community Edition 2024.2.4
- **테스트 입력**: 약 1,000,000자의 문자열(`abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789` 반복 생성)

---

## 결과
### 테스트 케이스 1: 간단한 모음 제거
| 지표                   | 정규식(Regex)         | 비정규식(Non-Regex)   |
|-----------------------|-----------------------|-----------------------|
| **실행 시간**         | 17,340,300 ns        | 16,216,200 ns        |
| **CPU 시간**          | 15,625,000 ns        | 15,625,000 ns        |
| **메모리 사용량**      | 2,470,584 bytes      | 3,297,240 bytes      |

### 테스트 케이스 2: 숫자 제거
| 지표                   | 정규식(Regex)         | 비정규식(Non-Regex)   |
|-----------------------|-----------------------|-----------------------|
| **실행 시간**         | 13,806,900 ns        | 5,631,100 ns         |
| **CPU 시간**          | 15,625,000 ns        | 0 ns                 |
| **메모리 사용량**      | 6,356,384 bytes      | 3,309,152 bytes      |

### 테스트 케이스 3: 대문자 제거
| 지표                   | 정규식(Regex)         | 비정규식(Non-Regex)   |
|-----------------------|-----------------------|-----------------------|
| **실행 시간**         | 15,983,900 ns        | 12,115,900 ns        |
| **CPU 시간**          | 15,625,000 ns        | 0 ns                 |
| **메모리 사용량**      | 8,691,792 bytes      | 3,290,152 bytes      |

### 테스트 케이스 4: 알파벳 및 숫자 제거
| 지표                   | 정규식(Regex)         | 비정규식(Non-Regex)   |
|-----------------------|-----------------------|-----------------------|
| **실행 시간**         | 22,854,600 ns        | 2,355,900 ns         |
| **CPU 시간**          | 15,625,000 ns        | 0 ns                 |
| **메모리 사용량**      | 27,271,232 bytes     | 2,220,744 bytes      |

---

## 관찰 결과
### 1. **실행 시간**
- 정규식은 반복적이고 간단한 문자 필터링 작업에서 비정규식보다 느린 경향이 있다.
- 비정규식 방식은 직접적인 흐름 제어를 통해 추가적인 컴파일 오버헤드가 없어 효율적이다.

### 2. **메모리 사용량**
- 정규식은 `Pattern` 객체의 컴파일 및 내부 구조로 인해 비정규식보다 메모리를 더 많이 사용한다.
- 비정규식 방식은 간단한 StringBuilder와 조건문만 사용하므로 메모리 사용량이 적다.

### 3. **CPU 시간**
- 정규식의 CPU 시간은 안정적으로 일정하지만, 비정규식은 작업의 복잡성에 따라 더 적은 CPU를 사용하는 경우가 많다.

---

## 코드 설명
### 핵심 기능
이 테스트 스크립트는 다음을 측정한다:
- **실행 시간**: `System.nanoTime()`으로 측정.
- **CPU 시간**: `ThreadMXBean.getCurrentThreadCpuTime()`으로 측정.
- **메모리 사용량**: `Runtime.totalMemory()`와 `Runtime.freeMemory()`를 비교하여 계산.

### 코드 예시
#### 정규식 접근 방식:
```java
String resultRegex = input.replaceAll("[aeiou]", "");
```

#### 비정규식 접근 방식:
```java
StringBuilder resultNonRegex = new StringBuilder();
for (char ch : input.toCharArray()) {
    if ("aeiou".indexOf(ch) == -1) {
        resultNonRegex.append(ch);
    }
}
```

### 전체 코드:
```java
import java.util.regex.Pattern;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

public class RegexVsNonRegex {
    public static void main(String[] args) {
        String input = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".repeat(10000); // 긴 테스트 문자열 생성

        // 테스트 케이스 1: 간단한 모음 제거
        System.out.println("Test Case 1: Simple Vowel Removal");
        testPerformance(input, "[aeiou]", "aeiou");

        // 테스트 케이스 2: 숫자 제거
        System.out.println("Test Case 2: Digit Removal");
        testPerformance(input, "\\d", "0123456789");

        // 테스트 케이스 3: 대문자 제거
        System.out.println("Test Case 3: Uppercase Removal");
        testPerformance(input, "[A-Z]", "ABCDEFGHIJKLMNOPQRSTUVWXYZ");

        // 테스트 케이스 4: 알파벳과 숫자 혼합 제거
        System.out.println("Test Case 4: Alphanumeric Removal");
        testPerformance(input, "[a-zA-Z0-9]", "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789");
    }

    public static void testPerformance(String input, String regexPattern, String charSet) {
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        Runtime runtime = Runtime.getRuntime();

        // 정규식을 사용한 방법
        runtime.gc(); // 가비지 컬렉션 실행 전
        long memoryBeforeRegex = runtime.totalMemory() - runtime.freeMemory();
        long cpuTimeStartRegex = threadMXBean.getCurrentThreadCpuTime();
        long startTimeRegex = System.nanoTime();
        String resultRegex = input.replaceAll(regexPattern, "");
        long endTimeRegex = System.nanoTime();
        long cpuTimeEndRegex = threadMXBean.getCurrentThreadCpuTime();
        long memoryAfterRegex = runtime.totalMemory() - runtime.freeMemory();

        System.out.println("Regex Time: " + (endTimeRegex - startTimeRegex) + " ns");
        System.out.println("Regex CPU Time: " + (cpuTimeEndRegex - cpuTimeStartRegex) + " ns");
        System.out.println("Regex Memory Usage: " + (memoryAfterRegex - memoryBeforeRegex) + " bytes");

        // 정규식을 사용하지 않은 방법
        runtime.gc(); // 가비지 컬렉션 실행 전
        long memoryBeforeNonRegex = runtime.totalMemory() - runtime.freeMemory();
        long cpuTimeStartNonRegex = threadMXBean.getCurrentThreadCpuTime();
        long startTimeNonRegex = System.nanoTime();
        StringBuilder resultNonRegex = new StringBuilder();
        for (char ch : input.toCharArray()) {
            if (charSet.indexOf(ch) == -1) {
                resultNonRegex.append(ch);
            }
        }
        long endTimeNonRegex = System.nanoTime();
        long cpuTimeEndNonRegex = threadMXBean.getCurrentThreadCpuTime();
        long memoryAfterNonRegex = runtime.totalMemory() - runtime.freeMemory();

        System.out.println("Non-Regex Time: " + (endTimeNonRegex - startTimeNonRegex) + " ns");
        System.out.println("Non-Regex CPU Time: " + (cpuTimeEndNonRegex - cpuTimeStartNonRegex) + " ns");
        System.out.println("Non-Regex Memory Usage: " + (memoryAfterNonRegex - memoryBeforeNonRegex) + " bytes");
        System.out.println();
    }
}
```

---

## 결론
### 정규식을 사용할 때
- 복잡한 패턴 처리 시 코드가 간결하고 유지보수가 쉬움.
- 다수의 조건이 포함된 문자열 처리 작업에서 효과적.

### 정규식을 피해야 할 때
- 성능이 중요한 대규모 데이터 처리 작업.
- 단순한 문자열 필터링 작업에서 비효율적일 수 있음.

---

## 향후 작업
- 가비지 컬렉션 시간 및 쓰레드 오버헤드 같은 추가 성능 지표 포함.
- 빈 문자열 또는 매우 큰 데이터셋과 같은 경계 상황 테스트.
- 다른 프로그래밍 언어와 비교하여 정규식 효율성 분석.

