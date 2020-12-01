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
	
	@GetMapping(path="/Hello", produces = MediaType.APPLICATION_JSON_VALUE)  
	public ResponseEntity<String> getAllAccounts() throws IOException  
	{		
		service = new ResultsService();
		String x = service.returnhello();
		return ResponseEntity.status(HttpStatus.OK).body(x);
	} 
}  