package controllers;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import components.NewMember;
import components.AddMemberToProject;
import components.AddModelToProject;
import components.GetByIdObject;
import components.InsertNewProject;
import services.DatabaseInsertServices;
import services.DatabaseSelectService;
import controllers.DatabaseSelectController;


@Controller
public class FrontEndController {

	private DatabaseSelectService service = new DatabaseSelectService();
	private DatabaseInsertServices insertServices = new DatabaseInsertServices();
	
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
			
		//	int results = service.addMemberAccount(lastname,firstname);
		//	newMember.setId(results);
			
			return "result";
	}
	
	// this wil be the inital function called when loading the page
	@GetMapping("/get/project")
	public String testGetProject(Model model) {
		// the name here will be its name in the html docutment ${"GetByIdObject"}
		model.addAttribute("getByIdObject", new GetByIdObject());
	// return the html page that contains to form 	..... html file has to be in resources/templates
		return "getProject";
	}
	
	@GetMapping("/get/project/{id}")
	public String GetProject(@PathVariable int id, Model model) {
		
		HashMap<String, String> project = service.getProject(id).get(0);
		String projectName = project.get("projectname");
		int projectID = Integer.parseInt(project.get("projectid"));
		String projectDescription = project.get("projectdescription");
		String projectDate = project.get("creationdate");
		
		model.addAttribute("projectName", projectName);
		model.addAttribute("projectID", projectID);
		model.addAttribute("projectDescription", projectDescription);
		model.addAttribute("projectDate", projectDate);
		model.addAttribute("projectMembers", service.getProjectMembers(id));
		model.addAttribute("projectModels", service.getProjectsModel(id));
		// return the name of the html file you want to return to..... html file has to be in resources/templates
		return "returnProject";
	
	}
	

	
	// this is the function that will be called when the form is submited
	@PostMapping("/get/project")
	public String testGetProjectSubmit(@ModelAttribute GetByIdObject getByIdObject, Model model) throws IOException{
			int id = getByIdObject.getId();
			return "redirect:/get/project/" + id;
	}
	
	
	@GetMapping("/insert/project")
	public String testInsertProject(Model model) {
		// the name here will be its name in the html docutment ${"GetByIdObject"}
		String greeting = "Hello, welcome to /test/insert/project";
		model.addAttribute("insertNewProject", new InsertNewProject());
	// return the html page that contains to form 	..... html file has to be in resources/templates
		return "insertNewProject";
	}
	
	@PostMapping("/insert/project")
	public String testInsertProjectPost(@ModelAttribute InsertNewProject insertNewProject, Model model) {
		
		String des = insertNewProject.getProjectDescription();
		Date cre = insertNewProject.getCreationDate();
		String name = insertNewProject.getProjectName();
		System.out.print(cre);
		int newId = insertServices.insertProject(name,des,cre);
		if(newId != 0) {
			insertNewProject.setProjectId(newId);
			System.out.println(insertNewProject.getProjectId());
		}else {
			System.out.println(newId);
		}
		model.addAttribute("insertNewProject", insertNewProject);
		
		return "redirect:/insert/membersToProject/" + newId; 
	}
	
	@GetMapping("/insert/membersToProject/{id}")
	public String addMembersToProject(@PathVariable int id, Model model) {
		
		HashMap<String, String> project = service.getProject(id).get(0);
		String projectName = project.get("projectname");
		String fileTitle = "Form: Add Members to '<em>" + projectName + "</em>' (ProjectId:" + id + ")";
		model.addAttribute("fileTitle", fileTitle);
		model.addAttribute("projectID", id);
		model.addAttribute("addMemberToProject", new AddMemberToProject(id,projectName));
		model.addAttribute("currentMembers", service.getProjectMembers(id));
		model.addAttribute("membersMap", service.getAllMembersNotInProject(id));
	// return the html page that contains to form 	..... html file has to be in resources/templates
		return "addMembersToProject";
	}
	
	
	
	
	@PostMapping("/insert/membersToProject")
	public String addMembersToProject(
			@RequestParam(value = "add", required = false) String add,
			@ModelAttribute AddMemberToProject addMemberToProject, Model model) {
		int projectId = addMemberToProject.getProjectId();
		int memberId = addMemberToProject.getMemberId();
		if(add != null) {
			if(add.equals("Add")) {
			insertServices.insertProjectMembers(memberId, projectId);
			return "redirect:/insert/membersToProject/" + projectId;
			}
		}
		return "redirect:/insert/modelsToProject/" + projectId;
	}
	

	@PostMapping("/insert/modelsToProject")
	public String addModelsToProject(
			@RequestParam(value = "add", required = false) String add,
			@ModelAttribute AddModelToProject addModelToProject, Model model) {
		
		int projectId = addModelToProject.getProjectId();
		int modelId = addModelToProject.getModelId();
		if(add != null) {
			if(add.equals("Add")) {
				insertServices.insertProjectModel(projectId, modelId);
			return "redirect:/insert/modelsToProject/" + projectId;
			}
		}
		return "redirect:/get/project/" + projectId;
	}
	
	
	@GetMapping("/insert/modelsToProject/{id}")
	public String addModelsToProject(@PathVariable int id, Model model) {
		
		HashMap<String, String> project = service.getProject(id).get(0);
		String projectName = project.get("projectname");
		String fileTitle = "Form: Add Models to '<em>" + projectName + "</em>' (ProjectId:" + id + ")";
		model.addAttribute("fileTitle", fileTitle);
		model.addAttribute("projectID", id);
		model.addAttribute("addModelToProject", new AddModelToProject(id,projectName));
		model.addAttribute("currentModels", service.getProjectsModel(id));
		model.addAttribute("modelsMap", service.getAllModelsNotInProject(id));
	// return the html page that contains to form 	..... html file has to be in resources/templates
		return "addModelsToProject";
	}
	
	
	
	

}



