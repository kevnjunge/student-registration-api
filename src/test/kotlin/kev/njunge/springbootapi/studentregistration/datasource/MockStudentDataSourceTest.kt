package kev.njunge.springbootapi.studentregistration.datasource

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class MockStudentDataSourceTest{

    private val mockDataSource = MockStudentDataSource()

    @Test
    fun `should provide a collection of students`(){
        //when
        val students = mockDataSource.retrieveStudents()

        //then
        assertThat(students.size).isGreaterThanOrEqualTo(3)
    }
    @Test
        fun `should provide some mock data`(){
            //when
            val students = mockDataSource.retrieveStudents()

            //then
            assertThat(students).allMatch { it.studentName.isNotBlank() }
            assertThat(students).allMatch { it.registrationNumber.isNotBlank() }
            assertThat(students).allMatch { it.studentEmail.isNotBlank() }
            assertThat(students).allMatch { it.studentCourse .isNotBlank() }
        }


}