package Exam;

import java.util.Scanner;
import java.util.ArrayList;

public class Home {
	static ArrayList<Student> studentList = new ArrayList<>();

	public static void stReg(){		// 1.학생 등록
		Scanner sc = new Scanner(System.in);
		
		System.out.println("=== === 1. 학생 등록 === ===");
		System.out.println();
		System.out.print("학생 이름을 입력 하세요 : ");
		String name = sc.next();
		System.out.print("학번을 입력 하세요 : ");
		int stNo = sc.nextInt();
		System.out.print("학과를 입력 하세요 : ");
		String className = sc.next();
		System.out.print("전화번호를 입력 하세요 : ");
		String phone = sc.next();
		
		Student student = new Student(name, stNo, className, phone);
		studentList.add(student);
		
		System.out.println("=== === 입력된 내용 === ===");
		student.printStInfo();  // Student 클래스에서 printStInfo 메서드 호출
		System.out.println("=== === === === === ===");
		System.out.println();
		
		
		System.out.println("이전 화면으로 가려면 'Y', 종료하려면 'N'을 누르세요... ...");
		System.out.println("다시 이용하시려면 아무키나 입력하세요.. .. ");
		String choice = sc.next();
		
		if(choice.equalsIgnoreCase("Y")) {
			main(new String[] {}); 
    	} else if(choice.equalsIgnoreCase("N")){
    		System.out.println("프로그램을 종료합니다.");
    		System.exit(0);
    	} else {
    		System.out.println("해당 메뉴를 다시 시작합니다.");
    		stReg();
    	}
	
		
	}
	public static void ttPrint(){ 	// 2.전체 출력
		Scanner sc = new Scanner(System.in);
		System.out.println("=== === 전체 학생 목록 === ===");
			for(Student student : studentList) {
				student.printStInfo();
				System.out.println("=== === === === === ===");
			}
			System.out.println("이전 화면으로 가려면 'Y', 종료하려면 'N'을 누르세요... ...");
			System.out.println("다시 이용하시려면 아무키나 입력하세요.. .. ");
			String choice = sc.next();
			
			if(choice.equalsIgnoreCase("Y")) {
				main(new String[] {}); 
	    	} else if(choice.equalsIgnoreCase("N")){
	    		System.out.println("프로그램을 종료합니다.");
	    		System.exit(0);
	    	} else {
	    		System.out.println("해당 메뉴를 다시 시작합니다.");
	    		ttPrint();
	    	}
	}
	
	public static void stInfo(){	// 3.학생 조회
		Scanner sc = new Scanner(System.in);
		boolean exit = false; // 루프 종료를 위한 플래그
		
		while(!exit) {
			System.out.println("조회할 옵션을 선택하세요 : ");
			System.out.println("1. 이름 조회");
			System.out.println("2. 학번 조회");
			System.out.println("3. 학과 조회");
			System.out.println("4. 전화번호 조회");
			System.out.println("=== === === === === ===");
		
			boolean found = false;
			int option = sc.nextInt();
			
			switch(option) {
				case 1 : 
					System.out.print("조회할 이름을 입력하세요 :");
					String name = sc.next();
					for(Student student : studentList) {
						if(student.searchName().equals(name)) {
							student.printStInfo();
							found = true;
							break;
						}
					}
					break;
				case 2 : 
					System.out.print("조회할 학번을 입력하세요 :");
					int stNo = sc.nextInt();
					for(Student student : studentList) {
						if(student.searchNo() == stNo) {
							student.printStInfo();
							found = true;
							break;
						}
					}
					break;
				case 3 : 
					System.out.print("조회할 학과을 입력하세요 :");
					String className = sc.next();
					for(Student student : studentList) {
						if(student.searchClass().equals(className)) {
							student.printStInfo();
							found = true;
						}
					}
					break;
				case 4 : 
					System.out.print("조회할 전화번호를 입력하세요 :");
					String phone = sc.next();
					for(Student student : studentList) {
						if(student.searchPhone().equals(phone)) {
							student.printStInfo();
							found = true;
							break;
						}
					}
					break;
				default:
					System.out.println("잘못된 입력입니다.");
					break;
			}
		
			if(!found) {
				System.out.println("해당 정보를 찾을 수 없습니다.");
			}
		
			System.out.println("다시 조회하시겠습니까? (Y/N)");
			String choice = sc.next();
			if(!choice.equalsIgnoreCase("Y")) {
				exit = true;
			}
		}
		System.out.println("이전 화면으로 가려면 'Y', 종료하려면 'N'을 누르세요... ...");
		System.out.println("다시 이용하시려면 아무키나 입력하세요.. .. ");
		String choice = sc.next();
		
		if(choice.equalsIgnoreCase("Y")) {
			main(new String[] {}); 
    	} else if(choice.equalsIgnoreCase("N")){
    		System.out.println("프로그램을 종료합니다.");
    		System.exit(0);
    	} else {
    		System.out.println("해당 메뉴를 다시 시작합니다.");
    		stInfo();
    	}
	}
	

