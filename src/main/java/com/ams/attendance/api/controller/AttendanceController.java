package com.ams.attendance.api.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ams.attendance.api.entity.AttendanceRecord;
import com.ams.attendance.api.entity.Student;
import com.ams.attendance.api.entity.Subject;
import com.ams.attendance.api.entity.User;
import com.ams.attendance.api.model.AttendanceRecordRequest;
import com.ams.attendance.api.service.AttendanceRecordService;
import com.ams.attendance.api.service.StudentService;
import com.ams.attendance.api.service.SubjectService;
import com.ams.attendance.api.service.UserService;

@RestController
@RequestMapping("/attendance")
@CrossOrigin("http://localhost:4200")
public class AttendanceController {

	@Autowired
	private AttendanceRecordService attendanceRecordService;
	
	@Autowired
	private UserService userService;

	@Autowired
	private SubjectService subjectService;

	@Autowired
	private StudentService studentService;
	
	@GetMapping("/get-all-attendance-records")
	public List<AttendanceRecord> getAllAttendanceRecords() {
		return attendanceRecordService.getAllAttendanceRecords();
	}
	
	 @GetMapping("/get-attendance-by-faculty/{facultyUsername}")
	    public List<AttendanceRecord> getAttendanceByFaculty(@PathVariable String facultyUsername) {
	        return attendanceRecordService.getAttendanceByFaculty(facultyUsername);
	    }
	
	
	@GetMapping("/get-attendance-by-date-subjet/{date}/{subjectId}")
	public List<AttendanceRecord> getAllAttendanceRecords(@PathVariable String date,@PathVariable long subjectId){
		return attendanceRecordService.getAllAttendanceRecords(date,subjectId);
	}
	
	@GetMapping("/get-attendance/{faculty}/{subjectId}/{date}")
	public List<AttendanceRecord> getAttendanceByFacultySubjectDate(
	        @PathVariable String faculty, 
	        @PathVariable long subjectId, 
	        @PathVariable String date) {
	    return attendanceRecordService.getAttendanceByFacultySubjectDate(faculty, subjectId, date);
	}


	@PostMapping("/take-attendance")
	public AttendanceRecord createAttendanceRecord(@RequestBody AttendanceRecordRequest request) {

	    User user = userService.getUserByName(request.getUsername());
	    Subject subject = subjectService.getSubjectById(request.getSubjectId());

	    AttendanceRecord attendanceRecord = new AttendanceRecord();
	    attendanceRecord.setUser(user);
	    attendanceRecord.setSubject(subject);
	    attendanceRecord.setDate(request.getDate());
	    attendanceRecord.setTime(request.getTime());

	    // Map studentIds to Student objects
	    Set<Student> students = new HashSet<>();
	    if (request.getStudentIds() != null) {
	        for (Long studentId : request.getStudentIds()) {
	            Student student = studentService.getStudentById(studentId);  // Fetch the Student object by ID
	            if (student != null) {
	                students.add(student);
	            }
	        }
	    }

	    attendanceRecord.setStudents(students);
	    attendanceRecord.setNumberOfStudents(students.size());

	    System.out.println("Attendance record: " + attendanceRecord);
	    return attendanceRecordService.saveAttendance(attendanceRecord);
	}
}
