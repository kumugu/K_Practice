#include <iostream>
#include <vector>
#include <string>

using namespace std;

// Book 클래스 정의
class Book {
private:
    string title;
    string author;
    int year;

public:
    // 생성자
    Book(string t, string a, int y) : title(t), author(a), year(y) {}

    // 도서 정보 출력
    void displayInfo() {
        cout << "Title: " << title << ", Author: " << author << ", Year: " << year << endl;
    }

    // 제목 반환
    string getTitle() {
        return title;
    }
};

// Library 클래스 정의
class Library {
private:
    vector<Book> books;

public:
    // 도서 추가
    void addBook(const Book& book) {
        books.push_back(book);
        cout << "Book \"" << book.getTitle() << "\" added to the library." << endl;
    }

    // 도서 검색
    void searchBook(const string& title) {
        bool found = false;
        for (const auto& book : books) {
            if (book.getTitle() == title) {
                cout << "Book found:" << endl;
                book.displayInfo();
                found = true;
                break;
            }
        }
        if (!found) {
            cout << "Book \"" << title << "\" not found in the library." << endl;
        }
    }

    // 도서 목록 출력
    void displayAllBooks() {
        if (books.empty()) {
            cout << "No books in the library." << endl;
        } else {
            cout << "Books in the library:" << endl;
            for (const auto& book : books) {
                book.displayInfo();
            }
        }
    }
};

// 메인 함수
int main() {
    Library library;

    while (true) {
        cout << "\nLibrary Management System" << endl;
        cout << "1. Add Book" << endl;
        cout << "2. Search Book" << endl;
        cout << "3. Display All Books" << endl;
        cout << "4. Exit" << endl;
        cout << "Enter your choice: ";
        int choice;
        cin >> choice;

        if (choice == 1) {
            string title, author;
            int year;
            cout << "Enter book title: ";
            cin.ignore(); // 버퍼 비우기
            getline(cin, title);
            cout << "Enter book author: ";
            getline(cin, author);
            cout << "Enter year of publication: ";
            cin >> year;
            library.addBook(Book(title, author, year));
        } else if (choice == 2) {
            string title;
            cout << "Enter book title to search: ";
            cin.ignore(); // 버퍼 비우기
            getline(cin, title);
            library.searchBook(title);
        } else if (choice == 3) {
            library.displayAllBooks();
        } else if (choice == 4) {
            cout << "Exiting program. Goodbye!" << endl;
            break;
        } else {
            cout << "Invalid choice. Please try again." << endl;
        }
    }

    return 0;
}
