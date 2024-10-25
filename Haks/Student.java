package Exam;

public class Student {
	// 인스턴스 변수 생성
	 private String name; 		// 학생 이름
	 private int stNo; 			// 학번
	 private String className;	// 학과
	 private String phone;    	// 전화번호
	 private String exit;     	// 이전 화면 
 
	 
 	public Student(String name, int stNo, String className, String phone) {
		 this.name = name;
		 this.stNo = stNo;
		 this.className = className;
		 this.phone = phone;
	 }
 	
 	public void printStInfo() {
		System.out.println("이름 : " + name);
		System.out.println("학번 : " + stNo);
		System.out.println("학과 : " + className);
		System.out.println("전화번호 : " + phone);
	}
 	
 	// 조회 기능을 위한 search method
 	public String searchName() { return name; }
 	public int searchNo() { return stNo; }
 	public String searchClass() { return className; }
 	public String searchPhone() { return phone; } 
 	
 	// 수정 기능을 위한 update method
 	public void updateName(String name) { this.name = name; }
 	public void updateClass(String className) { this.className = className;}
 	public void updatePhone(String phone) { this.phone = phone; } 
	 
	 
}
