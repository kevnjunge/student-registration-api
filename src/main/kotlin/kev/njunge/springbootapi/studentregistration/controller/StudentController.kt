package kev.njunge.springbootapi.studentregistration.controller

import kev.njunge.springbootapi.studentregistration.model.Student
import kev.njunge.springbootapi.studentregistration.service.StudentService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/students")
class StudentController(private val service:StudentService) {

    @ExceptionHandler(NoSuchElementException ::class)
    fun handleNotFound(e:NoSuchElementException): ResponseEntity<String> =
        ResponseEntity(e.message,HttpStatus.NOT_FOUND)

    @ExceptionHandler(IllegalArgumentException ::class)
    fun handleBadRequest(e:IllegalArgumentException): ResponseEntity<String> =
        ResponseEntity(e.message,HttpStatus.BAD_REQUEST)

    @GetMapping
    fun getStudents(): Collection<Student> = service.getStudents()

    @GetMapping("/{registrationNumber}")
    fun getStudent(@PathVariable registrationNumber: String): Student = service.getStudent(registrationNumber)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addStudent(@RequestBody student: Student): Student =service.addStudent(student)

    @PatchMapping
    fun updateStudent(@RequestBody student: Student):Student = service.updateStudent(student)

    @DeleteMapping("/{registrationNumber}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteStudent(@PathVariable registrationNumber: String): Unit = service.deleteStudent(registrationNumber)

}