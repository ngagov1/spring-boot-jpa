package hello;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {

    @Autowired
    private CustomerService customerService;
    
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value="/customersByLastName", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE}, consumes=MediaType.ALL_VALUE)
    public List<Customer> greeting(@RequestParam(value="lastName") String lastName) {
    	return customerService.findByLastName(lastName);
    }
}
