package com.demo.custapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.custapp.bean.ApiResponse;
import com.demo.custapp.bean.BookIssue;
import com.demo.custapp.entity.Customer;
import com.demo.custapp.entity.CustomerIssue;
import com.demo.custapp.exception.CustomerNotFoundException;
import com.demo.custapp.service.CustomerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController 
@CrossOrigin//(origins = "http://localhost:8081")
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	CustomerService cService;
	
	@Autowired
	Environment environment;
	
	//The @Tag annotation can be applied at class or method level and is used to group the APIs in a meaningful way.
	@Tag(name = "post", description = "POST methods of Customer APIs")
	@Operation(summary = "Register a new customer",
    description = "Register a new customer if all data are valid based on data constraints and return response. If validation fails, then exception object will throw back.")
	@PostMapping
	public ResponseEntity<Customer> addCustomer(@RequestBody Customer customer){
		customer = cService.addCustomer(customer);
		return new ResponseEntity<Customer>(customer, HttpStatus.OK);
	}
	
	@Tag(name = "get", description = "GET methods of Customer APIs")
	@Operation(summary = "get a customer information based on id",
    description = "Search a  customer by  id, if found then return customer data as response. If not found for ID, then exception object will throw back.")
	@GetMapping("/customer/{id}")
	public ResponseEntity<Customer> getCustomerById(@Parameter(
		       description = "ID of customer to be retrieved",
		       required = true) @PathVariable("id") Integer id) throws CustomerNotFoundException{
		String port = environment.getProperty("local.server.port");
		System.out.println("Port is "+port);
		Customer customer = cService.getCustomerById(id);
		return new ResponseEntity<Customer>(customer, HttpStatus.OK);
	}
	
	@Tag(name = "post", description = "POST methods of Customer APIs")
	@Operation(summary = "Issue a book to an existing customer",
    description = "Issueing a book to an existing customer, if customter is valid and book is available then book issue done and return response. If fails, then exception object will throw back.")
	@PostMapping("/issue")
	public ResponseEntity<ApiResponse> issueBook(@RequestBody BookIssue bookIssue){
		CustomerIssue ciObj = cService.issueBook(bookIssue);
		if(ciObj!=null) {
		ApiResponse response = new ApiResponse("Book is issued with reference:" + ciObj.getIssueId(), 200);
		return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
		}
		else {
			ApiResponse response = new ApiResponse("Book is not issued", 404);
			return new ResponseEntity<ApiResponse>(response, HttpStatus.NOT_FOUND);
		}
		
	}

	@Tag(name = "put", description = "PUT methods of Customer APIs")
	@Operation(summary = "Return an issued book from a customer",
    description = "submission of an issued book from a customer if issue reference is right, make book available for others and return response. If validation fails, then exception object will throw back.")
	@PutMapping("/submit/{issueid}")
	public ResponseEntity<ApiResponse> submitBook(@PathVariable("issueid") Integer issueid){
		CustomerIssue ciObj = cService.submitBook(issueid);
		if(ciObj==null) {
		ApiResponse response = new ApiResponse("Book reference:" + issueid+" is not found", 404);
		return new ResponseEntity<ApiResponse>(response, HttpStatus.NOT_FOUND);
		
		}
		else {
			ApiResponse response = new ApiResponse("Book is returned back", 200);
			return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
			
		}
		
	}
}








