package controllers;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import services.ResultsService;

@RestController
public class TestController {

	
	private ResultsService service;
	
	@GetMapping(path="/testConnection", produces = MediaType.APPLICATION_JSON_VALUE)  
	public ResponseEntity<String> getAllAccounts() throws IOException  
	{		
		service = new ResultsService();
		String results = service.testConnection();
		return ResponseEntity.status(HttpStatus.OK).body("Testing connection to data base: Trying to get current members: \n" + results);
	} 
}  