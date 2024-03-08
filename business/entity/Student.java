package ra.business.entity;

import java.util.Date;

public class Student {
    private int studentId;
    private String studentName;
    private Date birthDay;
    private String address;
    private boolean gender;
    private String phone;

    public Student() {
    }

    public Student(int studentId, String studentName, Date birthDay, String address, boolean gender, String phone) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.birthDay = birthDay;
        this.address = address;
        this.gender = gender;
        this.phone = phone;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
