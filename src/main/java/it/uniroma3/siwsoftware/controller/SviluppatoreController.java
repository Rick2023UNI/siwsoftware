package it.uniroma3.siwsoftware.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
	
	@GetMapping("/admin/formNewSviluppatore")
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
		model.addAttribute("software", software); 
		ArrayList<Sviluppatore> sviluppatori=(ArrayList<Sviluppatore>) this.sviluppatoreService.findAll();
		sviluppatori.removeAll(software.getSviluppatori());
		model.addAttribute("sviluppatori", sviluppatori);
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
		return "redirect:/admin/formAddSviluppatoreSoftware/"+idSoftware;
	}
	
	@GetMapping("/admin/removeSviluppatoreSoftware/{idSoftware}/{idSviluppatore}")
	public String removeSviluppatoreSoftware(@PathVariable("idSoftware") Long idSoftware, 
			@PathVariable("idSviluppatore") Long idSviluppatore,
			Model model) {
		Sviluppatore sviluppatore=sviluppatoreService.findById(idSviluppatore);
		Software software=this.softwareService.findById(idSoftware);
		
		software.removeSviluppatore(sviluppatore);
		softwareService.save(software);
		
		return "redirect:/admin/formAddSviluppatoreSoftware/"+idSoftware;
	}
	
	
	
	
	
	@GetMapping("/admin/formAddSviluppatoreSoftwareHouse/{idSoftwareHouse}")
	public String formAddSviluppatoreSoftwareHouse(@PathVariable("idSoftwareHouse") Long idSoftwareHouse, 
			Model model) {
		SoftwareHouse softwareHouse=this.softwareHouseService.findById(idSoftwareHouse);
		ArrayList<Sviluppatore> sviluppatori=(ArrayList<Sviluppatore>) (this.sviluppatoreService.findAll());
		sviluppatori.removeAll(softwareHouse.getSviluppatori());
		model.addAttribute("software", softwareHouse);
		model.addAttribute("sviluppatori", sviluppatori);
		model.addAttribute("operazione", "SoftwareHouse");
		return "admin/formAddSviluppatore.html";
	}
	
	@GetMapping("/admin/addSviluppatoreSoftwareHouse/{idSoftwareHouse}/{idSviluppatore}")
	public String addSviluppatoreSoftwareHouse(@PathVariable("idSoftwareHouse") Long idSoftwareHouse, 
			@PathVariable("idSviluppatore") Long idSviluppatore,
			Model model) {
		Sviluppatore sviluppatore=this.sviluppatoreService.findById(idSviluppatore);
		
		SoftwareHouse softwareHouse=this.softwareHouseService.findById(idSoftwareHouse);
		//Stranamente funziona solo modificando la SoftwareHouse dello sviluppatore, 
		//il cambiamento viene effettuato
		sviluppatore.setSoftwareHouse(softwareHouse);
		sviluppatoreService.save(sviluppatore);
		
		
		return "redirect:/admin/formAddSviluppatoreSoftwareHouse/"+idSoftwareHouse;
	}
	
	@GetMapping("/admin/removeSviluppatoreSoftwareHouse/{idSoftwareHouse}/{idSviluppatore}")
	public String removeSviluppatoreSoftwareHouse(@PathVariable("idSoftwareHouse") Long idSoftwareHouse, 
			@PathVariable("idSviluppatore") Long idSviluppatore,
			Model model) {
		Sviluppatore sviluppatore=sviluppatoreService.findById(idSviluppatore);
		SoftwareHouse softwareHouse=this.softwareHouseService.findById(idSoftwareHouse);
		
		sviluppatore.setSoftwareHouse(null);
		//softwareHouse.removeSviluppatore(sviluppatore);
		
		sviluppatoreService.save(sviluppatore);
		//softwareHouseService.save(softwareHouse);
		
		
		return "redirect:/admin/formAddSviluppatoreSoftwareHouse/"+idSoftwareHouse;
	}
	
	@GetMapping("/admin/removeSviluppatore/{id}")
	public String removeSviluppatore(@PathVariable("id") Long id, Model model) {
		Sviluppatore sviluppatore=this.sviluppatoreService.findById(id);
		this.sviluppatoreService.delete(sviluppatore);
		return "redirect:/admin/manageSviluppatori";
	}
	
	@GetMapping("/admin/manageSviluppatori")
	public String manageSoftware(Model model) {
		model.addAttribute("sviluppatori", this.sviluppatoreService.findAll());
		return "admin/manageSviluppatori.html";
	}
	
	@GetMapping("/admin/formUpdateSviluppatore/{id}")
	public String formUpdateSviluppatore(@PathVariable("id") Long id, Model model) {
		model.addAttribute("sviluppatore", this.sviluppatoreService.findById(id));
		return "admin/formUpdateSviluppatore.html";
	}
	
	@PostMapping("/admin/updateSviluppatore")
	public String updateSviluppatore(@ModelAttribute("sviluppatore") Sviluppatore sviluppatore) {
		sviluppatoreService.save(sviluppatore);
		return "redirect:/sviluppatore/"+sviluppatore.getId();
	}
	
	//Ricerca sviluppatore pagina gestione
	@PostMapping("/admin/manageSviluppatore")
	public String searchManage(@RequestParam("nome") String nome, @RequestParam("cognome") String cognome, Model model) {
		ArrayList<Sviluppatore> sviluppatoriPerNome=(ArrayList<Sviluppatore>) this.sviluppatoreService.findByNomeContaining(nome);
		ArrayList<Sviluppatore> sviluppatoriPerCognome=(ArrayList<Sviluppatore>) this.sviluppatoreService.findByCognomeContaining(cognome);
		//Sviluppatori con nome e cognome che contengono le stringe nome e cognome
		sviluppatoriPerNome.retainAll(sviluppatoriPerCognome);
		model.addAttribute("sviluppatori", sviluppatoriPerNome);
		return "admin/manageSviluppatori.html";
	}
}
