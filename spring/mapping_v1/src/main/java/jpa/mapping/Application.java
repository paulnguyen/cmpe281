package jpa.mapping;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(Application.class, args);
		
		StudentRepository studentRepository = context.getBean(StudentRepository.class);
		
		Student studentA = new Student();
		studentA.setFirstName("Alex");
		studentA.setLastName("Sander");
		studentA.setAge(25);
		
		Student studentB = new Student();
		studentB.setFirstName("Peter");
		studentB.setLastName("Duck");
		studentB.setAge(34);
		
		studentRepository.save(studentA);
		studentRepository.save(studentB);
	}
}