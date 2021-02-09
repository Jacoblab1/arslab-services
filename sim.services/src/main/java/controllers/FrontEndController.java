package controllers;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.concurrent.Executor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import components.*;
import org.springframework.web.bind.annotation.ResponseBody;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import services.DatabaseInsertServices;
import services.DatabaseSelectService;
import controllers.DatabaseSelectController;
import application.Application;
import controllers.S3Controller;
import sun.tools.jar.CommandLine;


@Configuration
@EnableAsync
@Controller
public class FrontEndController implements WebMvcConfigurer {
	
	
	

	private DatabaseSelectService service = new DatabaseSelectService();
	private DatabaseInsertServices insertServices = new DatabaseInsertServices();

	
	
	@Override
    public void addCorsMappings(CorsRegistry registry) {
		System.out.println("here");;
        registry.addMapping("/get/model/simulation/{id}").allowedOrigins("*").allowedHeaders("*").allowedMethods("*");
    }
	
	@GetMapping("/get/model/simulation/{id}")
	@ResponseBody
	public ResponseEntity<HashMap<String,String>> getSimulationJSON(@PathVariable String id) {
		return ResponseEntity.ok().header("Access-Control-Allow-Headers", "Content-Disposition")
				.header("Access-Control-Expose-Headers", "Content-Disposition").body(ModelProcessorService.getSimulation(id));
	   	
	}
	
	
	@GetMapping("/")
	public String homePage(Model model) {
		ArrayList<HashMap<String, String>> models = service.getAllModels();
		model.addAttribute("getByIdObject", new GetByIdObject());
		model.addAttribute("modelsArrayList", models);
		return "getModels";
	}

	@PostMapping("/newMember")
	public String greetingSubmit(@ModelAttribute NewMember newMember, Model model) throws IOException{
			model.addAttribute("newMember", newMember);
			String firstname = newMember.getFirstname();
			String lastname = newMember.getLastname();
			return "result";
	}
	
	// this wil be the inital function called when loading the page
	@GetMapping("/get/projects")
	public String testGetProject(Model model) {
		ArrayList<HashMap<String, String>> projects = service.getAllProjects();
		model.addAttribute("getByIdObject", new GetByIdObject());
		model.addAttribute("projectsArrayList", projects);
		return "getProjects";
	}
	
	@GetMapping("/get/project/{id}")
	public String GetProject(@PathVariable int id, Model model) {
		
		HashMap<String, String> project = service.getProject(id).get(0);
		String projectName = project.get("projectname");
		int projectID = Integer.parseInt(project.get("projectid"));
		String projectDescription = project.get("projectdescription");
		String projectDate = project.get("creationdate");
		ArrayList<HashMap<String,String>> files = FilesProcessorService.updateFilesLocation(service.getProjectsDocumentation(projectID));
		
		model.addAttribute("projectFiles", files);
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
		ArrayList<HashMap<String, String>> models = service.getAllModels();
		model.addAttribute("getByIdObject", new GetByIdObject());
		model.addAttribute("modelsArrayList", models);
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
		
		HashMap<Integer,ArrayList<HashMap<Integer, HashMap<String,String>>>> modelParentChildMap = ModelProcessorService.sortParentChildRelationship(service.getModelChildren(modelID));
		ArrayList<HashMap<String,String>> files = FilesProcessorService.updateFilesLocation(service.getModelFiles(modelID));
		ArrayList<HashMap<String,String>> modelSourceFiles = FilesProcessorService.updateFilesLocation(service.getModelSourceFiles(modelID));
		ArrayList<HashMap<String,String>> modelResultFiles = FilesProcessorService.updateFilesLocation(service.getModelResultFiles(modelID));
		ArrayList<HashMap<String,String>> modelConvertedFiles = service.getModelConvertedFiles(modelID);
		HashMap<String,ArrayList<HashMap<String,String>>>  modelSimulations = FilesProcessorService.sortFilesByAttribute(modelConvertedFiles, "simulationid");

		
		model.addAttribute("modelName", modelName);
		model.addAttribute("modelID", modelID);
		model.addAttribute("modelDescription", modelDescription);
		model.addAttribute("modelDate", modelDate);
		model.addAttribute("modelType", modelType);
		model.addAttribute("sourceLanguage", sourceLanguage);
		model.addAttribute("modelParentChildMap",modelParentChildMap);
		model.addAttribute("modelAllFiles", files);
		model.addAttribute("modelSourceFiles", modelSourceFiles);
		model.addAttribute("modelResultFiles", modelResultFiles);
		model.addAttribute("modelConvertedFiles", modelConvertedFiles);
		model.addAttribute("modelAuthors", service.getMembersFromModel(modelID));
		model.addAttribute("modelProject", service.getModelsProjects(modelID));
		model.addAttribute("modelSimulations", ModelProcessorService.parseModelSimulations(modelSimulations));
		
		return "displayModel";
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
		return "addModelsToProject";
	}
	
	
	@GetMapping("/run/simulation")
	public String runSimulation(Model model) {
		
		model.addAttribute("runSimulation", new runSimulationModel());
		model.addAttribute("models", service.getModel(70));
		model.addAttribute("modelsSimulations", service.getModelSourceFiles(70));
		return "runModel.html";
	}
	
	
	@GetMapping("/run")
	public ResponseEntity<String> run(Model model) {
		
		ArrayList<String> output =  application.runSimulation.run();
		String h = " ";
		for(String o : output) {
			 h += o + "<br><br><br><br>";
		}
		return ResponseEntity.status(HttpStatus.OK).body(h.toString());
	}

	@RequestMapping(value = "/zip/files", method = RequestMethod.POST)
	@ResponseBody
	public String zipFiles(HttpServletRequest request, Model model) {
		String name = request.getParameter("Name");
		return new String(Base64.getEncoder().encode(FilesProcessorService.getStoredFile(name)));
	}
	
	@RequestMapping(value = "/zipModel", method = RequestMethod.POST)
	@ResponseBody
	public String zipModel(HttpServletRequest request, Model model) {
    	
		String id = request.getParameter("id");
		int modelID = Integer.parseInt(id);

    	ArrayList<HashMap<String,String>> files = service.getModelFiles(modelID);
    	ArrayList<HashMap<String,String>> modelSourceFiles = service.getModelSourceFiles(modelID);
		ArrayList<HashMap<String,String>> modelResultFiles = service.getModelResultFiles(modelID);
		ArrayList<HashMap<String,String>> modelConvertedFiles = service.getModelConvertedFiles(modelID);

		FilesProcessorService.addStoredFile("modelAllFiles", FilesProcessorService.zipFiles(files));
		FilesProcessorService.addStoredFile("modelSourceFiles", FilesProcessorService.zipFiles(modelSourceFiles));
		FilesProcessorService.addStoredFile("modelResultFiles", FilesProcessorService.zipFiles(modelResultFiles));
		FilesProcessorService.addStoredFile("modelConvertedFiles", FilesProcessorService.zipFiles(modelConvertedFiles));
    	return "done";
    	
    }
	
	@RequestMapping(value = "/zipProject", method = RequestMethod.POST)
	@ResponseBody
	public String zipProject(HttpServletRequest request, Model model) {
		String id = request.getParameter("id");
		int projectid = Integer.parseInt(id);
    	ArrayList<HashMap<String,String>> files = FilesProcessorService.updateFilesLocation(service.getProjectsDocumentation(projectid));
		FilesProcessorService.addStoredFile("projectAllFiles", FilesProcessorService.zipFiles(files));
    	return "done";	
    }

}



