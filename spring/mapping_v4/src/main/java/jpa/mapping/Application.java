package jpa.mapping;

import java.util.ArrayList;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;


@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		
		ApplicationContext context = SpringApplication.run(Application.class, args);

		RoleRepository roleRepository = context.getBean(RoleRepository.class);
		
		Role role = new Role();
		role.setName("Admin");
		
		User userA = new User();
		userA.setUsername("n.horatio");
		userA.setPassword("secret123");
		userA.setFullname("Horatio Nguyen");
		
		User userB = new User();
		userB.setUsername("t.cayson");
		userB.setPassword("12345");
		userB.setFullname("Cayson Tyler");
		
		role.setUsers(new ArrayList<User>());
		role.getUsers().add(userA);
		role.getUsers().add(userB);
		userA.setRole(role);
		userB.setRole(role);
		
		roleRepository.save(role);
		
	}
}
