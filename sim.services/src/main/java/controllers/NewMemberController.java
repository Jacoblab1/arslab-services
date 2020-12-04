package controllers;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import components.NewMember;
import services.DatabaseSelectService;

@Controller
public class NewMemberController {

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
			
			int results = service.addMemberAccount(lastname,firstname);
			newMember.setId(results);
			
			return "result";
	}

}



