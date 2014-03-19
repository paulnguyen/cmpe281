package hellorest;



import java.util.List;

import org.springframework.data.repository.CrudRepository;


public interface ContactRepository extends CrudRepository<Contact, Long> {

	public List<Contact> findByFirstName(String firstName);
	
}
