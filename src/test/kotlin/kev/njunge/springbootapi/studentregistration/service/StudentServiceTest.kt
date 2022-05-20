package kev.njunge.springbootapi.studentregistration.service

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kev.njunge.springbootapi.studentregistration.datasource.StudentDataSource
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class StudentServiceTest{

    private val dataSource: StudentDataSource = mockk(relaxed = true)

    private val studentService = StudentService(dataSource)

    @Test
        fun `should call its data source to retrieve students `(){
        //when
        studentService.getStudents()

            //then
        verify (exactly = 1){ dataSource.retrieveStudents() }

        }
}