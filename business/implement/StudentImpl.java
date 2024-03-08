package ra.business.implement;

import ra.business.design.IOData;
import ra.business.entity.Student;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.SimpleFormatter;
import java.util.regex.Pattern;

import static ra.presentation.SchoolManagement.*;

public class StudentImpl extends Student implements IOData {
    @Override
    public void inputData() {
        this.setStudentId(generateStudentId());
        this.setStudentName(inputStudentName(scanner));
        this.setBirthDay(inputBirthDay(scanner));
        this.setAddress(inputAddress(scanner));
        this.setGender(inputGender(scanner));
        this.setPhone(inputPhoneNumber(scanner));
    }

    public static int generateStudentId(){
        if(listStudents.isEmpty()){
            return 1;
        }else {
            return listStudents.stream().mapToInt(Student::getStudentId).max().orElse(0) +1;
        }
    }
    public static String inputStudentName(Scanner scanner){
        System.out.println("Hãy nhập tên học sinh:");
        do {
            String nameInput = scanner.nextLine();
            if(nameInput.trim().isEmpty()){
                System.err.println("Không được để trống tên sinh viên!");
            } else {
                return nameInput;
            }
        }while (true);
    }

    public static Date inputBirthDay(Scanner scanner){
        System.out.println("Hãy nhập ngày sinh của sinh viên:");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        do {
            try{
                Date dateInput = sdf.parse(scanner.nextLine());
                return dateInput;
            }catch (Exception ex){
                System.err.println("Vui lòng nhập theo định dạng yyyy/MM/dd");
            }
        }while (true);
    }

    public static String inputAddress(Scanner scanner){
        System.out.println("Hãy nhập địa chỉ của sinh viên:");
        do {
            String addressInput = scanner.nextLine();
            if(addressInput.trim().isEmpty()){
                System.err.println("Không được để trống địa chỉ!");
            }else {
                return addressInput;
            }
        }while (true);
    }

    public static Boolean inputGender(Scanner scanner){
        System.out.println("Hãy nhập giới tính của sinh viên:");
        do {
            String inputGender = scanner.nextLine();
            if(Pattern.matches("(true|false)", inputGender)){
                return Boolean.parseBoolean(inputGender);
            }else {
                System.err.println("Vui lòng nhập true hoặc false!");
            }
        }while (true);
    }

    public static String inputPhoneNumber(Scanner scanner){
        System.out.println("Hãy nhâp số điện thoại của sinh viên:");
        do {
            String phoneInput = scanner.nextLine();
            if(listStudents.stream().anyMatch(student -> student.getPhone().equals(phoneInput))){
                System.err.println("Số điện thoại bị trùng, vui lòng nhập lại!");
            } else {
            if(Pattern.matches("^0\\d{9,10}$", phoneInput)){
                return phoneInput;
            } else {
                System.err.println("Vui lòng nhập theo định dạng: 10 hoặc 11 số , bắt đầu bằng số 0");
            }
            }
        }while (true);
    }
    @Override
    public void displayData() {
        System.out.printf("Mã sinh viên: %d - Tên sinh viên: %s - Ngày sinh: %s\n", getStudentId(), getStudentName(), getBirthDay());
        System.out.printf("Địa chỉ: %s - Giới tính: %s - Số điện thoại: %s\n", getAddress(), isGender()? "Nam" : "Nữ", getPhone());
    }
}
