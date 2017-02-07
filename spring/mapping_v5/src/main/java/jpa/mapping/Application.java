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
		role.setPermissions(new ArrayList<Permission>());
		
		Permission readPermission = new Permission();
		readPermission.setName("read");
		readPermission.setDescription("The person, who has this permission can read database");
		
		Permission writePermission = new Permission();
		writePermission.setName("write");
		writePermission.setDescription("The person, who has this permission can write database");
		
		role.getPermissions().add(readPermission);
		role.getPermissions().add(writePermission);
		roleRepository.save(role);
	}
}
