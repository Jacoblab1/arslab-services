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
public class DatabaseSelectController {

	
	private DatabaseSelectService service;
	
	@GetMapping(path="/get/member/projects/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getMemberProjects(@PathVariable int id) throws IOException  
	{	
		service = new DatabaseSelectService();
		ArrayList<HashMap<String,String>> results = service.getMembersProjects(id);
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
	
	@GetMapping(path="/get/member/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getMember(@PathVariable int id) throws IOException  
	{	
		service = new DatabaseSelectService();
		ArrayList<HashMap<String,String>> results = service.getMember(id);
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
	@GetMapping(path="/get/project/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getProject(@PathVariable int id) throws IOException  
	{	
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
	@GetMapping(path="/get/project/members/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getProjectMembers(@PathVariable int id) throws IOException  
	{	
		service = new DatabaseSelectService();
		ArrayList<HashMap<String,String>> results = service.getProjectMembers(id);
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
	@GetMapping(path="/get/model/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getModel(@PathVariable int id) throws IOException  
	{	
		service = new DatabaseSelectService();
		ArrayList<HashMap<String,String>> results = service.getModel(id);
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
	@GetMapping(path="/get/model/files/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getModelFiles(@PathVariable int id) throws IOException  
	{	
		service = new DatabaseSelectService();
		ArrayList<HashMap<String,String>> results = service.getModelFiles(id);
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
	@GetMapping(path="/get/model/sourceFiles/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getModelSourceFiles(@PathVariable int id) throws IOException  
	{	
		service = new DatabaseSelectService();
		ArrayList<HashMap<String,String>> results = service.getModelSourceFiles(id);
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
	
	@GetMapping(path="/get/model/originalFiles/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getModelResultFiles(@PathVariable int id) throws IOException  
	{	
		service = new DatabaseSelectService();
		ArrayList<HashMap<String,String>> results = service.getModelResultFiles(id);
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
	  
	@GetMapping(path="/get/model/convertedFiles/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getModelConvertedFiles(@PathVariable int id) throws IOException  
	{	
		service = new DatabaseSelectService();
		ArrayList<HashMap<String,String>> results = service.getModelConvertedFiles(id);
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
	  
	@GetMapping(path="/get/file/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getFile(@PathVariable int id) throws IOException  
	{	
		service = new DatabaseSelectService();
		ArrayList<HashMap<String,String>> results = service.getFile(id);
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
	  
	@GetMapping(path="/get/member/files/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getMembersFiles(@PathVariable int id) throws IOException  
	{	
		service = new DatabaseSelectService();
		ArrayList<HashMap<String,String>> results = service.getMembersFiles(id);
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
	  
	@GetMapping(path="/get/file/member/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getFilesAuthors(@PathVariable int id) throws IOException  
	{	
		service = new DatabaseSelectService();
		ArrayList<HashMap<String,String>> results = service.getFilesAuthors(id);
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
	  
	@GetMapping(path="/get/project/model/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getProjectsModel(@PathVariable int id) throws IOException  
	{	
		service = new DatabaseSelectService();
		ArrayList<HashMap<String,String>> results = service.getProjectsModel(id);
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
	  
	@GetMapping(path="/get/model/project/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getModelsProjects(@PathVariable int id) throws IOException  
	{	
		service = new DatabaseSelectService();
		ArrayList<HashMap<String,String>> results = service.getModelsProjects(id);
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
	  
	@GetMapping(path="/get/project/documents/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getProjectsDocumentation(@PathVariable int id) throws IOException  
	{	
		service = new DatabaseSelectService();
		ArrayList<HashMap<String,String>> results = service.getProjectsDocumentation(id);
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
	  
	@GetMapping(path="/get/projectDocument/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getDocumentFile(@PathVariable int id) throws IOException  
	{	
		service = new DatabaseSelectService();
		ArrayList<HashMap<String,String>> results = service.getDocumentFile(id);
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
	 
	@GetMapping(path="/get/model/members/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getMembersFromModel(@PathVariable int id) throws IOException  
	{	
		service = new DatabaseSelectService();
		ArrayList<HashMap<String,String>> results = service.getMembersFromModel(id);
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
	  
	@GetMapping(path="/get/model/children/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getModelChildren(@PathVariable int id) throws IOException  
	{	
		service = new DatabaseSelectService();
		ArrayList<HashMap<String,String>> results = service.getModelChildren(id);
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
	  
	@GetMapping(path="/get/convertedResult/all", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getAllConvertedResults() throws IOException  
	{	
		service = new DatabaseSelectService();
		ArrayList<HashMap<String,String>> results = service.getAllConvertedResults();
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
	  
	@GetMapping(path="/get/member/all", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getAllMembers() throws IOException  
	{	
		service = new DatabaseSelectService();
		ArrayList<HashMap<String,String>> results = service.getAllMembers();
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
	 
	@GetMapping(path="/get/model/all", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getAllModels() throws IOException  
	{	
		service = new DatabaseSelectService();
		ArrayList<HashMap<String,String>> results = service.getAllModels();
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
	 
	@GetMapping(path="/get/modelFiles/all", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getAllModelFiles() throws IOException  
	{	
		service = new DatabaseSelectService();
		ArrayList<HashMap<String,String>> results = service.getAllModelFiles();
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
	
	@GetMapping(path="/get/originalResult/all", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getAllOriginalResults() throws IOException  
	{	
		service = new DatabaseSelectService();
		ArrayList<HashMap<String,String>> results = service.getAllOriginalResults();
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
	 
	@GetMapping(path="/get/projectDocument/all", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getAllProjectDocuments() throws IOException  
	{	
		service = new DatabaseSelectService();
		ArrayList<HashMap<String,String>> results = service.getAllProjectDocuments();
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
	 
	@GetMapping(path="/get/sourceFile/all", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getAllSourceFiles() throws IOException  
	{	
		service = new DatabaseSelectService();
		ArrayList<HashMap<String,String>> results = service.getAllSourceFiles();
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