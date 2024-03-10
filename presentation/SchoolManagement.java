package ra.presentation;

import ra.business.entity.Mark;
import ra.business.entity.Student;
import ra.business.entity.Subject;
import ra.business.implement.MarkImpl;
import ra.business.implement.StudentImpl;
import ra.business.implement.SubjectImpl;

import java.io.*;
import java.sql.SQLOutput;
import java.sql.Savepoint;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import static ra.business.implement.MarkImpl.*;
import static ra.business.implement.StudentImpl.*;
import static ra.business.implement.SubjectImpl.inputSubjectName;

public class SchoolManagement {
    //    public static List<Student> listStudents = new ArrayList<>();
//    public static List<Subject> listSubjects = new ArrayList<>();
//    public static List<Mark> listMarks = new ArrayList<>();
    public static Scanner scanner = new Scanner(System.in);
    public static List<StudentImpl> listStudents = new ArrayList<>();

    public static List<SubjectImpl> listSubjects = new ArrayList<>() ;

    public static List<MarkImpl> listMarks = new ArrayList<>();
    static int choice;

    public static void main(String[] args) {
//        readDataFromFile();
        do {
            System.out.println("*************SCHOOL - MANAGEMENT***************");
            System.out.println("1. Quản lý học sinh");
            System.out.println("2. Quản lý môn học");
            System.out.println("3. Quản lý điểm thi");
            System.out.println("4. Thoát");
            System.out.print("Lựa chọn của bạn là:");
            inputChoice(scanner);
            switch (choice) {
                case 1:
                    displayStudentManagementMenu();
                    break;
                case 2:
                    displaySubjectManagementMenu();
                    break;
                case 3:
                    displayMarkManagementMenu();
                    break;
                case 4:
//                    writeDataToFile();
                    System.exit(0);
                default:
                    System.err.println("Vui lòng chọn từ 1-4");

            }

        } while (true);
    }

    public static int inputChoice(Scanner scanner) {
        do {
            try {
                choice = Integer.parseInt(scanner.nextLine());
                return choice;
            } catch (NumberFormatException ex) {
                System.err.println("Vui lòng nhập bằng số nguyên!");
            }
        } while (true);
    }

