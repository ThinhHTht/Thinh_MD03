package ra.business.implement;

import ra.business.design.IOData;
import ra.business.entity.Mark;
import ra.business.entity.Student;
import ra.business.entity.Subject;

import java.util.Scanner;

import static ra.presentation.SchoolManagement.*;

public class MarkImpl extends Mark implements IOData {
    @Override
    public void inputData() {
        setMarkId(generateMarkId());
        setStudent(selectStudent(scanner));
        setSubject(selectSubject(scanner));
        setPoint(inputPoint(scanner));
    }
    public static int generateMarkId(){
        if(listMarks.isEmpty()){
            return 1;
        }else {
            return listMarks.stream().mapToInt(Mark::getMarkId).max().orElse(0) +1;
        }
    }
    public static Student selectStudent(Scanner scanner){
        if(listStudents.isEmpty()){
            MarkImpl.inputData();
        }else {
            System.out.println("Hãy chọn 1 trong những sinh viên sau:");
            listStudents.stream().forEach(student -> System.out.printf("Mã sinh viên: %d: \n", student.getStudentId()));
            do {
                int idSelect = Integer.parseInt(scanner.nextLine());
                if(listStudents.stream().anyMatch(student -> student.getStudentId() == idSelect)){
                    int indexSelect = findStudentIndexById(idSelect);
                    return listStudents.get(indexSelect);
                } else {
                    System.err.println("Mã sinh viên này không tồn tại, vui lòng nhập lại!");
                }
            }while (true);
        }
        return null;
    }

    public static int findStudentIndexById(int id){
        for (int i = 0; i < listStudents.size(); i++) {
            if(listStudents.get(i).getStudentId() == id){
                return i;
            }
        }
        return -1;
    }
    public static Subject selectSubject(Scanner scanner){
        do {
            if (listSubjects.isEmpty()) {
                subjectImp.inputData();
            } else {
                System.out.println("Hãy chọn 1 trong những môn học sau:");
                listSubjects.stream().forEach(subject -> System.out.printf("Mã môn học: %s: \n", subject.getSubjectId()));
                do {
                    String idSelect = scanner.nextLine();
                    if (listSubjects.stream().anyMatch(subject -> subject.getSubjectId().equals(idSelect))) {
                        int indexSelect = findSubjectIndexById(idSelect);
                        return listSubjects.get(indexSelect);
                    } else {
                        System.err.println("Mã môn học này không tồn tại, vui lòng nhập lại!");
                    }
                } while (true);
            }
            return null;
        } while (true);
    }

    public static int findSubjectIndexById(String id){
        for (int i = 0; i < listSubjects.size(); i++) {
            if(listSubjects.get(i).getSubjectId().equals(id)){
                return i;
            }
        }
        return -1;
    }

    public static double inputPoint(Scanner scanner){
        System.out.println("Hãy nhập điểm số:");
        do {
            try{
                double pointInput = Double.parseDouble(scanner.nextLine());
                if(pointInput >= 0 && pointInput <= 10){
                    return pointInput;
                }else {
                    System.err.println("Vui lòng nhập từ 0-10");
                }
            }catch (NumberFormatException ex){
                System.err.println("Vui lòng nhập bằng số thực!");
            }
        }while (true);
    }
    @Override
    public void displayData() {
        System.out.printf("ID điểm: %d - Tên học sinh: %s - Tên môn học: %s - Điểm số: %.2f\n", getMarkId(), getStudent().getStudentName(), getSubject().getSubjectName(), getPoint());
    }
}
