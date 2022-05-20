package kev.njunge.springbootapi.studentregistration.service

import kev.njunge.springbootapi.studentregistration.datasource.StudentDataSource
import kev.njunge.springbootapi.studentregistration.model.Student
import org.springframework.stereotype.Service

@Service
class StudentService(private val dataSource: StudentDataSource) {

    fun getStudents(): Collection<Student> = dataSource.retrieveStudents()

    fun getStudent(registrationNumber: String): Student = dataSource.retrieveStudent(registrationNumber)

    fun addStudent(student: Student): Student  = dataSource.createStudent(student)

    fun updateStudent(student: Student): Student = dataSource.updateStudent(student)

    fun deleteStudent(registrationNumber: String): Unit = dataSource.deleteStudent(registrationNumber)
}