### 클래스 목록 및 진행 순서

1. **Calculator (계산기 핵심 클래스)**
   - 역할: 계산의 중심이 되는 클래스입니다. 입력된 값을 바탕으로 연산 메서드를 호출하고, 연산 순서를 제어하며 계산 결과를 반환합니다. **모든 연산 로직**이 이 클래스에서 처리됩니다.
   - 주요 메서드:
     - `add(a, b)`, `subtract(a, b)`, `multiply(a, b)`, `divide(a, b)`, `mod(a, b)`: 각각의 연산 메서드
     - `calculate(expression)`: 수식을 받아 연산 순서를 고려하여 계산
     - `reset()`: 계산기 초기화
     - `validateInput(input)`: 입력값 검증
2. **Display (화면 표시 및 UI 관리 클래스)**
   - 역할: 사용자에게 **계산 결과와 오류 메시지를 출력**하고, **계산기의 현재 상태**를 표시하는 역할을 담당합니다. GUI를 고려하여 Swing 컴포넌트 (JTextField, JLabel 등)를 포함할 수도 있습니다.
   - 주요 메서드:
     - `showResult(result)`: 계산 결과 출력
     - `showError(errorMessage)`: 오류 메시지 출력
     - `clearDisplay()`: 디스플레이 초기화
3. **InputHandler (입력값 처리 및 이벤트 관리 클래스)**
   - 역할: 키보드 입력 또는 버튼 클릭과 같은 사용자 입력을 처리하여, **계산기 로직에 전달**합니다. 입력값 저장 및 오류 검출 등의 역할을 포함합니다.
   - 주요 메서드:
     - `receiveInput(input)`: 사용자의 입력을 받아 저장
     - `parseInput()`: 입력을 연산자와 피연산자로 분리
     - `isOperator(input)`: 입력이 연산자인지 판별

------

### 클래스명 추천 및 진행 순서

#### 클래스명 및 순서

**추천 클래스명**:

1. **Calculator**: 계산 기능과 관련된 모든 로직을 처리하는 핵심 클래스
2. **InputHandler**: 입력값 처리와 관련된 로직을 담아, Calculator에 전달하는 클래스
3. **Display**: 사용자에게 결과 및 메시지를 보여주는 역할을 담당

#### 진행 순서

1. **Calculator 클래스**: 먼저 계산기의 핵심 기능인 계산 로직을 먼저 구현해야 하므로, `Calculator` 클래스를 우선적으로 개발합니다.
2. **InputHandler 클래스**: `Calculator` 클래스가 완성된 후, 입력값을 처리하고 Calculator에 전달하는 `InputHandler`를 작성합니다.
3. **Display 클래스**: 최종적으로 사용자와의 상호작용을 위한 `Display` 클래스를 추가하여, 최종 계산 결과를 보여주고 오류 발생 시 메시지를 출력하도록 합니다.

------

### 요약

- **클래스 목록**: `Calculator`, `InputHandler`, `Display`

- 진행 순서

  :

  1. `Calculator`
  2. `InputHandler`
  3. `Display`