package it.uniroma3.siwsoftware.controller;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siwsoftware.model.Software;
import it.uniroma3.siwsoftware.model.SoftwareHouse;
import it.uniroma3.siwsoftware.model.Sviluppatore;
import it.uniroma3.siwsoftware.service.SoftwareHouseService;
import it.uniroma3.siwsoftware.service.SoftwareService;
import it.uniroma3.siwsoftware.service.SviluppatoreService;

@Controller 
public class SviluppatoreController {
	@Autowired SviluppatoreService sviluppatoreService;
	@Autowired SoftwareService softwareService;
	@Autowired SoftwareHouseService softwareHouseService;

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
	
	@GetMapping("/admin/formAddSviluppatoreSoftware/{idSoftware}")
	public String formAddSviluppatoreSoftware(@PathVariable("idSoftware") Long idSoftware, 
			Model model) {
		Software software=this.softwareService.findById(idSoftware);
		model.addAttribute("software", this.softwareService.findById(idSoftware));
		model.addAttribute("sviluppatori", ((List<Sviluppatore>) this.sviluppatoreService.findAll()).removeAll(this.softwareService.findById(idSoftware).getSviluppatori()));
		model.addAttribute("operazione", "Software");
		return "admin/formAddSviluppatore.html";
	}
	
	@GetMapping("/admin/addSviluppatoreSoftware/{idSoftware}/{idSviluppatore}")
	public String addSviluppatoreSoftware(@PathVariable("idSoftware") Long idSoftware, 
			@PathVariable("idSviluppatore") Long idSviluppatore,
			Model model) {
		Sviluppatore sviluppatore=this.sviluppatoreService.findById(idSviluppatore);
		
		Software software=this.softwareService.findById(idSoftware);
		software.addSviluppatore(sviluppatore);
		softwareService.save(software);
		
		model.addAttribute("software", this.softwareService.findById(idSoftware));
		model.addAttribute("sviluppatori", ((List<Sviluppatore>) this.sviluppatoreService.findAll()).removeAll(this.softwareService.findById(idSoftware).getSviluppatori()));
		model.addAttribute("operazione", "Software");
		return "admin/formAddSviluppatore.html";
	}
	
	@GetMapping("/admin/removeSviluppatoreSoftware/{idSoftware}/{idSviluppatore}")
	public String removeSviluppatoreSoftware(@PathVariable("idSoftware") Long idSoftware, 
			@PathVariable("idSviluppatore") Long idSviluppatore,
			Model model) {
		Sviluppatore sviluppatore=sviluppatoreService.findById(idSviluppatore);
		Software software=this.softwareService.findById(idSoftware);
		
		software.removeSviluppatore(sviluppatore);
		softwareService.save(software);
		
		model.addAttribute("software", this.softwareService.findById(idSoftware));
		model.addAttribute("sviluppatori", ((List<Sviluppatore>) this.sviluppatoreService.findAll()).removeAll(this.softwareService.findById(idSoftware).getSviluppatori()));
		model.addAttribute("operazione", "Software");
		return "admin/formAddSviluppatore.html";
	}
	
	
	
	
	
	@GetMapping("/admin/formAddSviluppatoreSoftwareHouse/{idSoftwareHouse}")
	public String formAddSviluppatoreSoftwareHouse(@PathVariable("idSoftwareHouse") Long idSoftwareHouse, 
			Model model) {
		SoftwareHouse softwareHouse=this.softwareHouseService.findById(idSoftwareHouse);
		model.addAttribute("software", this.softwareHouseService.findById(idSoftwareHouse));
		model.addAttribute("sviluppatori", ((List<Sviluppatore>) this.sviluppatoreService.findAll()).removeAll(this.softwareHouseService.findById(idSoftwareHouse).getSviluppatori()));
		model.addAttribute("operazione", "SoftwareHouse");
		return "admin/formAddSviluppatore.html";
	}
	
	@GetMapping("/admin/addSviluppatoreSoftwareHouse/{idSoftwareHouse}/{idSviluppatore}")
	public String addSviluppatoreSoftwareHouse(@PathVariable("idSoftwareHouse") Long idSoftwareHouse, 
			@PathVariable("idSviluppatore") Long idSviluppatore,
			Model model) {
		Sviluppatore sviluppatore=this.sviluppatoreService.findById(idSviluppatore);
		
		SoftwareHouse softwareHouse=this.softwareHouseService.findById(idSoftwareHouse);

		softwareHouse.addSviluppatore(sviluppatore);
		softwareHouseService.save(softwareHouse);
		
		model.addAttribute("software", this.softwareHouseService.findById(idSoftwareHouse));
		model.addAttribute("sviluppatori", ((List<Sviluppatore>) this.sviluppatoreService.findAll()).removeAll(this.softwareHouseService.findById(idSoftwareHouse).getSviluppatori()));
		model.addAttribute("operazione", "SoftwareHouse");
		return "admin/formAddSviluppatore.html";
	}
	
	@GetMapping("/admin/removeSviluppatoreSoftwareHouse/{idSoftwareHouse}/{idSviluppatore}")
	public String removeSviluppatoreSoftwareHouse(@PathVariable("idSoftwareHouse") Long idSoftwareHouse, 
			@PathVariable("idSviluppatore") Long idSviluppatore,
			Model model) {
		Sviluppatore sviluppatore=sviluppatoreService.findById(idSviluppatore);
		SoftwareHouse softwareHouse=this.softwareHouseService.findById(idSoftwareHouse);
		
		softwareHouse.removeSviluppatore(sviluppatore);
		softwareHouseService.save(softwareHouse);
		
		model.addAttribute("software", this.softwareHouseService.findById(idSoftwareHouse));
		model.addAttribute("sviluppatori", ((List<Sviluppatore>) this.sviluppatoreService.findAll()).removeAll(this.softwareHouseService.findById(idSoftwareHouse).getSviluppatori()));
		model.addAttribute("operazione", "SoftwareHouse");
		return "admin/formAddSviluppatore.html";
	}
}
