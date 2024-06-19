package it.uniroma3.siwsoftware.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siwsoftware.model.Recensione;
import it.uniroma3.siwsoftware.model.Software;
import it.uniroma3.siwsoftware.service.RecensioneService;
import it.uniroma3.siwsoftware.service.SoftwareService;

@Controller 
public class SoftwareController {
	@Autowired SoftwareService softwareService;
	@Autowired RecensioneService recensioneService;
	
	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("software", this.softwareService.findAll());
		return "index.html";
	}
	
	@GetMapping("/admin/newSoftware")
	public String addSoftware(Model model) {
		model.addAttribute("software", new Software());
		return "admin/formNewSoftware.html";
	}
	
	@PostMapping("/admin/software")
	public String newSoftware(@ModelAttribute("software") Software software) {
		softwareService.save(software);
		return "redirect:/software/"+software.getId();
	}
	
	@GetMapping("/software/{id}")
	public String getSoftware(@PathVariable("id") Long id, Model model) {
		model.addAttribute("software", this.softwareService.findById(id));
		model.addAttribute("recensione", new Recensione());
		return "software.html";
	}
}
