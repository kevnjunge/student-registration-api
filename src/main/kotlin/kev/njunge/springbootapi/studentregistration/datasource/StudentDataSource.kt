package kev.njunge.springbootapi.studentregistration.datasource

import kev.njunge.springbootapi.studentregistration.model.Student

interface StudentDataSource {

    fun retrieveStudents(): Collection<Student>

    fun retrieveStudent(registrationNumber: String): Student

    fun createStudent(student: Student): Student

    fun updateStudent(student: Student): Student

    fun deleteStudent(registrationNumber: String)
}