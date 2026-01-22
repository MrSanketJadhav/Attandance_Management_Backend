package com.ams.attendance.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ams.attendance.api.entity.Student;
import com.ams.attendance.api.service.StudentService;

@RestController
@RequestMapping("/student")
@CrossOrigin("http://localhost:4200")
public class StudentController {

	@Autowired
	private StudentService studentService;
	
	@GetMapping("/get-all-students")
	public List<Student> getAllStudents() {
		return studentService.getAllStudents();
	}

	@PostMapping("/add-student")
	public Student createStudent(@RequestBody Student student) {
		return studentService.createStudent(student);
	}

	@GetMapping("/get-student-by-id")
	public Student getStudentById(@RequestParam Long id) {
		return studentService.getStudentById(id);
	}

	@PutMapping("/update-student")
	public Student updateStudent(@RequestBody Student studentDetails) {

		return studentService.updateStudent(studentDetails);
	}

	@DeleteMapping("/delete-student")
	public String deleteStudent(@RequestParam long id) {
		return studentService.deleteStudent(id);
	}
}
