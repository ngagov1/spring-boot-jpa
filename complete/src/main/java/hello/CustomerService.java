package hello;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerService {
	
	@Autowired
    private CustomerRepository repository;
	
	@Transactional(readOnly=true)
	public List<Customer> findByLastName(String lastName) {
		return repository.findByLastName(lastName);
	}
}
