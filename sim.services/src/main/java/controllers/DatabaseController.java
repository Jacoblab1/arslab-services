package controllers;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import services.DatabaseService;

@RestController
public class DatabaseController {

	
	private DatabaseService service;
	
	@GetMapping(path="/testConnection", produces = MediaType.APPLICATION_JSON_VALUE)  
	public ResponseEntity<String> getAllAccounts() throws IOException  
	{		
		service = new DatabaseService();
		String results = service.testConnection();
		return ResponseEntity.status(HttpStatus.OK).body("Testing connection to data base: Trying to get current members: \n" + results);
	} 
	
	//Get all information about the author by /lastname/firstname
	@GetMapping(path="/get/author/{lastname}/{firstname}", produces = MediaType.APPLICATION_JSON_VALUE)  
	public ResponseEntity<String> getAuthorAccount(@PathVariable String lastname,@PathVariable String firstname) throws IOException  
	{		
		service = new DatabaseService();
		String results = service.getAuthorAccount(firstname,lastname);
		return ResponseEntity.status(HttpStatus.OK).body("Getting your results:\n" + results);
	}
	
	@GetMapping(path="/get/author/projects/{lastname}/{firstname}", produces = MediaType.APPLICATION_JSON_VALUE)  
	public ResponseEntity<String> getAuthorProjects(@PathVariable String lastname,@PathVariable String firstname) throws IOException  
	{		
		service = new DatabaseService();
		String results = service.getAuthorProjects(lastname,firstname);
		return ResponseEntity.status(HttpStatus.OK).body("Getting your results:\n" + results);
	} 
	
	
}  