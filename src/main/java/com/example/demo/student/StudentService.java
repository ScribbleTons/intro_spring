package com.example.demo.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class StudentService {

    StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    public Student addNewStudent(Student student) {
        Optional<Student> studentOptional = studentRepository.findStudentByEmail(student.getEmail());

        if (studentOptional.isPresent()) {
            throw new IllegalStateException("Email taken");
        }

        return studentRepository.save(student);
    }

    public void deleteStudent(Long studentId) {
        boolean exists = studentRepository.existsById(studentId);

        if (!exists) {
            throw new IllegalStateException(
                    "Student with ID: " + studentId + " does not exist"
            );
        }

        studentRepository.deleteById(studentId);
    }

    @Transactional
    public Student updateStudent(Long studentId, Student updatedStudent) {
        Student student = studentRepository.findById(studentId).orElseThrow(
                () -> new IllegalStateException("Student with ID: " + studentId + " does not exist.")
        );

        if (updatedStudent.getName() != null && !Objects.equals(student.getName(),updatedStudent.getName())) {
            student.setName(updatedStudent.getName());
        }

        if (updatedStudent.getEmail() != null && !Objects.equals(student.getEmail(), updatedStudent.getEmail())) {
            Optional<Student> studentOptional = studentRepository.findStudentByEmail(updatedStudent.getEmail());

            if (studentOptional.isPresent()) {
                throw new IllegalStateException("Email taken");
            }
            student.setEmail(updatedStudent.getEmail());
        }

        Student studentResponse = studentRepository.save(student);

        return studentResponse;

    }
}
