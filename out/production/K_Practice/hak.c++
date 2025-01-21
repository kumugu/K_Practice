#include <iostream>
#include <vector>
#include <string>

using namespace std;

// 학생 클래스
class Student {
private:
    string name;
    int age;
    string studentID;

public:
    // 생성자
    Student(string n, int a, string id) : name(n), age(a), studentID(id) {}

    // 학생 정보 출력
    void displayInfo() const {
        cout << "Name: " << name << ", Age: " << age << ", Student ID: " << studentID << endl;
    }

    // 이름 반환
    string getName() const {
        return name;
    }

    // 학번 반환
    string getStudentID() const {
        return studentID;
    }
};

// 학생 관리 시스템 클래스
class StudentManagementSystem {
private:
    vector<Student> students;

public:
    // 학생 추가
    void addStudent(const Student& student) {
        students.push_back(student);
        cout << "Student \"" << student.getName() << "\" added successfully." << endl;
    }

    // 학생 삭제
    void removeStudent(const string& studentID) {
        for (auto it = students.begin(); it != students.end(); ++it) {
            if (it->getStudentID() == studentID) {
                cout << "Student \"" << it->getName() << "\" removed successfully." << endl;
                students.erase(it);
                return;
            }
        }
        cout << "Student with ID \"" << studentID << "\" not found." << endl;
    }

    // 학생 검색
    void searchStudent(const string& studentID) const {
        for (const auto& student : students) {
            if (student.getStudentID() == studentID) {
                cout << "Student found:" << endl;
                student.displayInfo();
                return;
            }
        }
        cout << "Student with ID \"" << studentID << "\" not found." << endl;
    }

    // 모든 학생 출력
    void displayAllStudents() const {
        if (students.empty()) {
            cout << "No students in the system." << endl;
        } else {
            cout << "All Students:" << endl;
            for (const auto& student : students) {
                student.displayInfo();
            }
        }
    }
};

// 메인 함수
int main() {
    StudentManagementSystem sms;

    while (true) {
        cout << "\nStudent Management System" << endl;
        cout << "1. Add Student" << endl;
        cout << "2. Remove Student" << endl;
        cout << "3. Search Student" << endl;
        cout << "4. Display All Students" << endl;
        cout << "5. Exit" << endl;
        cout << "Enter your choice: ";
        int choice;
        cin >> choice;

        if (choice == 1) {
            string name, studentID;
            int age;
            cout << "Enter student name: ";
            cin.ignore(); // 버퍼 비우기
            getline(cin, name);
            cout << "Enter student age: ";
            cin >> age;
            cout << "Enter student ID: ";
            cin >> studentID;
            sms.addStudent(Student(name, age, studentID));
        } else if (choice == 2) {
            string studentID;
            cout << "Enter student ID to remove: ";
            cin >> studentID;
            sms.removeStudent(studentID);
        } else if (choice == 3) {
            string studentID;
            cout << "Enter student ID to search: ";
            cin >> studentID;
            sms.searchStudent(studentID);
        } else if (choice == 4) {
            sms.displayAllStudents();
        } else if (choice == 5) {
            cout << "Exiting program. Goodbye!" << endl;
            break;
        } else {
            cout << "Invalid choice. Please try again." << endl;
        }
    }

    return 0;
}