    public static void displayStudentManagementMenu() {
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
            switch (choice) {
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
//                    writeDataToFile();
                    isExit = false;
                    break;
                default:
                    System.err.println("Vui lòng nhập từ 1-5");
            }

        } while (isExit);
    }

    public static void addStudent(Scanner scanner) {
        System.out.println("Hãy nhập số học sinh muốn thêm:");
        inputChoice(scanner);
        for (int i = 0; i < choice; i++) {
            StudentImpl newStudent = new StudentImpl();
            newStudent.inputData();
            listStudents.add(newStudent);
        }
    }

    public static void showListStudents() {
        listStudents.stream().forEach(student -> student.displayData());
    }

    public static void updateStudent(Scanner scanner) {
        System.out.println("Hãy nhập mã sinh viên muốn update");
        inputChoice(scanner);
        int indexUpdate = findStudentIndexById(choice);
        if (indexUpdate >= 0) {
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
                switch (choice) {
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
            } while (isExit);
        } else {
            System.err.println("Không có sinh viên nào có mã như đã nhập!");
        }
    }

    public static void deleteStudentById(Scanner scanner) {
        System.out.println("Hãy nhập mã sinh viên muốn xóa:");
        inputChoice(scanner);
        int indexDelete = findStudentIndexById(choice);
        if (indexDelete >= 0) {
            if (listMarks.stream().anyMatch(mark -> mark.getStudent().getStudentId() == choice)) {
                System.err.println("Sinh viên này có điểm nên không thể xóa!");
            } else {
                listStudents.remove(indexDelete);
            }
        } else {
            System.err.println("Không tồn tại sinh viên này!");
        }
    }

    public static void displaySubjectManagementMenu() {
        boolean isExit = true;
        do {
            System.out.println("**************SUBJECT-MANAGEMENT****************");
            System.out.println("1. Thêm mới môn học");
            System.out.println("2. Hiển thị danh sách tất cả các môn học đã lưu trữ");
            System.out.println("3. Thay đổi thông tin môn học");
            System.out.println("4. Xóa môn học (nếu môn học chứa điểm thi thì không xóa được)");
            System.out.println("5. Thoát");
            System.out.print("Lựa chọn của bạn là:");
            inputChoice(scanner);
            switch (choice) {
                case 1:
                    addSubject(scanner);
//                    writeDataToFile();
                    break;
                case 2:
                    showListSubjects();
                    break;
                case 3:
                    updateSubject(scanner);
                    break;
                case 4:
                    deleteSubject(scanner);
                    break;
                case 5:
//                    writeDataToFile();
                    isExit = false;
                    break;
                default:
                    System.err.println("Vui lòng nhập từ 1-5");
            }
        } while (isExit);
    }

    public static void addSubject(Scanner scanner) {
        System.out.println("Hãy nhập số lượng môn học muốn thêm:");
        inputChoice(scanner);
        for (int i = 0; i < choice; i++) {
            SubjectImpl newSubject = new SubjectImpl();
            newSubject.inputData();
            listSubjects.add(newSubject);
        }
    }

    public static void showListSubjects() {
        listSubjects.forEach(subject -> subject.displayData());
    }

    public static void updateSubject(Scanner scanner) {
        System.out.println("Hãy nhập mã môn học muốn update:");
        String idUpdate = scanner.nextLine();
        int indexUpdate = findSubjectIndexById(idUpdate);
        if (indexUpdate >= 0) {
            System.out.println("Cập nhật tên môn học:");
            listSubjects.get(indexUpdate).setSubjectName(inputSubjectName(scanner));
            System.out.println("Đã cập nhật xong tên môn học!");
        } else {
            System.err.println("Môn học này không tồn tại!");
        }
    }

    public static int findSubjectIndexById(String id) {
        for (int i = 0; i < listSubjects.size(); i++) {
            if (listSubjects.get(i).getSubjectId().equals(id)) {
                return i;
            }
        }
        return -1;
    }

    public static void deleteSubject(Scanner scanner) {
        System.out.println("Hãy nhập mã môn học muốn xóa:");
        String idDelete = scanner.nextLine();
        int indexDelete = findSubjectIndexById(idDelete);
        if (indexDelete >= 0) {
            if (listMarks.stream().anyMatch(mark -> mark.getSubject().getSubjectId().equals(idDelete))) {
                System.err.println("Môn học này có chứa điểm nên không thể xóa!");
            } else {
                listSubjects.remove(indexDelete);
                System.out.println("Đã xóa xong môn học này!");
            }
        }
    }

    public static void displayMarkManagementMenu() {
        boolean isExit = true;
        do {
            System.out.println("**************MARK-MANAGEMENT*************");
            System.out.println("1. Thêm mới điểm cho 1 sinh viên");
            System.out.println("2. Hiển thị điểm theo thứ tự điểm tăng dần");
            System.out.println("3. Thay đổi điểm theo sinh viên");
            System.out.println("4. Xóa điểm thi của sinh viên");
            System.out.println("5. Hiển thị danh sách điểm thi theo mã môn học");
            System.out.println("6. Hiển thị đánh giá học lực của từng học sinh theo mã môn học (giả sử <5 là yếu , <=6.5 là trung" +
                    "bình, <= 8 là khá, <= 9 là giỏi, còn lại là xuất sắc");
            System.out.println("7. Thoát");
            System.out.print("Lựa chọn của bạn là:");
            inputChoice(scanner);
            switch (choice) {
                case 1:
                    addMark(scanner);
//                    writeDataToFile();
                    break;
                case 2:
                    sortByMarkESC();
                    break;
                case 3:
                    updateMark(scanner);
                    break;
                case 4:
                    deleteMarkByStudentId(scanner);
                    break;
                case 5:
                    showMarkListBySubjectId();
                    break;
                case 6:
                    showLevelOfStudent();
                    break;
                case 7:
                    isExit = false;
                    break;
                default:
                    System.err.println("Vui lòng nhập từ 1-7");
            }
        } while (isExit);
    }

    public static void addMark(Scanner scanner) {
        StudentImpl selectedStudent = selectStudent(scanner);
        System.out.println("Nhập điểm cho sinh viên này:");
        MarkImpl newMark = new MarkImpl();
        newMark.setStudent(selectedStudent);
        newMark.inputData();
        listMarks.add(newMark);
    }

    public static void sortByMarkESC() {
        System.out.println("Danh sách điểm theo thứ tự tăng dần là:");
        listMarks.stream().sorted(Comparator.comparing(MarkImpl::getPoint)).forEach(mark -> mark.displayData());
    }

    public static void updateMark(Scanner scanner) {
        System.out.println("Hãy nhập mã sinh viên muốn update điểm:");
        inputChoice(scanner);
        if (listMarks.stream().filter(mark -> mark.getStudent().getStudentId() == choice) != null) {
            List<MarkImpl> filterdList = listMarks.stream().filter(mark -> mark.getStudent().getStudentId() == choice).collect(Collectors.toList());
            boolean isExit = true;
            do {
                System.out.println("Hãy chọn mã môn học muốn update điểm cho sinh viên này:");
                filterdList.stream().forEach(mark -> System.out.println(mark.getSubject().getSubjectId()));
                String idSubject = scanner.nextLine();
                int indexUpdate = findMarkBySubjectId(idSubject);
                if (indexUpdate >= 0) {
                    listMarks.get(indexUpdate).setPoint(inputPoint(scanner));
                    System.out.println("Đã cập nhật xong điểm cho môn học này!");
                } else {
                    System.err.println("Không tồn tại môn học này!");
                }
                System.out.println("Bạn có muốn thoát update không?");
                System.out.println("1. Có");
                System.out.println("2. Không");
                inputChoice(scanner);
                if (choice == 1) {
                    isExit = false;
                }
            } while (isExit);
        } else {
            System.err.println("Sinh viên này chưa có điểm để update!");
        }

    }

    public static int findMarkByStudentId(int id) {
        for (int i = 0; i < listMarks.size(); i++) {
            if (listMarks.get(i).getStudent().getStudentId() == id) {
                return i;
            }
        }
        return -1;
    }

    public static int findMarkBySubjectId(String id) {
        for (int i = 0; i < listMarks.size(); i++) {
            if (listMarks.get(i).getSubject().getSubjectId().equals(id)) {
                return i;
            }
        }
        return -1;
    }

    public static void deleteMarkByStudentId(Scanner scanner) {
        System.out.println("Hãy nhập mã sinh viên muốn xóa điểm thi:");
        inputChoice(scanner);
        if (listMarks.stream().filter(mark -> mark.getStudent().getStudentId() == choice) != null) {
            boolean isExit = true;
            do {
                System.out.println("Hãy nhập mã môn học muốn xóa điểm thi:");
                listMarks.stream().filter(mark -> mark.getStudent().getStudentId() == choice).forEach(mark -> System.out.println(mark.getSubject().getSubjectId()));
                String subjectId = scanner.nextLine();
                int indexDelete = findMarkBySubjectId(subjectId);
                if (indexDelete >= 0) {
                    listMarks.remove(indexDelete);
                    System.out.println("Đã xóa xong điểm môn học này!");
                } else {
                    System.err.println("Chưa có điểm môn này!");
                }
                System.out.println("Bạn có muốn thoát khỏi xóa không?");
                System.out.println("1. Có");
                System.out.println("2. Không");
                inputChoice(scanner);
                if (choice == 1) {
                    isExit = false;
                }
            } while (isExit);
        } else {
            System.err.println("Sinh viên này chưa có điểm để xóa!");
        }
    }

    public static void showMarkListBySubjectId(){
        listSubjects.stream().forEach(subject -> {
            System.out.println(subject.getSubjectId());
            listMarks.stream().filter(mark -> mark.getSubject().getSubjectId().equals(subject.getSubjectId())).forEach(mark -> mark.displayData());
        });
    }

    public static void showLevelOfStudent(){
        listStudents.stream().forEach(student -> {
            System.out.printf("%d - %s:\n", student.getStudentId(), student.getStudentName());
            listMarks.stream().filter(mark -> mark.getStudent().getStudentId() == student.getStudentId()).forEach(
                    mark -> {
                        System.out.printf("%s: %s\n", mark.getSubject().getSubjectId(), mark.getPoint() < 5? "Yếu": mark.getPoint() <= 6.5? "Trung bình": mark.getPoint() <=8? "Khá" : mark.getPoint() <=9? "Giỏi" : "Xuất sắc");
                    }
            );
        });
    }

