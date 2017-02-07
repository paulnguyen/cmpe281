package jpa.mapping;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;


@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		
		ApplicationContext context = SpringApplication.run(Application.class, args);

		AccountRepository accountRepository = context.getBean(AccountRepository.class);
		
		Account account = new Account();
		account.setUsername("a.watson");
		account.setPassword("secret1234");
		account.setFullname("Alex Watson");
		
		UserProfile userProfile = new UserProfile();
		userProfile.setEmailAddress("a.watson@yopmail.com");
		userProfile.setAge(26);
		userProfile.setFirstName("Watson");
		userProfile.setLastName("Alex");
		userProfile.setPhoneNumber("0988123456");
		userProfile.setAddress("665 Clyde Avenue Mountain View CA 94043");
		
		account.setUserProrile(userProfile);
		userProfile.setAccount(account);
		
		accountRepository.save(account);
		
	}
}