	public static void stModi(){	
		// 4.정보 수정
		Scanner sc = new Scanner(System.in);
		
		boolean exit = false; // 루프 종료를 위한 플래그
		
		while(!exit) {
			System.out.print("수정할 학생의 학번을 입력 하세요 : ");
			int stNo = sc.nextInt();
			
			Student foundStudent = null;
			for(Student student : studentList) {
				if (student.searchNo() == stNo) {
					foundStudent = student;
					break;
				}
			}
			
			if (foundStudent == null) {
				System.out.println("해당 학번의 번호를 찾을 수 없습니다.");
				return;
			}
			
		    System.out.println("수정할 정보를 선택하세요: ");
		    System.out.println(" === === === === === === ");
		    System.out.println("1. 이름 수정");
		    System.out.println("2. 학과 수정");
		    System.out.println("3. 전화번호 수정");
		    System.out.println(" === === === === === === ");
		    int option = sc.nextInt();
		    
		    switch (option) {
		    case 1:
		    	System.out.print("새로운 이름을 입력 하세요 : ");
		    	String newName = sc.next();
		    	foundStudent.updateName(newName);
		    	break;
		    case 2:
		    	System.out.print("새로운 학과를 입력 하세요 : ");
		    	String newClass = sc.next();
		    	foundStudent.updateClass(newClass);
		    	break;
		    case 3:
		    	System.out.print("새로운 전화번호를 입력 하세요 : ");
		    	String newPhone = sc.next();
		    	foundStudent.updatePhone(newPhone);
		    	break;
		    default:
		    	System.out.print("잘못된 입력입니다.");
		    	break;
		    }
	    
		    System.out.println("수정된 정보 ");
		    foundStudent.printStInfo();
		    
			System.out.println("추가로 수하시겠습니까? (Y/N)");
			String choice = sc.next();
			if(!choice.equalsIgnoreCase("Y")) {
				exit = true;
			}
	
		}
		System.out.println("이전 화면으로 가려면 'Y', 종료하려면 'N'을 누르세요... ...");
		System.out.println("다시 이용하시려면 아무키나 입력하세요.. .. ");
		String choice = sc.next();
		
		if(choice.equalsIgnoreCase("Y")) {
			main(new String[] {}); 
    	} else if(choice.equalsIgnoreCase("N")){
    		System.out.println("프로그램을 종료합니다.");
    		System.exit(0);
    	} else {
    		System.out.println("해당 메뉴를 다시 시작합니다.");
    		stModi();
    	}
		
	}
	
	
	public static void exit(){		// 5.프로그램 종료
		Scanner sc = new Scanner(System.in);
		System.out.println("이전 화면으로 가려면 'Y', 종료하려면 'N'을 누르세요... ...");
		String choice = sc.next();
		if(choice.equalsIgnoreCase("Y")) {
			main(new String[] {}); 
    	} else if(choice.equalsIgnoreCase("N")){
    		System.out.println("프로그램을 종료합니다.");
    		System.out.println("=== === === === === === === ");
    		System.exit(0);
    	} else {
    		System.out.println("이전 화면으로 가려면 'Y', 종료하려면 'N'을 누르세요... ...");
    		exit();
    	}
		
	}
	public static void home(){		// Home 화면으로
		
		// 1. 메뉴 표시
		System.out.println("=== === === 메뉴 === === ===");
		System.out.println("이용 할 메뉴 번호를 입력 하세요...");
		System.out.println("1. 학생 등록 ");
		System.out.println("2. 전체 출력 ");
		System.out.println("3. 학생 조회 ");
		System.out.println("4. 정보 수정 ");
		System.out.println("5. 프로그램 종료");
		System.out.println("=== === === === === === ===");
		
	}
	
	public static void printMainPage() {
	    System.out.println( "::::::::::::::::::::::::::::학사 관리 프로그램::::::::::::::::::::::::::::::");
	    System.out.println( "::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
	    System.out.println();
	    System.out.println();
        System.out.println("			  학사 관리 프로그램");
        System.out.println("			  개발자 : [김 금 구]");
        System.out.println("			  버전 : 1.0");
	    System.out.println();
	    System.out.println();
        System.out.println("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
        System.out.println("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::" );
	}


	public static void main(String[] args) {
	
		printMainPage();
		
		Scanner sc = new Scanner(System.in);
		while(true) {	
			
		// 1. 메뉴 표시
		System.out.println("=== === === 메뉴 === === ===");
		System.out.println("이용 할 메뉴 번호를 입력 하세요...");
		System.out.println("1. 학생 등록 ");
		System.out.println("2. 전체 출력 ");
		System.out.println("3. 학생 조회 ");
		System.out.println("4. 정보 수정 ");
		System.out.println("5. 프로그램 종료");
		System.out.println("=== === === === === === ===");
		

		int menuSelect = sc.nextInt();
		
		// 3. switch를 사용하여 각 메뉴 선택 기능 추가
		
		switch (menuSelect) {
		case 1 : stReg(); 	// 1.학생 등록
			break;
		case 2 : ttPrint();  // 2.전체 출력
			break;
		case 3 : stInfo();   // 3.학생 조회
			break;
		case 4 : stModi();   // 4.정보 수정
			break;
		case 5 : exit(); 	// 5.프로그램 종료 
			break;
		default:
			System.out.println("잘못 입력하셨습니다.");
			break;
			}
		}
	}
}
