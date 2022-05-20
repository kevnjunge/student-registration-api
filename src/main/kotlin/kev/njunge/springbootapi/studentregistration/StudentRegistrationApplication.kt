package kev.njunge.springbootapi.studentregistration

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class StudentRegistrationApplication

fun main(args: Array<String>) {
	runApplication<StudentRegistrationApplication>(*args)
}
