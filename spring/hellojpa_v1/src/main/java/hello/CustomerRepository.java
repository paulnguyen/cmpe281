package hello;

import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

	// Default Query Generator
    List<Customer> findByLastName(String lastName) ;

}