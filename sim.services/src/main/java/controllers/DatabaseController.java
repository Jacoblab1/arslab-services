package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import services.DatabaseSelectService;

@RestController
public class DatabaseController {

	
	private DatabaseSelectService service;
	
	@GetMapping(path="/testConnection", produces = MediaType.APPLICATION_JSON_VALUE)  
	public ResponseEntity<String> getAllAccounts() throws IOException  
	{		
		service = new DatabaseSelectService();
		ArrayList<HashMap<String, String>> results = service.testConnection();
		return ResponseEntity.status(HttpStatus.OK).body("Testing connection to data base: Trying to get current members: \n" + results);
	} 
	
	//Get all information about the author by /lastname/firstname
	@GetMapping(path="/get/author/{lastname}/{firstname}", produces = MediaType.APPLICATION_JSON_VALUE)  
	public ResponseEntity<String> getAuthorAccount(@PathVariable String lastname,@PathVariable String firstname) throws IOException  
	{		
		service = new DatabaseSelectService();
		ArrayList<HashMap<String, String>> results = service.getAuthorAccount(lastname, firstname);
		return ResponseEntity.status(HttpStatus.OK).body("Getting your results:\n" + results);
	}
	
	@GetMapping(path="/get/author/projects/{id}", produces = MediaType.APPLICATION_JSON_VALUE)  
	public ResponseEntity<String> getAuthorProjects(@PathVariable int id) throws IOException  
	{	
		System.out.println(id);
		service = new DatabaseSelectService();
		ArrayList<HashMap<String, String>> results = service.getMembersProjects(id);
		return ResponseEntity.status(HttpStatus.OK).body("Getting your results:\n" + results);
	} 
	
	@GetMapping(path="/get/project/{id}", produces = MediaType.APPLICATION_JSON_VALUE)  
	public ResponseEntity<String> getProject(@PathVariable int id) throws IOException  
	{	
		//System.out.println(id);
		service = new DatabaseSelectService();
		ArrayList<HashMap<String,String>> results = service.getProject(id);
		int count = 0;
		String result = "";
		for(int i = 0; i < results.size(); i++) {
			HashMap<String,String> row = results.get(i);
			result += "Row:" + count + "->";
			
			for(HashMap.Entry<String, String> r : row.entrySet()) {
				result += " " + r.getKey() + ": " + r.getValue();
			}
			result += " \n";
			count++;
		}
		return ResponseEntity.status(HttpStatus.OK).body("Getting your results:\n" + result );
	} 
	
	
}  