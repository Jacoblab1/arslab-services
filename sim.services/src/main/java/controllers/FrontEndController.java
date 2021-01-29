package controllers;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import components.NewMember;
import components.runSimulationModel;
import components.AddMemberToProject;
import components.AddModelToProject;
import components.GetByIdObject;
import components.InsertNewProject;
import services.DatabaseInsertServices;
import services.DatabaseSelectService;
import controllers.DatabaseSelectController;
import controllers.S3Controller;
import sun.tools.jar.CommandLine;



@EnableAsync
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
	@GetMapping("/get/projects")
	public String testGetProject(Model model) {
		// the name here will be its name in the html docutment ${"GetByIdObject"}
		ArrayList<HashMap<String, String>> projects = service.getAllProjects();
		model.addAttribute("getByIdObject", new GetByIdObject());
		model.addAttribute("projectsArrayList", projects);
	// return the html page that contains to form 	..... html file has to be in resources/templates
		return "getProjects";
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
		return "getProject";
	
	}
	

	
	// this is the function that will be called when the form is submited
	@PostMapping("/get/projects")
	public String testGetProjectSubmit(
			@RequestParam(value = "add", required = false) String add,
			@ModelAttribute GetByIdObject getByIdObject, Model model) throws IOException{
			int id = getByIdObject.getId();
			
			if(add != null) {
				if(add.equals("View All Models")) {
				return "redirect:/get/models";
				}
			}
			if(id == -1) {
				return "redirect:/get/projects";
			}
			return "redirect:/get/project/" + id;
	}
	
	
	@GetMapping("/get/models")
	public String GetModels(Model model) {
		// the name here will be its name in the html docutment ${"GetByIdObject"}
		ArrayList<HashMap<String, String>> models = service.getAllModels();
		model.addAttribute("getByIdObject", new GetByIdObject());
		model.addAttribute("modelsArrayList", models);
	// return the html page that contains to form 	..... html file has to be in resources/templates
		return "getModels";
	}
	
	@PostMapping("/get/models")
	public String GetModels(
		@RequestParam(value = "add", required = false) String add,
		@ModelAttribute GetByIdObject getByIdObject, Model model) throws IOException{
		
		int id = getByIdObject.getId();	
		if(add != null) {
			if(add.equals("View All Projects")) {
			return "redirect:/get/projects";
			}
		}
		if(id == -1) {
			return "redirect:/get/models";
		}
		return "redirect:/get/model/" + id;
	}
	
	@GetMapping("/get/model/{id}")
	public String GetModel(@PathVariable int id, Model model) {
		
		HashMap<String, String> mod = service.getModel(id).get(0);
		String modelName = mod.get("modelname");
		int modelID = Integer.parseInt(mod.get("modelid"));
		String modelDescription = mod.get("description");
		String modelDate = mod.get("creationdate");
		String modelType = mod.get("modeltype");
		String sourceLanguage = mod.get("sourcelanguage");
		
		
		model.addAttribute("modelName", modelName);
		model.addAttribute("modelID", modelID);
		model.addAttribute("modelDescription", modelDescription);
		model.addAttribute("modelDate", modelDate);
		model.addAttribute("modelType", modelType);
		model.addAttribute("sourceLanguage", sourceLanguage);
		
		ArrayList<HashMap<String,String>> parentChildren = service.getModelChildren(modelID);
		HashMap<Integer, HashMap<String,String>> map = new HashMap<Integer, HashMap<String,String>>();
		HashMap<Integer,ArrayList<Integer>> h = new HashMap<Integer,ArrayList<Integer>>();
		if(parentChildren.size() != 0) {		
			for(int i = 0; i < parentChildren.size(); i++) {
		
				HashMap<String, String> parentChild = parentChildren.get(i);
				int childID = Integer.parseInt(parentChild.get("childid"));
				int parentID = Integer.parseInt(parentChild.get("parentid"));
				HashMap<String, String> child = service.getModel(childID).get(0);
				
				ArrayList<Integer> parent = h.get(parentID);
				if(parent == null) {
					ArrayList<Integer> childList = new ArrayList<Integer>();
					childList.add(childID);
					h.put(parentID, childList);
					
				}else {
					parent.add(childID);
					h.put(parentID, parent);
				}
				
				
				
				map.put(childID, child);
			}
			//System.out.print(h.get(65));
			m(h,h.get(modelID), 0, map);
		}
		
//		for (Map.Entry<Integer, HashMap<String,String>> e : treeMap.entrySet()) {
//            System.out.println(e.getKey() 
//                               + " "
//                               + e.getValue().get("modelname")); 
//		}
		model.addAttribute("modelChildrenMap", h);
		model.addAttribute("modelSubModels", map);
		model.addAttribute("modelSize", map.size());
		
		S3Controller s3 = new S3Controller();
		ArrayList<HashMap<String,String>> files = service.getModelFiles(modelID);
		for(int i = 0; i < files.size(); i++) {
			System.out.println(files.get(i).get("location"));
			String url = s3.getObjectUrl(files.get(i).get("location"));
			files.get(i).put("location", url);
		}
		
		ArrayList<HashMap<String,String>> modelSourceFiles = service.getModelSourceFiles(modelID);
		for(int i = 0; i < modelSourceFiles.size(); i++) {
			System.out.println(modelSourceFiles.get(i).get("location"));
			String url = s3.getObjectUrl(modelSourceFiles.get(i).get("location"));
			modelSourceFiles.get(i).put("location", url);
		}
		
		ArrayList<HashMap<String,String>> modelResultFiles = service.getModelResultFiles(modelID);
		for(int i = 0; i < modelResultFiles.size(); i++) {
			System.out.println(modelResultFiles.get(i).get("location"));
			String url = s3.getObjectUrl(modelResultFiles.get(i).get("location"));
			modelResultFiles.get(i).put("location", url);
		}
		
		ArrayList<HashMap<String,String>> modelConvertedFiles = service.getModelConvertedFiles(modelID);
		for(int i = 0; i < modelConvertedFiles.size(); i++) {
			System.out.println(modelConvertedFiles.get(i).get("location"));
			String url = s3.getObjectUrl(modelConvertedFiles.get(i).get("location"));
			modelConvertedFiles.get(i).put("location", url);
		}
		
		
		
		model.addAttribute("modelAuthors", service.getMembersFromModel(modelID));
		model.addAttribute("modelProject", service.getModelsProjects(modelID));
		model.addAttribute("modelAllFiles", files);
		model.addAttribute("modelSourceFiles", modelSourceFiles);
		model.addAttribute("modelResultFiles", modelResultFiles);
		model.addAttribute("modelConvertedFiles", modelConvertedFiles);
		// return the name of the html file you want to return to..... html file has to be in resources/templates
		return "displayModel";
	
	}
	
	
	public void m(HashMap<Integer,ArrayList<Integer>> h, ArrayList<Integer> parent, int level, HashMap<Integer, HashMap<String,String>> map) {
		
		for(int i = 0; i < parent.size(); i++) {
			int childId = parent.get(i);
			ArrayList<Integer> childsChildren = h.get(childId);
			if(childsChildren == null) {
				
				
			}else {
				m(h,childsChildren, level + 1,map);
				
			}
		}
		
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
	
	
	@GetMapping("/run/simulation")
	public String runSimulation(Model model) {
		
		model.addAttribute("runSimulation", new runSimulationModel());
		model.addAttribute("models", service.getModel(70));
		model.addAttribute("modelsSimulations", service.getModelSourceFiles(70));
	// return the html page that contains to form 	..... html file has to be in resources/templates
		return "runModel.html";
	}
	
	
	@GetMapping("/run")
	public ResponseEntity<String> run(Model model) {
		
		ArrayList<String> output =  application.runSimulation.run();
		String h = " ";
		for(String o : output) {
			 h += o + "<br><br><br>";
		}
	// return the html page that contains to form 	..... html file has to be in resources/templates
		return ResponseEntity.status(HttpStatus.OK).body(h.toString());
	}
	
	
	@RequestMapping(value = "/zip/files", method = RequestMethod.POST)
	@ResponseBody
	public String zipFiles(HttpServletRequest request, Model model) {
		//model.addAttribute("link",);
		System.out.println(request.getParameter("modelId"));
		S3Controller s3 = new S3Controller();
		byte[] b =  s3.test();
		
		//Convert.ToBase64String
		//Encoder encoder = Base64.getEncoder();
		//String encodedString = encoder.encodeToString(b);
		//System.out.print(encodedString);
		return new String(Base64.getEncoder().encode(b));
	}
	
	

	
	
	
	

}



