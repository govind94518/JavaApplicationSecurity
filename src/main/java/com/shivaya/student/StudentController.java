package com.shivaya.student;

import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("api/v1/student")
public class StudentController {
    private static final List<Student> STUDENTS = Arrays.asList(
            new Student(1,"shiva"),
            new Student(2,"universe"),
            new Student(3,"guru")
    );

    @GetMapping(path = "{studentId}")
    public  Student getStudentById(@PathVariable int studentId){
        return STUDENTS.stream()
                .filter(student -> studentId ==student.getId())
                .findAny()
                .orElseThrow(()->new RuntimeException("studentId is not present "+studentId));
    }

    @GetMapping
    public  List<Student>  getAllStudents(){
        return STUDENTS ;
    }
}
