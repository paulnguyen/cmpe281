package hello;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

	private static final Logger log = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		SpringApplication.run(Application.class);
	}

	@Bean
	public CommandLineRunner demo(CustomerRepository repository) {
		return (args) -> {

			// save some of customers
			Customer jack = new Customer("Jack", "Bauer", "jack@bauer.com") ;
			Address jack_address = new Address( "123 Main St.", "San Francisco", "USA") ;
			jack.add( jack_address ) ;
			repository.save( jack );

			Customer chloe = new Customer("Chloe", "O'Brian", "cloe@obrian.com") ;
			Address chloe_address = new Address( "123 Main St.", "London", "UK") ;	
			chloe.add ( chloe_address ) ;		
			repository.save( chloe );

			Customer kim = new Customer("Kim", "Bauer", "kim@bauer.com") ;
			Address kim_address = new Address( "123 Main St.", "San Jose", "USA") ;	
			kim.add( kim_address ) ;		
			repository.save( kim );

			Customer david = new Customer("David", "Palmer", "david@palmer.com") ;
			Address david_address = new Address( "123 Main St.", "Berkeley", "USA") ;	
			david.add( david_address ) ;			
			repository.save( david );

			Customer michelle = new Customer("Michelle", "Dessler", "michelle@dessler.com") ;
			Address michelle_address = new Address( "456 Main St.", "London", "UK") ;	
			michelle.add( michelle_address ) ;
			repository.save( michelle );

			// fetch all customers
			System.out.println("===============================");
			System.out.println("Customers found with findAll():");
			System.out.println("===============================");
			for (Customer customer : repository.findAll()) {
				System.out.println(customer.toString());
			}
			System.out.println("");

			// fetch an individual customer by ID
			Customer customer = repository.findOne(1L);
			System.out.println("===============================");
			System.out.println("Customer found with findOne(1L):");
			System.out.println("================================");
			System.out.println(customer.toString());
			System.out.println("");

			// fetch customers by last name
			System.out.println("============================================");
			System.out.println("Customer found with findByLastName('Bauer'):");
			System.out.println("============================================");
			for (Customer bauer : repository.findByLastName("Bauer")) {
				System.out.println(bauer.toString());
			}
			System.out.println("");

			// find by email address
			System.out.println("=================================================");
			System.out.println("Customer found with findByEmail('kim@bauer.com'):");
			System.out.println("=================================================");
			for (Customer cust : repository.findByEmail2("kim@bauer.com")) {
				System.out.println(cust.toString());
			}
			System.out.println("");

			// find by email and last name
			System.out.println("====================================================================");
			System.out.println("Customer found with findByEmailAndLastname('kim@bauer.com','bauer'):");
			System.out.println("====================================================================");
			for (Customer cust : repository.findByEmailAndLastName("kim@bauer.com","bauer")) {
				System.out.println(cust.toString());
			}
			System.out.println("");			

			// find by Address Country = USA
			System.out.println("=================================================");
			System.out.println("Customer found with findByAddressCountry('USA') :");
			System.out.println("=================================================");
			for (Customer cust : repository.findByAddresses_Country("USA")) {
				System.out.println(cust.toString());
			}
			System.out.println("");				

		};
	}

}