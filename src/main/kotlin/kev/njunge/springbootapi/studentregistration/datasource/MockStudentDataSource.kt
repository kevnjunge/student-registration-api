package kev.njunge.springbootapi.studentregistration.datasource

import kev.njunge.springbootapi.studentregistration.model.Student
import org.springframework.stereotype.Repository

@Repository
class MockStudentDataSource: StudentDataSource {

    val students = mutableListOf(
        Student("cit-213","joe" , "joe@test1.com", "Commerce"),
        Student("bus-21-3","Angus","angus@std2.com","Psychology"),
        Student("sct-32-3","Jane","jane@mail.com","IT")
    )

    override fun retrieveStudents(): Collection<Student> = students
    override fun retrieveStudent(registrationNumber: String): Student =
         students.firstOrNull(){it.registrationNumber == registrationNumber}
             ?: throw NoSuchElementException("Could not find student with registration number $registrationNumber")

    override fun createStudent(student: Student): Student {
        if (students.any{it.registrationNumber == student.registrationNumber}){
            throw IllegalArgumentException("Student with registration number ${student.registrationNumber} already exists")
        }
        students.add(student)
        return student
    }

    override fun updateStudent(student: Student): Student {
        val  currentStudent = students.firstOrNull{it.registrationNumber == student.registrationNumber}
            ?: throw  NoSuchElementException("Could not find a student with registration number ${student.registrationNumber}")

        students.remove(currentStudent)
        students.add(student)

        return student
    }

    override fun deleteStudent(registrationNumber: String) {
        val  currentStudent = students.firstOrNull{it.registrationNumber == registrationNumber}
            ?: throw  NoSuchElementException("Could not find a student with registration number $registrationNumber")

        students.remove(currentStudent)



    }
}