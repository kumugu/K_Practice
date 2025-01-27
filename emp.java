import java.util.ArrayList;
import java.util.Scanner;

// 직원 클래스
class Employee {
    private int id;
    private String name;
    private String department;
    private double salary;

    public Employee(int id, String name, String department, double salary) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.salary = salary;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDepartment() {
        return department;
    }

    public double getSalary() {
        return salary;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + name + ", Department: " + department + ", Salary: " + salary;
    }
}

// 직원 관리 시스템 클래스
class EmployeeManagementSystem {
    private ArrayList<Employee> employees;
    private int nextId;

    public EmployeeManagementSystem() {
        employees = new ArrayList<>();
        nextId = 1; // 직원 ID 자동 증가
    }

    public void addEmployee(String name, String department, double salary) {
        Employee employee = new Employee(nextId++, name, department, salary);
        employees.add(employee);
        System.out.println("Employee added successfully!");
    }

    public void removeEmployee(int id) {
        Employee toRemove = null;
        for (Employee e : employees) {
            if (e.getId() == id) {
                toRemove = e;
                break;
            }
        }
        if (toRemove != null) {
            employees.remove(toRemove);
            System.out.println("Employee removed successfully!");
        } else {
            System.out.println("Employee with ID " + id + " not found.");
        }
    }

    public void listEmployees() {
        if (employees.isEmpty()) {
            System.out.println("No employees found.");
        } else {
            System.out.println("Employee List:");
            for (Employee e : employees) {
                System.out.println(e);
            }
        }
    }

    public void updateEmployee(int id, String newDepartment, double newSalary) {
        for (Employee e : employees) {
            if (e.getId() == id) {
                e.setDepartment(newDepartment);
                e.setSalary(newSalary);
                System.out.println("Employee updated successfully!");
                return;
            }
        }
        System.out.println("Employee with ID " + id + " not found.");
    }

    public void searchEmployee(int id) {
        for (Employee e : employees) {
            if (e.getId() == id) {
                System.out.println("Employee found: " + e);
                return;
            }
        }
        System.out.println("Employee with ID " + id + " not found.");
    }
}

// 메인 클래스
public class Main {
    public static void main(String[] args) {
        EmployeeManagementSystem system = new EmployeeManagementSystem();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- Employee Management System ---");
            System.out.println("1. Add Employee");
            System.out.println("2. Remove Employee");
            System.out.println("3. List Employees");
            System.out.println("4. Update Employee");
            System.out.println("5. Search Employee");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // 버퍼 비우기

            switch (choice) {
                case 1:
                    System.out.print("Enter name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter department: ");
                    String department = scanner.nextLine();
                    System.out.print("Enter salary: ");
                    double salary = scanner.nextDouble();
                    system.addEmployee(name, department, salary);
                    break;

                case 2:
                    System.out.print("Enter employee ID to remove: ");
                    int idToRemove = scanner.nextInt();
                    system.removeEmployee(idToRemove);
                    break;

                case 3:
                    system.listEmployees();
                    break;

                case 4:
                    System.out.print("Enter employee ID to update: ");
                    int idToUpdate = scanner.nextInt();
                    scanner.nextLine(); // 버퍼 비우기
                    System.out.print("Enter new department: ");
                    String newDepartment = scanner.nextLine();
                    System.out.print("Enter new salary: ");
                    double newSalary = scanner.nextDouble();
                    system.updateEmployee(idToUpdate, newDepartment, newSalary);
                    break;

                case 5:
                    System.out.print("Enter employee ID to search: ");
                    int idToSearch = scanner.nextInt();
                    system.searchEmployee(idToSearch);
                    break;

                case 6:
                    System.out.println("Exiting the system. Goodbye!");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
