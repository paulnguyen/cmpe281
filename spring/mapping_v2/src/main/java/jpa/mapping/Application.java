package jpa.mapping;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(Application.class, args);
		
		AccountRepository accountRepository = context.getBean(AccountRepository.class);
		
		Account accountA = new Account();
		accountA.setUsername("root");
		accountA.setPassword("secret");
		accountA.setFullname("Supert User");
		accountA.setState(AccountState.Activated);
		
		Account accountB = new Account();
		accountB.setUsername("user");
		accountB.setPassword("secret");
		accountB.setFullname("Normal User");
		accountB.setState(AccountState.Blocked);
		
		accountRepository.save(accountA);
		accountRepository.save(accountB);
	}
}
