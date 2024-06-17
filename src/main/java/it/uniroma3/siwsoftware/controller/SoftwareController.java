package it.uniroma3.siwsoftware.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siwsoftware.model.Software;
import it.uniroma3.siwsoftware.service.SoftwareService;

@Controller 
public class SoftwareController {
	@Autowired SoftwareService softwareService;
	
	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("software", this.softwareService.findAll());
		return "index.html";
	}
	
	@GetMapping("/newSoftware")
	public String addSoftware(Model model) {
		model.addAttribute("software", new Software());
		return "formNewSoftware.html";
	}
	
	@PostMapping(value="/software")
	public String newSoftware(@ModelAttribute("software") Software software) {
		softwareService.save(software);
		return "redirect:software/"+software.getId();
	}
}
