package com.demo.custapp.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.demo.custapp.bean.Book;
import com.demo.custapp.bean.BookIssue;
import com.demo.custapp.entity.Customer;
import com.demo.custapp.entity.CustomerIssue;
import com.demo.custapp.exception.CustomerNotFoundException;
import com.demo.custapp.repository.CustomerIssueRepository;
import com.demo.custapp.repository.CustomerRepository;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.transaction.Transactional;

@Service
public class CustomerService {

	@Autowired
	CustomerRepository cRepo;
	@Autowired
	CustomerIssueRepository ciRepo;
	private static final String SAMPLE_SERVICE = "sampleService";
	
	
	public Customer addCustomer(Customer customer) {
		customer = cRepo.save(customer);
		return customer;
	}
	
	public Customer getCustomerById(Integer id) throws CustomerNotFoundException {
		Customer customer = cRepo.findById(id).orElse(null);
		if(customer==null)
			throw new CustomerNotFoundException("Customer Id not found");
		return customer;
	}

	@Transactional
	@CircuitBreaker(name = SAMPLE_SERVICE, fallbackMethod = "fallbackResponse")
	public CustomerIssue issueBook(BookIssue bookIssue) {
		RestTemplate rTemplate= new RestTemplate();
		//try {
			Customer customer = cRepo.findById(bookIssue.getCustomerId()).orElse(null);
			if(customer==null)
				//throw new CustomerNotFoundException("Customer Id not found");
				return null;
			
			Book book = rTemplate.getForObject("http://localhost:9091/app1/book/"+bookIssue.getIsbnNo(), Book.class);
			//Book book = rTemplate.getForObject("http://APIGATEWAYAPP/app1/book/"+bookIssue.getIsbnNo(), Book.class);
			//logical action
			if(!book.isStatus()) // book already issued
				return null;
			CustomerIssue ciObj = new CustomerIssue();
			ciObj.setBookId(bookIssue.getIsbnNo());
			ciObj.setCustomer(customer);
			ciObj.setIssueDate(LocalDate.now());
			ciObj.setReturnDate(LocalDate.now().plusDays(7));
			ciObj.setStatus("issued");
			ciObj = ciRepo.save(ciObj);
			
			rTemplate.put("http://localhost:9091/app1/book/"+bookIssue.getIsbnNo()+"/false", Book.class);
			//rTemplate.put("http://APIGATEWAYAPP/app1/book/"+bookIssue.getIsbnNo()+"/false", Book.class);
			return ciObj;
	
		//}
		//catch(Exception e) {
		//	System.out.println(e.getMessage());
		//	return null;
		//}
		
	}
	
	/**
     * Fallback method called when the circuit breaker is open.
     * @param ex Exception thrown
     * @return Fallback response
     */
    public CustomerIssue fallbackResponse(Exception ex) {
        System.out.println("Fallback response: " + ex.getMessage());
        return null;
    }

	@Transactional
	public CustomerIssue submitBook(Integer issueid) {
		RestTemplate rTemplate= new RestTemplate();
		CustomerIssue ciObj = ciRepo.findById(issueid).orElse(null);
		if(ciObj==null)
		    return null;
		ciObj.setStatus("returned");
		ciObj = ciRepo.save(ciObj);
		rTemplate.put("http://localhost:9091/app1/book/"+ciObj.getBookId()+"/true", Book.class);
		//rTemplate.put("APIGATEWAYAPP/app1/book/"+ciObj.getBookId()+"/true", Book.class);
		return ciObj;
		
	}

	
}








