# T 게임 캐릭터 시스템 기술 설계서

## 추가 기술 요구사항

### 1. 성능 최적화
* **클라이언트 사이드 최적화**
  * 캐릭터 애니메이션 및 이펙트의 WebGL 기반 렌더링
  * 스프라이트 아틀라스를 통한 리소스 최적화
  * 메모리 캐싱을 통한 커스터마이징 에셋 관리

* **서버 사이드 최적화**
  * Connection Pooling을 통한 DB 연결 관리
  * 스킬 이펙트 계산의 서버 사이드 검증
  * 실시간 매칭을 위한 Redis 기반 큐 시스템

### 2. 확장된 데이터베이스 스키마

#### 사용자 진행도 테이블
```sql
CREATE TABLE user_progress (
    progress_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    character_id INT,
    level INT DEFAULT 1,
    exp INT DEFAULT 0,
    achievements JSON,
    last_login TIMESTAMP,
    FOREIGN KEY (character_id) REFERENCES characters(character_id)
);
```

#### 매칭 히스토리 테이블
```sql
CREATE TABLE match_history (
    match_id INT PRIMARY KEY AUTO_INCREMENT,
    room_id VARCHAR(50),
    character_id INT,
    skill_used JSON,
    accuracy FLOAT,
    wpm INT,
    score INT,
    match_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (character_id) REFERENCES characters(character_id)
);
```

### 3. API 엔드포인트 설계

#### 캐릭터 관리
```
POST /api/v1/characters/create
PUT /api/v1/characters/{characterId}/customize
GET /api/v1/characters/{characterId}/stats
PUT /api/v1/characters/{characterId}/skills
```

#### 게임플레이
```
POST /api/v1/matches/create
POST /api/v1/matches/{matchId}/skill
GET /api/v1/matches/{matchId}/status
POST /api/v1/matches/{matchId}/complete
```

### 4. 추가 게임 기능

#### 성장 시스템
* **레벨링 시스템**
  * 경험치 획득: `base_exp * (accuracy_bonus + speed_bonus)`
  * 레벨별 스킬 포인트 획득
  * 스킬 강화 및 특성 개방

* **업적 시스템**
  * 타이핑 속도 기반 업적
  * 정확도 기반 업적
  * 멀티플레이 참여 업적

#### 매칭 시스템
* **실력 기반 매칭**
  * ELO 레이팅 시스템 적용
  * `rating_change = K * (actual_result - expected_result)`
  * 비슷한 실력의 플레이어끼리 매칭

### 5. 보안 고려사항

#### 클라이언트 보안
* 입력 데이터 유효성 검증
* 스킬 사용 쿨다운 클라이언트/서버 동기화
* 치팅 방지를 위한 입력 패턴 분석

#### 서버 보안
* Rate limiting 적용
* WebSocket 연결 인증
* SQL Injection 방지

### 6. 모니터링 및 분석

#### 성능 모니터링
* 서버 응답 시간 추적
* WebSocket 연결 상태 모니터링
* DB 쿼리 성능 분석

#### 사용자 행동 분석
* 캐릭터 타입별 선호도
* 스킬 사용 패턴
* 게임 체류 시간

## 구현 우선순위

1. **Phase 1 - 핵심 기능**
   * 기본 캐릭터 시스템
   * 타이핑 게임 코어 메카닉
   * 단일 플레이어 모드

2. **Phase 2 - 멀티플레이**
   * 실시간 매칭 시스템
   * 기본 멀티플레이 기능
   * 리더보드

3. **Phase 3 - 확장 기능**
   * 캐릭터 커스터마이징
   * 스킬 시스템
   * 업적 시스템

## 테스트 전략

### 단위 테스트
```typescript
describe('Character Skills', () => {
  it('should apply speed bonus correctly', () => {
    const character = new Character('speed');
    const result = character.calculateSpeedBonus(100, 0.95);
    expect(result).toBeCloseTo(1.25);
  });
});
```

### 통합 테스트
* WebSocket 연결 테스트
* DB 트랜잭션 테스트
* 멀티플레이어 동기화 테스트

### 부하 테스트
* 동시 접속자 처리 테스트
* 데이터베이스 부하 테스트
* 네트워크 지연 시뮬레이션

## 확장성 고려사항

### 미래 확장 가능성
* 새로운 캐릭터 타입 추가
* 커스텀 게임 모드
* 길드/클랜 시스템
* 시즌제 운영
* 토너먼트 시스템

