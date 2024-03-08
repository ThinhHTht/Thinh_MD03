package ra.presentation;

import ra.business.entity.Mark;
import ra.business.entity.Student;
import ra.business.entity.Subject;
import ra.business.implement.MarkImpl;
import ra.business.implement.StudentImpl;
import ra.business.implement.SubjectImpl;

import java.sql.SQLOutput;
import java.sql.Savepoint;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static ra.business.implement.MarkImpl.findStudentIndexById;
import static ra.business.implement.StudentImpl.*;

public class SchoolManagement {
//    public static List<Student> listStudents = new ArrayList<>();
//    public static List<Subject> listSubjects = new ArrayList<>();
//    public static List<Mark> listMarks = new ArrayList<>();
    public static Scanner scanner = new Scanner(System.in);
    public static List<StudentImpl> listStudents = new ArrayList<>();

    public static List<SubjectImpl> listSubjects = new ArrayList<>();

    public static List <MarkImpl> listMarks = new ArrayList<>();
    static int choice;
    public static void main(String[] args) {
    do {
        System.out.println("*************SCHOOL - MANAGEMENT***************");
        System.out.println("1. Quản lý học sinh");
        System.out.println("2. Quản lý môn học");
        System.out.println("3. Quản lý điểm thi");
        System.out.println("4. Thoát");
        System.out.print("Lựa chọn của bạn là:");
        inputChoice(scanner);
        switch (choice){
            case 1:
                displayStudentManagementMenu();
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                System.exit(0);
            default:
                System.err.println("Vui lòng chọn từ 1-4");

        }

    }while (true);
    }

    public static int inputChoice(Scanner scanner){
        do {
            try{
                choice = Integer.parseInt(scanner.nextLine());
                return choice;
            }catch (NumberFormatException ex){
                System.err.println("Vui lòng nhập bằng số nguyên!");
            }
        }while (true);
    }

    public static void displayStudentManagementMenu(){
        boolean isExit = true;
        do {
            System.out.println("**************STUDENT-MANAGEMENT**************");
            System.out.println("1. Thêm mới học sinh");
            System.out.println("2. Hiển thị danh sách tất cả học sinh đã lưu trữ");
            System.out.println("3. Thay đổi thông tin học sinh");
            System.out.println("4. Xóa học sinh (kiểm tra xem nếu sinh viên có điểm thi thì không xóa được)");
            System.out.println("5. Thoát");
            System.out.print("Lựa chọn của bạn là:");
            inputChoice(scanner);
            switch (choice){
                case 1:
                    addStudent(scanner);
                    break;
                case 2:
                    showListStudents();
                    break;
                case 3:
                    updateStudent(scanner);
                    break;
                case 4:
                    deleteStudentById(scanner);
                    break;
                case 5:
                    isExit = false;
                    break;
                default:
                    System.err.println("Vui lòng nhập từ 1-5");
            }

        }while (isExit);
    }

    public static void addStudent(Scanner scanner){
        System.out.println("Hãy nhập số học sinh muốn thêm:");
        inputChoice(scanner);
        for (int i = 0; i < choice; i++) {
            StudentImpl newStudent = new StudentImpl();
            newStudent.inputData();
            listStudents.add(newStudent);
        }
    }

    public static void showListStudents(){
        listStudents.stream().forEach(student -> student.displayData());
    }
    public static void updateStudent(Scanner scanner){
        System.out.println("Hãy nhập mã sinh viên muốn update");
        inputChoice(scanner);
        int indexUpdate = findStudentIndexById(choice);
        if(indexUpdate >= 0){
            boolean isExit = true;
            do {
                System.out.println("Hãy chọn mục muốn update:");
                System.out.println("1. Cập nhật tên sinh viên");
                System.out.println("2. Cập nhật ngày sinh");
                System.out.println("3. Cập nhật địa chỉ");
                System.out.println("4. Cập nhật giới tính");
                System.out.println("5. Cập nhật số điện thoại");
                System.out.println("6. Thoát khỏi cập nhật");
                System.out.print("Lựa chọn của bạn là:");
                inputChoice(scanner);
                switch (choice){
                    case 1:
                        listStudents.get(indexUpdate).setStudentName(inputStudentName(scanner));
                        break;
                    case 2:
                        listStudents.get(indexUpdate).setBirthDay(inputBirthDay(scanner));
                        break;
                    case 3:
                        listStudents.get(indexUpdate).setAddress(inputAddress(scanner));
                        break;
                    case 4:
                        listStudents.get(indexUpdate).setGender(!listStudents.get(indexUpdate).isGender());
                        System.out.println("Đã cập nhật xong giới tính");
                        break;
                    case 5:
                        listStudents.get(indexUpdate).setPhone(inputPhoneNumber(scanner));
                        break;
                    case 6:
                        isExit = false;
                        break;
                    default:
                        System.err.println("Vui lòng nhập từ 1-6");
                }
            }while (isExit);
        } else {
            System.err.println("Không có sinh viên nào có mã như đã nhập!");
        }
    }

    public static void deleteStudentById(Scanner scanner){
        System.out.println("Hãy nhập mã sinh viên muốn xóa:");
        inputChoice(scanner);
        int indexDelete = findStudentIndexById(choice);
        if(indexDelete >= 0){
            if(listMarks.stream().anyMatch(mark -> mark.getStudent().getStudentId() == choice)){
                System.err.println("Sinh viên này có điểm nên không thể xóa!");
            }else {
                listStudents.remove(indexDelete);
            }
        } else {
            System.err.println("Không tồn tại sinh viên này!");
        }
    }

}
