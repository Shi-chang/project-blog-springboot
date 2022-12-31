package com.example.springboot.controller;

import com.example.springboot.bean.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {
    // Get an object
//    @GetMapping("/student")
//    public Student getStudent() {
//        Student student = new Student(1, "alan", "turing");
//
//        return student;
//    }

    @GetMapping("/student")
    public ResponseEntity<Student> getStudent() {
        Student student = new Student(1, "alannn", "turing");

//        return new ResponseEntity<>(student, HttpStatus.OK);
//        return ResponseEntity.ok(student);
        return ResponseEntity.ok().header("custom-header", "alan1").body(student);
//        return ResponseEntity.status(HttpStatus.OK).header("123", "yes").body(student);
    }

    // Get a list of objects
    @GetMapping
    public List<Student> getStudents() {
        List<Student> students = new ArrayList<>();
        students.add(new Student(1, "alan", "turing"));
        students.add(new Student(2, "bryan", "smith"));

        return students;
    }

    // Spring boot rest api with path variable
    @GetMapping("/{id}")
    public Student studentPathVariable(@PathVariable("id") int studentId) {
        return new Student(studentId, "alan", "turing");
    }

    // Spring boot rest api with request param
    @GetMapping("/query")
    public Student studentRequestParam(@RequestParam int id) {
        return new Student(id, "alan", "turing");
    }

    // Spring boot rest api that handles HTTP POST request
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Student createStudent(@RequestBody Student student) {
        System.out.println(student);
        return student;
    }

    // Spring boot rest api that handles http put request
    @PutMapping("/{id}/update")
    @ResponseStatus(HttpStatus.OK)
    public Student updateStudent(@RequestBody Student student, @PathVariable int id) {
        System.out.println(student);
        return student;
    }

    @DeleteMapping("/{id}/delete")
    @ResponseStatus(HttpStatus.OK)
    public String deleteStudent(@PathVariable int id) {
        System.out.println(id);
        return "student deleted successfully.";
    }
}
