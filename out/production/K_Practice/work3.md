---
layout: single
title: "Java Method (예제)"
toc: true
toc_sticky: true
categories: JAVA
tag: [JAVA]
author_profile: false
sidebar:
    nav: "docs"
---

# Java Method (예제)

* 학생 관리 프로그램 구현하기

## 1. 프로그램 개요

수업 중에 진행한 내용이다.<br/>이 프로그램은 기본적인 학생 정보를 관리하는 콘솔 기반 애플리케이션.<br/>배열과 메서드를 활용하여 CRUD(Create, Read, Update, Delete) 작업의 기본 개념을 구현.<br/>

## 2. 주요 기능

### 2.1 데이터 구조

- 학생 정보는 평행 배열(Parallel Arrays)을 사용하여 저장
  - names[]: 학생 이름 저장
  - hakbuns[]: 학번 저장
  - majors[]: 학과 저장
  - phones[]: 연락처 저장

### 2.2 메서드 구성

1. **학생 정보 입력 (input)**

```java
public static void input(String[] n, int[] h, String[] m, String[] p, Scanner sc) {
    for(int i=0; i<n.length; i++) {
        System.out.println("<<< " + (i+1)+ " 번째 학생 정보 입력 >>> ");
        // 학생 정보 입력 로직
    }
}
```

2. **전체 정보 출력 (output)**

```java
public static void output(String[] na, int[] ha, String[] ma, String[] ph) {
    for(int i=0; i<ha.length; i++) {
        System.out.println("*** " + (i+1) + " 번째 학생 정보 ***");
        // 학생 정보 출력 로직
    }
}
```

3. **학생 정보 검색 (search)**

```java
public static void search(String[] n, int[] h, String[] m, String[] p, Scanner sc) {
    System.out.print("조회할 학생의 학번을 입력하세요. : ");
    int hakbun = sc.nextInt();
    // 학번으로 학생 검색 로직
}
```

4. **정보 수정 (modify)**

```java
public static void modify(int[] h, String[] m, String[] p, Scanner sc) {
    System.out.print("수정할 학생의 학번을 입력하세요. : ");
    int hakbun = sc.nextInt();
    // 학생 정보 수정 로직
}
```

5. **프로그램 종료 (end)**

```java
public static String end(Scanner sc) {
    System.out.println("프로그램을 종료하시겠습니까?(Y:종료 / N:계속) : ");
    return sc.next();
}
```

## 3. 전체 소스 코드

```java
package exam;

import java.util.Scanner;

public class Exam {
    // 위의 모든 메서드 구현
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        // 배열 초기화
        System.out.print("학생 수를 입력하세요. : ");
        String[] names = new String[sc.nextInt()];
        int[] hakbuns = new int[names.length];
        String[] majors = new String[names.length];
        String[] phones = new String[names.length];
        
        // 메인 프로그램 루프
        while(true) {
            // 메뉴 출력 및 처리 로직
        }
    }
}
```

## 4. 프로그램의 특징과 장점

1. **모듈화된 구조**
   - 각 기능이 독립적인 메서드로 구현
   - 코드 재사용성과 유지보수성 향상

2. **직관적인 메뉴 시스템**
   - 사용자 친화적인 콘솔 인터페이스
   - 명확한 메뉴 구조로 쉬운 네비게이션

3. **효율적인 데이터 관리**
   - 평행 배열을 통한 관계형 데이터 구조 구현
   - 빠른 검색과 수정이 가능한 구조

4. **견고한 예외 처리**
   - 사용자 입력 검증
   - 안정적인 프로그램 실행 보장

## 5. 개선 가능한 부분

1. **데이터 영속성**
   - 현재는 메모리에만 데이터 저장
   - 파일이나 데이터베이스 연동 가능

2. **객체 지향적 설계**
   - Student 클래스를 만들어 객체 지향적으로 재구성 가능
   - ArrayList 등의 컬렉션 프레임워크 활용 가능

3. **검증 로직**
   - 중복 학번 체크
   - 입력값 유효성 검사 강화

4. **사용자 인터페이스**
   - GUI 인터페이스로 개선 가능
   - 더 직관적인 데이터 표현 방식 적용 가능

## 6. 활용 방법

1. **교육용 프로젝트**
   - Java 기본 문법 학습
   - 메서드와 배열 활용 실습

2. **실무 응용**
   - 기본 CRUD 작업 이해
   - 데이터 관리 시스템 설계 기초

## 7. 프로그래밍 팁

1. **코드 구조화**
   - 관련 기능끼리 모듈화
   - 명확한 메서드 명명 규칙 적용

2. **메모리 관리**
   - 배열 크기의 효율적 관리
   - 불필요한 객체 생성 최소화

3. **사용자 경험**
   - 명확한 안내 메시지 제공
   - 일관된 인터페이스 유지