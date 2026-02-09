package com.demo.custapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class CustAppConfiguration {

	@Bean
	public OpenAPI createCustomerServiceOpenAPI() {
		Contact myContact = new Contact();
	    myContact.setName("Dibakar Sanyal");
	    myContact.setEmail("sanyaldibakar@gmail.com");

		return new OpenAPI()
				.info(new Info()
						.title("CustomerService API Documentation")
						.description("This is the documentation to define all end points of CustomerService App")
						.version("v0.0.1")
						.license(new License().name("Apache 2.0"))
						.contact(myContact)
				)
				.externalDocs(new ExternalDocumentation()
						.url("https://customerservice.com/docs")
						.description("This is the external documentation to refer CustomerService App")
				);
	}
}