### 기술 스택 권장사항
* Frontend: React + TypeScript
* Backend: Node.js/NestJS
* DB: PostgreSQL + Redis
* WebSocket: Socket.io
* 모니터링: Prometheus + Grafana









```
게임 캐릭터 디자인 아이디어 정리
1. 캐릭터 생성 및 커스터마이징
가입 후 캐릭터 생성
이용자는 가입 후 고유한 캐릭터를 생성.
캐릭터는 외형 커스터마이징이 가능하며, 의상, 머리 스타일, 색상 등을 설정할 수 있음.
2. 캐릭터 타입 및 특징
캐릭터 타입
속타형: 빠른 타자 속도 달성 시 특수 효과 발생.
예: 단시간 내 여러 단어 입력 시 점수 보너스 또는 적의 속도 감소 효과.
정확성 중심형: 높은 정확도로 타자 시 강력한 효과.
예: 연속해서 정확히 입력하면 보호막 생성.
밸런스형: 속도와 정확성의 균형 유지.
안정적 성능과 다양한 스킬 발동 가능.
3. 멀티플레이 역할
역할 분배

힐러/마법사: 장문을 정확히 입력하면 아군의 체력 회복 또는 방어막 제공.
도적/암살자: 짧고 빠른 단어를 연속 입력해 적에게 콤보 대미지 발동.
전사: 난이도가 높은 문장을 성공적으로 입력하면 적에게 강력한 일격.
역할 기반 스킬

역할별로 고유 기술 및 특수 효과.
다양한 역할을 조합하여 전략적 플레이 유도.
이점과 문제점
이점
게임성 강화

단순 타자 게임에서 벗어나 RPG 요소를 결합하여 몰입감 상승.
캐릭터와 역할 기반 시스템으로 게임의 다양성과 전략성 제공.
사용자 참여 유도

커스터마이징과 캐릭터 타입 설정으로 개인화된 경험 제공.
캐릭터 성장을 통해 지속적인 게임 참여 유도.
멀티플레이 흥미 증대

역할 분배로 팀 기반 전략 플레이 가능.
유저 간 협업과 경쟁을 동시에 경험.
문제점
복잡성 증가

신규 유저가 게임 메커니즘을 이해하기 어렵거나 진입 장벽이 높아질 가능성.
캐릭터와 스킬 밸런스 문제 발생 가능.
기술 구현

각 타입별 특성과 역할 기반 스킬 구현에 대한 개발 리소스 증가.
실시간 멀티플레이 환경에서의 동기화 문제.
데이터 관리

유저 커스터마이징 정보 및 캐릭터 데이터를 저장 및 처리하기 위한 데이터베이스 설계 복잡성.
문제 해결 방안
복잡성 해결

초기 튜토리얼 제공: 게임 시작 시 역할별 기본 메커니즘을 체험하도록 유도.
단계적 난이도: 초반에는 단순한 타자 게임 요소로 시작, 이후 점진적으로 역할과 스킬 추가.
기술적 해결

스킬 시스템 설계: 스킬 효과와 발동 조건을 분리하여 쉽게 관리.
스킬 테이블: skill_id, type, effect, trigger_condition.
실시간 동기화: WebSocket 또는 gRPC를 활용하여 멀티플레이 환경 동기화.
데이터 관리

데이터베이스 설계:
캐릭터 데이터: character_id, user_id, type, appearance, stats.
스킬 데이터: skill_id, character_id, effect, cooldown.
데이터베이스 최적화: Redis와 같은 캐싱 시스템으로 자주 호출되는 데이터를 빠르게 처리.
설계 구성
DB 설계

캐릭터 테이블
sql
코드 복사
CREATE TABLE characters (
  character_id INT PRIMARY KEY AUTO_INCREMENT,
  user_id INT,
  type VARCHAR(50),
  appearance JSON,
  speed INT,
  accuracy INT,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
스킬 테이블
sql
코드 복사
CREATE TABLE skills (
  skill_id INT PRIMARY KEY AUTO_INCREMENT,
  character_id INT,
  effect VARCHAR(255),
  trigger_condition VARCHAR(255),
  cooldown INT
);
게임 서버 설계

스킬 로직 구현
캐릭터 타입별 스킬과 조건에 따른 발동 처리.
멀티플레이 관리
유저 간의 실시간 데이터 전송: WebSocket 기반.
UI 설계

캐릭터 생성 화면에서 커스터마이징 제공.
실시간으로 타이핑 효과와 스킬 발동을 시각화.

```

