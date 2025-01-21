---
layout: single
title: "Java 객체 배열 예제"
toc: true
toc_sticky: true
categories: JAVA
tag: [JAVA]
author_profile: false
sidebar:
    nav: "docs"
---
```java
package exxx;

public class Book {

	// 필드선언 (인스턴스 변수)
	String title; 
	String author;
	int pages;
	
	// 생성자
	public Book(String title, String author, int pages) {
		this.title = title;
		this.author = author;
		this.pages = pages;
	}

	// 정보출력 메서드
	public void printInfo() {
		System.out.println("제목 : " + title);
		System.out.println("저자 : " + author);
		System.out.println("페이지수 : " + pages);
	}
	
}
```
```java
package exxx;

import java.util.Scanner;

public class Library {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		// 도서 배열 생성
		System.out.print("입력해야할 도서는 몇권 입니까? : ");
		Book[] books = new Book[sc.nextInt()];
		
		// 도서 정보 입력
		for(int i=0; i<books.length; i++) {
			System.out.println((i+1) + "번 도서의 정보를 입력하세요" );
			System.out.print("제목 : ");
			String title = sc.next();
			System.out.print("저자 : ");
			String author = sc.next();
			System.out.print("페이지수 : ");
			int pages = sc.nextInt();
			books[i] = new Book(title, author, pages);
		}
		
		System.out.println("\n도서 정보 출력 ");
		for(Book book : books) {
			book.printInfo();
			System.out.println("\n=== === === === ");
		}
	}
}

```
객체배열에 대한 예제를 해석해보자

코드 해석
패키지 및 클래스 선언

```java
package exxx;
public class Book {
```
exxx라는 패키지에 속한 Book 클래스다. <br/>
이 클래스는 도서의 제목, 저자, 페이지 수와 관련된 정보를 저장한다.<br/>

필드 선언 (인스턴스 변수)
```java
String title; 
String author;
int pages;
```
title: 도서의 제목을 저장하는 String 타입의 변수<br/>
author: 도서의 저자를 저장하는 String 타입의 변수<br/>
pages: 도서의 페이지 수를 저장하는 int 타입의 변수<br/>
이 필드들은 각 Book 객체마다 고유한 값을 가지는 인스턴스 변수<br/>

생성자

```java
public Book(String title, String author, int pages) {
    this.title = title;
    this.author = author;
    this.pages = pages;
}
```
생성자는 Book 클래스의 인스턴스를 생성할 때 호출.<br/>

title, author, pages의 값을 매개변수로 받아, <br/>
this.title, this.author, this.pages와 같이 클래스의 인스턴스 변수에 할당<br/>
this 키워드는 생성자 매개변수와 인스턴스 변수를 구분하기 위해 사용<br/>

정보 출력 메서드
```java
public void printInfo() {
    System.out.println("제목 : " + title);
    System.out.println("저자 : " + author);
    System.out.println("페이지수 : " + pages);
}
```
printInfo 메서드는 Book 객체의 정보를 출력하는 역할을 한다.<br/>
title, author, pages 필드에 저장된 값을 각각 출력.<br/>
Library 클래스에서 이 메서드를 호출해, 사용자가 입력한 도서 정보가 화면에 표시.<br/>


코드 해석
패키지 및 클래스 선언
```java
package exxx;
import java.util.Scanner;
public class Library {
```
이 코드는 exxx라는 패키지에 속해 있으며, <br/>
Library라는 클래스로 구성되어 있다.<br/>
Scanner를 사용하기 위해 java.util.Scanner 패키지를 임포트한다.<br/>

메인 메서드

```java
public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
```
main 메서드는 프로그램 실행의 시작 지점으로, <br/>
Scanner 객체를 생성해 사용자 입력을 받을 준비 한다.<br/>

도서 배열 생성
```java
System.out.print("입력해야할 도서는 몇권 입니까? : ");
Book[] books = new Book[sc.nextInt()];
```
사용자에게 입력해야 할 도서의 권수를 묻고, <br/>
입력받은 정수값을 이용해 Book 타입의 배열 books를 생성한다. <br/>
배열의 크기는 사용자가 입력한 값에 따라 결정된다.<br/>

도서 정보 입력
```java
for(int i = 0; i < books.length; i++) {
    System.out.println((i + 1) + "번 도서의 정보를 입력하세요" );
    System.out.print("제목 : ");
    String title = sc.next();
    System.out.print("저자 : ");
    String author = sc.next();
    System.out.print("페이지수 : ");
    int pages = sc.nextInt();
    books[i] = new Book(title, author, pages);
}
```
for 반복문을 통해 각 도서의 정보를 사용자로부터 입력받음.<br/>
제목: title 변수에 저장<br/>
저자: author 변수에 저장<br/>
페이지 수: pages 변수에 저장<br/>
입력받은 title, author, pages 정보를 사용해 Book 클래스의 인스턴스를 생성한 후, 이를 books[i]에 할당합니다.<br/>

도서 정보 출력
```java
System.out.println("\n도서 정보 출력 ");
for(Book book : books) {
    book.printInfo();
    System.out.println("\n=== === === === ");
}
```
for-each 반복문을 통해 books 배열의 각 Book 객체의 printInfo 메서드를 호출하여 도서 정보를 출력.<br/>

Scanner 종료<br/>
Scanner 사용 후 sc.close()로 닫음.

