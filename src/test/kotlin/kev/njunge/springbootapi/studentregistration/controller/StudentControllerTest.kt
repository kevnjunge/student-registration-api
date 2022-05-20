package kev.njunge.springbootapi.studentregistration.controller

import com.fasterxml.jackson.databind.ObjectMapper
import kev.njunge.springbootapi.studentregistration.model.Student
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.*

@SpringBootTest
@AutoConfigureMockMvc
internal class StudentControllerTest @Autowired constructor(
    val mockMvc: MockMvc,
    val objectMapper: ObjectMapper
){

    val baseUrl = "/api/students"

    @Nested
    @DisplayName("GET /api/students")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetStudents{
        @Test
        fun `should return all students`(){
            //when/then
            mockMvc.get(baseUrl)
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON )}
                    jsonPath("$[0].registrationNumber"){
                        value("cit-213")
                    }
                }
        }

    }

    @Nested
    @DisplayName("GET /api/student/{accountNumber}")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetStudent{
        @Test
        fun `should return a student with a given registration number`(){
            //given
            val registrationNumber = "cit-213"

            //when/then
            mockMvc.get("$baseUrl/$registrationNumber")
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$.studentName"){value("joe")}
                    jsonPath("$.studentEmail"){value("joe@test1.com")}
                    jsonPath("$.studentCourse"){value("Commerce")}
                }
        }

        @Test
            fun `should return NOT FOUND if registration number dies not exist `(){
                //given
                val registrationNumber = "does_not_exist"

                //when/then
            mockMvc.get("$baseUrl/$registrationNumber")
                .andDo { print() }
                .andExpect { status { isNotFound() } }

            }



    }

    @Nested
        @DisplayName("POST /api/students")
        @TestInstance(TestInstance.Lifecycle.PER_CLASS)
        inner class PostNewStudent {
            @Test
                fun `should add the a new Student`(){
                    //given
                    val newStudent = Student("cit-321","Angel", "angel@mail.com", "Psychology")

                    //when
                val performPost =mockMvc.post(baseUrl){
                    contentType = MediaType.APPLICATION_JSON
                    content = objectMapper.writeValueAsString(newStudent)
                }
                //then
                   performPost
                           .andDo { print() }
                            .andExpect {
                            status { isCreated() }
                            content {
                                contentType(MediaType.APPLICATION_JSON)
                                json(objectMapper.writeValueAsString(newStudent))
                            }
                       }
                mockMvc.get("$baseUrl/${newStudent.registrationNumber}")
                    .andExpect { content { json(objectMapper.writeValueAsString(newStudent)) } }

                }
        @Test
            fun `should return BAD REQUEST student with given registration number already exist`(){
                //given
                val invalidStudent = Student("cit-213","joe","joe@test1.com","Commerce")
        
                //when
            val performPost =mockMvc.post(baseUrl) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(invalidStudent)
            }
                //then
                performPost
                    .andDo { print() }
                    .andExpect { status {isBadRequest() } }
                
            }

        }
    @Nested
        @DisplayName("PATCH /api/students")
        @TestInstance(TestInstance.Lifecycle.PER_CLASS)
        inner class PatchExistingStudent{
            
            @Test
                fun `should update existing student`(){
                    //given
                    val updatedStudent = Student("cit-321","Angel", "angel22@mail.com", "Psychology with It")
            
                    //when
                   val performPatchRequest = mockMvc.patch(baseUrl){
                        contentType = MediaType.APPLICATION_JSON
                        content = objectMapper.writeValueAsString(updatedStudent)
                    }
            
                    //then
                    performPatchRequest
                        .andDo { print() }
                        .andExpect {
                            status { isOk() }
                            content {
                                contentType(MediaType.APPLICATION_JSON)
                                json(objectMapper.writeValueAsString(updatedStudent))
                            }
                        }
                mockMvc.get("$baseUrl/${updatedStudent.registrationNumber}")
                    .andExpect { content { json(objectMapper.writeValueAsString(updatedStudent)) } }
                    
                }
        @Test
            fun `should return a BAD REQUEST id no student with the given account number exists`(){
                //given
                val invalidStudent = Student("does_not_exist","joe","joe@test1.com","Commerce")

                //when
            val performPatchRequest = mockMvc.patch(baseUrl){
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(invalidStudent)
            }
                //then
            performPatchRequest
                .andDo { print() }
                .andExpect { status { isNotFound() } }

            }



        }
    @Nested
        @DisplayName("DELETE /api/students{registrationNumber}")
        @TestInstance(TestInstance.Lifecycle.PER_CLASS)
        inner class DeleteExistingBank {
            @Test
                fun `should delete the student with the given registrationNumber`(){
                    //given
                    val registrationNumber = "cit-213"

                    //when/then
                mockMvc.delete("$baseUrl/$registrationNumber")
                    .andDo { print() }
                    .andExpect {
                        status { isNoContent() }
                    }
                mockMvc.get("$baseUrl/$registrationNumber")
                    .andExpect { status { isNotFound() } }

                }
        @Test
            fun `should return NoT FOUND if no student with given registration number exists`(){
                //given
                val invalidRegistrationNumber = "does_not_exist"
        
                //when
                mockMvc.delete("$baseUrl/$invalidRegistrationNumber")
                    .andDo { print() }
                    .andExpect { status { isNotFound() } }
        
                //then
                
            }


        }




}