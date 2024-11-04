---
layout: single
title: "Java Method (예제2)"
toc: true
toc_sticky: true
categories: JAVA
tag: [JAVA]
author_profile: false
sidebar:
    nav: "docs"
---
## 학생 관리 프로그램(예제2)


이 코드는 이전 내용과 같은 문제로 만든 학생 관리 프로그램으로,<br/> 
수업내용과는 다르게 바꿔서 만들어보았다. <br/>
아래는 작업 순서를 정리하고 해당 부분을 분석하여 예시 코드와 함께 설명한 내용이고<br/>
마지막에는 이전 내용과 다른 점에 대해서 비교하였다.<br/>

------
 1. 메뉴 표시

```java
    System.out.println("=== === === 메뉴 === === ===");
    System.out.println("이용 할 메뉴 번호를 입력 하세요...");
    System.out.println("1. 학생 등록 ");
    System.out.println("2. 전체 출력 ");
    System.out.println("3. 학생 조회 ");
    System.out.println("4. 정보 수정 ");
    System.out.println("5. 프로그램 종료");
    System.out.println("=== === === === === === ===");
```




### 1. **학생 등록 기능** (`stReg` 메서드)

학생의 이름, 학번, 학과, 전화번호를 입력 받아 `Student` 객체를 생성하고 `studentList`에 추가합니다.

```java
public static void stReg() {  
    Scanner sc = new Scanner(System.in);
    System.out.print("학생 이름을 입력 하세요: ");
    String name = sc.next();
    System.out.print("학번을 입력 하세요: ");
    int stNo = sc.nextInt();
    System.out.print("학과를 입력 하세요: ");
    String className = sc.next();
    System.out.print("전화번호를 입력 하세요: ");
    String phone = sc.next();
    
    Student student = new Student(name, stNo, className, phone);
    studentList.add(student); // 리스트에 학생 추가
    student.printStInfo(); // 등록한 학생 정보 출력
}
```

**설명**: `sc.next()`와 `sc.nextInt()`로 입력을 받고, `Student` 클래스의 인스턴스를 생성하여 `ArrayList`인 `studentList`에 추가.

#### 전체 코드

```java
public static void stReg() {  
    // ...
    System.out.println("이전 화면으로 가려면 'Y', 종료하려면 'N'을 누르세요...");
    String choice = sc.next();
    if(choice.equalsIgnoreCase("Y")) {
        main(new String[] {});
    } else if(choice.equalsIgnoreCase("N")){
        System.exit(0);
    } else {
        stReg();
    }
}
```

### 2. **전체 학생 출력 기능** (`ttPrint` 메서드)

모든 학생 정보를 출력합니다.

```java
public static void ttPrint() {
    System.out.println("=== 전체 학생 목록 ===");
    for (Student student : studentList) {
        student.printStInfo();
    }
}
```

### 3. **학생 조회 기능** (`stInfo` 메서드)

사용자가 입력한 옵션에 따라 이름, 학번, 학과, 전화번호 중 하나로 학생을 조회합니다.

```java
public static void stInfo() {
    Scanner sc = new Scanner(System.in);
    System.out.println("조회할 옵션을 선택하세요: ");
    System.out.println("1. 이름 조회");
    System.out.println("2. 학번 조회");
    int option = sc.nextInt();
    
    if (option == 1) {
        System.out.print("조회할 이름을 입력하세요: ");
        String name = sc.next();
        for (Student student : studentList) {
            if (student.searchName().equals(name)) {
                student.printStInfo();
            }
        }
    }
}
```

**설명**: 사용자가 조회할 조건을 입력하고, `studentList`에서 일치하는 학생의 정보를 출력합니다.

### 4. **학생 정보 수정 기능** (`stModi` 메서드)

학번으로 학생을 찾아 이름, 학과, 전화번호 중 하나를 수정합니다.

