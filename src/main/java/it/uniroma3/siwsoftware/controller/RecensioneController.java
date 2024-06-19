package it.uniroma3.siwsoftware.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siwsoftware.model.Recensione;
import it.uniroma3.siwsoftware.service.RecensioneService;
import it.uniroma3.siwsoftware.service.SoftwareService;

@Controller 
public class RecensioneController {
	@Autowired RecensioneService recensioneService;
	@Autowired SoftwareService softwareService;
	
	@PostMapping("/recensione/{id}")
	public String newRecensione(@PathVariable("id") Long id, @ModelAttribute("sviluppatore") Recensione recensione) {
		recensione.setSoftware(softwareService.findById(id));
		recensioneService.save(recensione);
		return "redirect:/software/"+recensione.getSoftware().getId();
	}
}
