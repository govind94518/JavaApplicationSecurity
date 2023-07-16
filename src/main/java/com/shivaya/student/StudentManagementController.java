package com.shivaya.student;

import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("management/api/v1/student")
public class StudentManagementController {
    private static final List<Student> STUDENTS = Arrays.asList(
            new Student(1,"shiva"),
            new Student(2,"universe"),
            new Student(3,"guru")
    );

    @GetMapping(path = "{studentId}")
    public  Student getStudentById(@PathVariable int studentId){
        System.out.println("StudentManagementController getStudentById "+studentId);
        return STUDENTS.stream()
                .filter(student -> studentId ==student.getId())
                .findAny()
                .orElseThrow(()->new RuntimeException("studentId is not present "+studentId));
    }

    @GetMapping
    public  List<Student>  getAllStudents(){
        System.out.println("StudentManagementController getAllStudents()");
        return STUDENTS ;
    }

    @DeleteMapping(path = "{studentId}")
    public  Student deleteStudentById(@PathVariable int studentId){
        System.out.println("StudentManagementController  deleteStudentById "+studentId);
        return STUDENTS.stream()
                .filter(student -> studentId ==student.getId())
                .findAny()
                .orElseThrow(()->new RuntimeException("studentId is not present "+studentId));
    }

    @PutMapping(path = "{studentId}")
    public  Student updateStudentById(@PathVariable int studentId){
        System.out.println("StudentManagementController  updateStudentById "+studentId);
        return STUDENTS.stream()
                .filter(student -> studentId ==student.getId())
                .findAny()
                .orElseThrow(()->new RuntimeException("studentId is not present "+studentId));
    }
}