//    public static void readDataFromFile() {
//        File studentFile = new File("students.txt");
//        File subjectFile = new File("subjects.txt");
//        File markFile = new File("marks.txt");
//        try {
//            if (studentFile.exists()) {
//                FileInputStream fis = new FileInputStream(studentFile);
//                ObjectInputStream ois = new ObjectInputStream(fis);
//                listStudents = (List<StudentImpl>) ois.readObject();
//                ois.close();
//                fis.close();
//            } else {
//                listStudents = new ArrayList<>();
//            }
//            if (subjectFile.exists()) {
//                FileInputStream fis = new FileInputStream(subjectFile);
//                ObjectInputStream ois = new ObjectInputStream(fis);
//                listSubjects = (List<SubjectImpl>) ois.readObject();
//                ois.close();
//                fis.close();
//            } else {
//                listSubjects = new ArrayList<>();
//            }
//            if (markFile.exists()) {
//                FileInputStream fis = new FileInputStream(markFile);
//                ObjectInputStream ois = new ObjectInputStream(fis);
//                listMarks = (List<MarkImpl>) ois.readObject();
//                ois.close();
//                fis.close();
//            } else {
//                listMarks = new ArrayList<>();
//            }
//        } catch (Exception ex) {
//            System.err.println("Đã xảy ra lỗi trong quá trình đọc file!");
//        }
//    }
//
//    public static void writeDataToFile() {
//        File studentFile = new File("students.txt");
//        File subjectFile = new File("subjects.txt");
//        File markFile = new File("marks.txt");
//        try {
//            FileOutputStream studentFos = new FileOutputStream(studentFile);
//            ObjectOutputStream studentOos = new ObjectOutputStream(studentFos);
//            studentOos.writeObject(listStudents);
//            studentOos.flush();
//            studentFos.close();
//            studentOos.close();
//
//            FileOutputStream subjectFos = new FileOutputStream(subjectFile);
//            ObjectOutputStream subjectOos = new ObjectOutputStream(subjectFos);
//            subjectOos.writeObject(listSubjects);
//            subjectOos.flush();
//            subjectFos.close();
//            subjectOos.close();
//
//            FileOutputStream markFos = new FileOutputStream(markFile);
//            ObjectOutputStream markOos = new ObjectOutputStream(markFos);
//            markOos.writeObject(listMarks);
//            markOos.flush();
//            markFos.close();
//            markOos.close();
//        } catch (Exception ex) {
//            System.err.println("Đã xảy ra lỗi trong quá trình ghi file!");
//        }
//    }

}
