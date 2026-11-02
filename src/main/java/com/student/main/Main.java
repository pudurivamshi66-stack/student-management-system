package com.student.main;

import com.student.dao.StudentDAO;
import com.student.model.Student;

import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner sc = new Scanner(System.in);
    private static final StudentDAO dao = new StudentDAO();

    public static void main(String[] args) {
        while (true) {
            printMenu();
            int choice = readInt("Enter choice: ");

            switch (choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    viewStudents();
                    break;
                case 3:
                    updateStudent();
                    break;
                case 4:
                    deleteStudent();
                    break;
                case 5:
                    System.out.println("Total students: " + dao.getCount());
                    break;
                case 6:
                    searchById();
                    break;
                case 7:
                    searchByName();
                    break;
                case 8:
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Enter a number between 1 and 8.");
            }
        }
    }

    private static void printMenu() {
        System.out.println();
        System.out.println("===== Student Management System =====");
        System.out.println("1. Add Student");
        System.out.println("2. View All Students");
        System.out.println("3. Update Student");
        System.out.println("4. Delete Student");
        System.out.println("5. Total Students Count");
        System.out.println("6. Search Student by ID");
        System.out.println("7. Search Student by Name");
        System.out.println("8. Exit");
    }

    private static void addStudent() {
        String name = readText("Name: ");
        String email = readText("Email: ");
        String course = readText("Course: ");

        if (dao.addStudent(new Student(name, email, course))) {
            System.out.println("Student added successfully!");
        } else {
            System.out.println("Failed to add student.");
        }
    }

    private static void viewStudents() {
        List<Student> students = dao.getAllStudents();
        if (students.isEmpty()) {
            System.out.println("No students found.");
            return;
        }
        System.out.println("ID | Name | Email | Course");
        for (Student s : students) {
            System.out.println(s);
        }
    }

    private static void updateStudent() {
        int id = readInt("Enter student ID to update: ");
        String name = readText("New name: ");
        String email = readText("New email: ");
        String course = readText("New course: ");

        if (dao.updateStudent(new Student(id, name, email, course))) {
            System.out.println("Student updated successfully!");
        } else {
            System.out.println("Update failed. Check the ID.");
        }
    }

    private static void deleteStudent() {
        int id = readInt("Enter student ID to delete: ");
        if (dao.deleteStudent(id)) {
            System.out.println("Student deleted successfully!");
        } else {
            System.out.println("Delete failed. Check the ID.");
        }
    }

    private static void searchById() {
        int id = readInt("Enter student ID: ");
        Student s = dao.getStudentById(id);
        if (s == null) {
            System.out.println("No student found with ID " + id);
        } else {
            System.out.println("----- Student Details -----");
            System.out.println("ID     : " + s.getId());
            System.out.println("Name   : " + s.getName());
            System.out.println("Email  : " + s.getEmail());
            System.out.println("Course : " + s.getCourse());
        }
    }

    private static void searchByName() {
        String keyword = readText("Enter name to search: ");
        List<Student> results = dao.searchByName(keyword);
        if (results.isEmpty()) {
            System.out.println("No students found with that name.");
            return;
        }
        System.out.println("ID | Name | Email | Course");
        for (Student s : results) {
            System.out.println(s);
        }
    }

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = sc.nextLine().trim();
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private static String readText(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = sc.nextLine().trim();
            if (!line.isEmpty()) {
                return line;
            }
            System.out.println("This field cannot be empty.");
        }
    }
}