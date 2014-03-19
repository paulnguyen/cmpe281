package hellorest;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.google.common.collect.Lists;


@Service("contactService")
@Repository
@Transactional
public class ContactServiceImpl implements ContactService {

	@Autowired
	private ContactRepository contactRepository;
	
	@Transactional(readOnly=true)
	public List<Contact> findAll() {
		return Lists.newArrayList(contactRepository.findAll());
	}

	@Transactional(readOnly=true)
	public List<Contact> findByFirstName(String firstName) {
		return contactRepository.findByFirstName(firstName);
	}

	@Transactional(readOnly=true)	
	public Contact findById(Long id) {
		return contactRepository.findOne(id);
	}

	public Contact save(Contact contact) {
		return contactRepository.save(contact);
	}

	public void delete(Contact contact) {
		contactRepository.delete(contact);
	}

}

