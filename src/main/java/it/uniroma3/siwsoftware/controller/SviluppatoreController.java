package it.uniroma3.siwsoftware.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siwsoftware.model.Immagine;
import it.uniroma3.siwsoftware.model.Software;
import it.uniroma3.siwsoftware.model.SoftwareHouse;
import it.uniroma3.siwsoftware.model.Sviluppatore;
import it.uniroma3.siwsoftware.model.Utente;
import it.uniroma3.siwsoftware.service.ImmagineService;
import it.uniroma3.siwsoftware.service.SoftwareHouseService;
import it.uniroma3.siwsoftware.service.SoftwareService;
import it.uniroma3.siwsoftware.service.SviluppatoreService;
import it.uniroma3.siwsoftware.service.UtenteService;

@Controller 
public class SviluppatoreController {
	@Autowired SviluppatoreService sviluppatoreService;
	@Autowired SoftwareService softwareService;
	@Autowired SoftwareHouseService softwareHouseService;
	@Autowired ImmagineService immagineService;
	@Autowired PasswordEncoder passwordEncoder;
	@Autowired UtenteService utenteService;

	@GetMapping("/sviluppatore/{id}")
	public String getSviluppatore(@PathVariable("id") Long id, Model model) {
		model.addAttribute("sviluppatore", this.sviluppatoreService.findById(id));
		return "Sviluppatore.html";
	}
	
	@GetMapping("/admin/formNewSviluppatore")
	public String addSviluppatore(Model model) {
		model.addAttribute("sviluppatore", new Sviluppatore());
		model.addAttribute("utente", new Utente());
		return "admin/formNewSviluppatore.html";
	}
	
	@PostMapping("/admin/sviluppatore")
	public String newSviluppatore(@ModelAttribute("utente") Utente utente,
			@ModelAttribute("sviluppatore") Sviluppatore sviluppatore,
			@RequestParam("input-image") MultipartFile multipartFile) throws IOException {
		this.sviluppatoreService.save(sviluppatore);
		String fileName=StringUtils.cleanPath(multipartFile.getOriginalFilename());
		Immagine immagine=new Immagine();
		immagine.setFolder("sviluppatore");
		fileName=sviluppatore.getId()+fileName.substring(fileName.lastIndexOf('.'));
		immagine.uploadImage(fileName, multipartFile);
		this.immagineService.save(immagine);
		sviluppatore.setFoto(immagine);

		utente.setPassword(passwordEncoder.encode(utente.getPassword()));
		utente.setSviluppatore(sviluppatore);
		
		this.sviluppatoreService.save(sviluppatore);
		utenteService.save(utente);
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
		//La s è mancante per rendere possibile concatenare la s minuscola o maiuscola a seconda del caso
		model.addAttribute("operazione", "oftware");
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
		//La s è mancante per rendere possibile concatenare la s minuscola o maiuscola a seconda del caso
		model.addAttribute("operazione", "oftwareHouse");
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
		Sviluppatore sviluppatore=this.sviluppatoreService.findById(id);
		model.addAttribute("sviluppatore", sviluppatore);
		Utente utente=sviluppatore.getUtente();
		utente.setPassword(null);
		model.addAttribute("utente", utente);
		return "admin/formUpdateSviluppatore.html";
	}
	
	@PostMapping("/admin/updateSviluppatore/{id}")
	public String updateSviluppatore(@PathVariable("id") Long id, 
			@ModelAttribute("sviluppatore") Sviluppatore sviluppatoreAggiornato,
			@ModelAttribute("utente") Utente utenteAggiornato,
			@RequestParam("input-image") MultipartFile multipartFile) throws IOException {
		Sviluppatore sviluppatore=sviluppatoreService.findById(id);
		String fileName=StringUtils.cleanPath(multipartFile.getOriginalFilename());
		/*Evita tentativo di caricare il file vuoto causato
		 dall'ultimo input che viene aggiunto in automatico
		 ed è sempre vuoto
		 */
		if (fileName!="") {
			sviluppatore.getFoto().delete();
			fileName=sviluppatore.getId()+fileName.substring(fileName.lastIndexOf('.'));
			Immagine immagine=new Immagine();
			immagine.setFolder("sviluppatore");
			immagine.uploadImage(fileName, multipartFile);
			
			sviluppatore.setFoto(immagine);
			this.immagineService.save(immagine);
		}
		sviluppatore.aggiorna(sviluppatoreAggiornato);
		sviluppatoreService.save(sviluppatore);
		
		
		Utente utente=sviluppatore.getUtente();
		utente.setUsername(utenteAggiornato.getUsername());
		if (utenteAggiornato.getPassword()!=null) {
			utente.setPassword(passwordEncoder.encode(utenteAggiornato.getPassword()));
		}
		utenteService.save(sviluppatore.getUtente());
		
		
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
