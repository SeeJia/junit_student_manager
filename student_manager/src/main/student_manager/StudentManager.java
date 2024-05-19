package main.student_manager;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class StudentManager {
	Map<String, Student> studentList = new ConcurrentHashMap<String, Student>();

    public void addStudent(int studentID, String firstName, String lastName, String phoneNumber) {
        Student student = new Student(studentID, firstName, lastName, phoneNumber);
        validateStudent(student);
        checkIfStudentAlreadyExist(student);
        studentList.put(generateKey(student), student);
    }

    public Collection<Student> getAllStudentsInfo() {
        return studentList.values();
    }

    private void checkIfStudentAlreadyExist(Student student) {
        if (studentList.containsKey(generateKey(student)))
            throw new RuntimeException("Student Already Exists");
    }

    private void validateStudent(Student student) {
    	student.validateStudentID();
    	student.validateFirstName();
    	student.validateLastName();
    	student.validatePhoneNumber();
    }

    private String generateKey(Student student) {
        return String.format("%s-%s", student.getFirstName(), student.getLastName());
    }
}
