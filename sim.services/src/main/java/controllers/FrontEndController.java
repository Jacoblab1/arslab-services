package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import components.NewMember;
import components.GetByIdObject;
import components.NewProject;
import services.DatabaseSelectService;

@Controller
public class FrontEndController {

	private DatabaseSelectService service;
	
	@GetMapping("/newMember")
	public String greetingForm(Model model) {
		model.addAttribute("newMember", new NewMember());
		return "newMember";
	}

	@PostMapping("/newMember")
	public String greetingSubmit(@ModelAttribute NewMember newMember, Model model) throws IOException{
			model.addAttribute("newMember", newMember);
			String firstname = newMember.getFirstname();
			String lastname = newMember.getLastname();
			service = new DatabaseSelectService();
			
		//	int results = service.addMemberAccount(lastname,firstname);
		//	newMember.setId(results);
			
			return "result";
	}
	
	// this wil be the inital function called when loading the page
	@GetMapping("/test/get/project")
	public String testGetProject(Model model) {
		// the name here will be its name in the html docutment ${"GetByIdObject"}
		model.addAttribute("getByIdObject", new GetByIdObject());
	// return the html page that contains to form 	..... html file has to be in resources/templates
		return "getProject";
	}

	
	// this is the function that will be called when the form is submited
	@PostMapping("/test/get/project")
	public String testGetProjectSubmit(@ModelAttribute GetByIdObject getByIdObject, Model model) throws IOException{
			
		
			model.addAttribute("getProject", getByIdObject);
			int id = getByIdObject.getId();
			service = new DatabaseSelectService();
			
			ArrayList results = service.getProject(id);
			
			// doesnt do anything, just for turning the hashmap into a string if you need to test
			int count = 0;
			String result = "";
			for(int i = 0; i < results.size(); i++) {
				HashMap<String,String> row = (HashMap) results.get(i);
				result += "Row:" + count + "->";
				
				for(HashMap.Entry<String, String> r : row.entrySet()) {
					result += " " + r.getKey() + ": " + r.getValue();
				}
				result += " \n";
				count++;
			}
			
			getByIdObject.setResults(results);
		//	newMember.setId(results);
			
			// return the name of the html file you want to return to..... html file has to be in resources/templates
			return "returnProject";
	}
	@GetMapping("/newProject")
	public String newObject(Model model) {
		model.addAttribute("newProject", new NewProject());
		return "createNewProject";
	}

	@PostMapping("/newProject")
	public String newObjectSubmit(@ModelAttribute NewProject newProject, Model model) throws IOException{
		model.addAttribute("newProject", newProject);
		String projectid = newProject.getProjectId();
		String projectname = newProject.getProjectName();
		String projectdescription = newProject.getProjectDescription();
		service = new DatabaseSelectService();

		//	int results = service.addMemberAccount(lastname,firstname);
		//	newMember.setId(results);

		return "newProject";
	}

}



