package it.uniroma3.siwsoftware.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siwsoftware.model.Software;
import it.uniroma3.siwsoftware.model.Sviluppatore;
import it.uniroma3.siwsoftware.service.SviluppatoreService;

@Controller 
public class SviluppatoreController {
	@Autowired SviluppatoreService sviluppatoreService;

	@GetMapping("/sviluppatore/{id}")
	public String getSviluppatore(@PathVariable("id") Long id, Model model) {
		model.addAttribute("sviluppatore", this.sviluppatoreService.findById(id));
		return "Sviluppatore.html";
	}
	
	@GetMapping("/admin/newSviluppatore")
	public String addSviluppatore(Model model) {
		model.addAttribute("sviluppatore", new Sviluppatore());
		return "admin/formNewSviluppatore.html";
	}
	
	@PostMapping("/admin/sviluppatore")
	public String newSviluppatore(@ModelAttribute("sviluppatore") Sviluppatore sviluppatore) {
		sviluppatoreService.save(sviluppatore);
		return "redirect:/sviluppatore/"+sviluppatore.getId();
	}
}