```java
public static void stModi() {
    Scanner sc = new Scanner(System.in);
    System.out.print("수정할 학생의 학번을 입력하세요: ");
    int stNo = sc.nextInt();
    
    Student foundStudent = null;
    for (Student student : studentList) {
        if (student.searchNo() == stNo) {
            foundStudent = student;
            break;
        }
    }
    
    if (foundStudent != null) {
        System.out.println("수정할 정보를 선택하세요: 1. 이름 2. 학과 3. 전화번호");
        int option = sc.nextInt();
        
        if (option == 1) {
            System.out.print("새로운 이름을 입력하세요: ");
            foundStudent.updateName(sc.next());
        }
    }
}
```

### 5. **프로그램 종료 기능** (`exit` 메서드)

```java
public static void exit() {
    System.out.println("프로그램을 종료합니다.");
    System.exit(0);
}
```

**설명**: 프로그램 종료를 위해 `System.exit(0)` 메서드를 사용하여 즉시 종료합니다.

### 전체 코드

```java
public class Home {
    static ArrayList<Student> studentList = new ArrayList<>();
    // 각 기능 메서드 구현 (stReg, ttPrint, stInfo, stModi, exit 등)
    public static void main(String[] args) {
        // 메뉴 화면 표시 및 선택 구현
    }
}
```

이 코드들은 학생 정보 등록, 출력, 조회, 수정, 종료 기능을 간단한 학사 관리 프로그램으로 구성하는 예시입니다.


두 가지 코드가 학생 관리 프로그램이라는 점은 같지만, 구현 방식과 데이터 관리 방식이 다릅니다. 간단히 비교해 보겠습니다.

### 1번 코드 (배열 사용)

- **데이터 구조**: `String[]`와 `int[]` 배열을 사용하여 학생의 정보를 저장합니다. 배열 크기가 고정되어 있어, 학생 수를 초과하는 경우 배열을 재할당해야 합니다.
- **기능**: 학생 등록, 전체 출력, 조회, 정보 수정, 종료를 지원합니다. 각 기능이 개별 메서드로 구현되어 있으며, 메뉴 선택을 통해 프로그램을 제어합니다.
- **장점**: 배열을 사용하여 간단한 구조로 구현되어 있으며, 메모리 효율성이 높습니다.
- **단점**: 배열 크기가 고정되어 있어 유연성이 떨어지며, 동적 할당이 불가능합니다.

### 2번 코드 (ArrayList 및 클래스 사용)

- **데이터 구조**: `ArrayList<Student>`로 학생 정보를 저장하며, `Student`라는 클래스를 정의하여 학생의 이름, 학번, 학과, 전화번호를 객체로 관리합니다.
- **기능**: 학생 등록, 전체 출력, 조회, 정보 수정, 종료 등 기능을 포함합니다. 메뉴 선택 후 이전 화면으로 돌아가는 기능이 추가되어 있으며, 학생 조회 시 다양한 기준(이름, 학번, 학과, 전화번호)으로 조회할 수 있습니다.
- **장점**: `ArrayList`를 사용하여 동적 배열을 구현함으로써 학생 수에 제한이 없으며, 객체 지향적 설계로 코드가 깔끔해집니다. `Student` 클래스에서 `printStInfo()` 메서드를 통해 학생 정보를 출력할 수 있어 응집도가 높습니다.
- **단점**: 배열 방식에 비해 메모리 사용이 증가할 수 있으며, 약간의 오버헤드가 있습니다.

### 비교

- **확장성**: 2번 코드가 `ArrayList`와 객체 지향 설계를 활용해 유지보수와 확장에 유리합니다.
- **간단한 구조**: 1번 코드는 간단하게 배열로 처리하며, Java 입문자에게 적합한 구조입니다.
- **기능성**: 2번 코드는 다양한 조회 기능과 유연한 구조를 갖추고 있어, 실제 학생 관리 시스템에 더 적합합니다.