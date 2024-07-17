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
import org.springframework.validation.BindingResult;
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
import it.uniroma3.siwsoftware.security.UtenteValidator;
import it.uniroma3.siwsoftware.service.ImmagineService;
import it.uniroma3.siwsoftware.service.SoftwareHouseService;
import it.uniroma3.siwsoftware.service.SoftwareService;
import it.uniroma3.siwsoftware.service.SviluppatoreService;
import it.uniroma3.siwsoftware.service.UtenteService;
import jakarta.validation.Valid;

@Controller 
public class SviluppatoreController {
	@Autowired SviluppatoreService sviluppatoreService;
	@Autowired SoftwareService softwareService;
	@Autowired SoftwareHouseService softwareHouseService;
	@Autowired ImmagineService immagineService;
	@Autowired PasswordEncoder passwordEncoder;
	@Autowired UtenteValidator utenteValidator;
	@Autowired UtenteService utenteService;

	//dettagli di uno sviluppatore
	@GetMapping("/sviluppatore/{id}")
	public String getSviluppatore(@PathVariable("id") Long id, Model model) {
		model.addAttribute("sviluppatore", this.sviluppatoreService.findById(id));
		return "Sviluppatore.html";
	}

	//action ti fa aprire sempre la stessa form sia per creare che per modificare e in base ad action la form chiama la post a path diversi
	@GetMapping("/admin/formNewSviluppatore")
	public String formNewSviluppatore(Model model) {
		model.addAttribute("action", "/admin/sviluppatore");
		model.addAttribute("sviluppatore", new Sviluppatore());
		model.addAttribute("utente", new Utente());
		return "admin/formNewSviluppatore.html";
	}

	//form per update sviluppatore
	//action ti fa aprire sempre la stessa form sia per creare che per modificare e in base ad action la form chiama la post a path diversi
	@GetMapping("/admin/formUpdateSviluppatore/{id}")
	public String formUpdateSviluppatore(Model model,
			@PathVariable("id") Long id) {
		Sviluppatore sviluppatore=sviluppatoreService.findById(id);
		Utente utente;
		if (sviluppatore==null) {
			sviluppatore=new Sviluppatore();
			utente=new Utente();
		}
		else {
			utente=sviluppatore.getUtente();
			utente.setPassword("");
		}
		model.addAttribute("action", "/admin/updateSviluppatore/"+sviluppatore.getId());
		model.addAttribute("sviluppatore", sviluppatore);
		model.addAttribute("utente", utente);
		return "admin/formNewSviluppatore.html";
	}

	//crea sviluppatore
	@PostMapping("/admin/sviluppatore")
	public String newSviluppatore(@Valid @ModelAttribute("utente") Utente utente,
			BindingResult credentialsBindingResult,
			@ModelAttribute("sviluppatore") Sviluppatore sviluppatore,
			@RequestParam("input-immagine") MultipartFile multipartFile) throws IOException {

		this.utenteValidator.validate(utente, credentialsBindingResult);

		if(!credentialsBindingResult.hasErrors()) {

			this.sviluppatoreService.save(sviluppatore);
			String fileName=StringUtils.cleanPath(multipartFile.getOriginalFilename());
			Immagine immagine=new Immagine();
			immagine.setFolder("sviluppatore");
			fileName=sviluppatore.getId()+fileName.substring(fileName.lastIndexOf('.'));
			immagine.uploadImage(fileName, multipartFile);
			this.immagineService.save(immagine);
			utente.setFoto(immagine);

			utente.setPassword(passwordEncoder.encode(utente.getPassword()));
			utente.setSviluppatore(sviluppatore);

			this.sviluppatoreService.save(sviluppatore);
			utenteService.save(utente);

			return "redirect:/sviluppatore/"+sviluppatore.getId();
		}
		System.err.println(credentialsBindingResult.getAllErrors());
		return "redirect:/admin/manageSviluppatori?error=true";
	}

	//update svilupttaore
	@PostMapping("/admin/updateSviluppatore/{id}")
	public String updateSviluppatore(@PathVariable("id") Long id, 
			@ModelAttribute("sviluppatore") Sviluppatore sviluppatoreAggiornato,
			@ModelAttribute("utente") Utente utenteAggiornato,
			@RequestParam("input-immagine") MultipartFile multipartFile) throws IOException {
		
		Sviluppatore sviluppatore=sviluppatoreService.findById(id);
		Utente utente=utenteService.getCredentials(sviluppatore.getUtente().getId());
		String fileName=StringUtils.cleanPath(multipartFile.getOriginalFilename());
		if (fileName!="") {
			Immagine vecchiaImmagine=utente.getFoto();
			utente.setFoto(null);
			this.immagineService.delete(vecchiaImmagine);
			fileName=sviluppatore.getId()+fileName.substring(fileName.lastIndexOf('.'));
			Immagine immagine=new Immagine();
			immagine.setFolder("sviluppatore");
			immagine.uploadImage(fileName, multipartFile);

			utente.setFoto(immagine);
			this.immagineService.save(immagine);
		}
		sviluppatore.aggiorna(sviluppatoreAggiornato);
		sviluppatoreService.save(sviluppatore);


		utente.setUsername(utenteAggiornato.getUsername());
		System.out.println(utenteAggiornato.getPassword());
		System.out.println(utenteAggiornato.getPassword()!=null);
		System.out.println(utenteAggiornato.getPassword()!="");
		if (!utenteAggiornato.getPassword().isEmpty()) {
			utente.setPassword(passwordEncoder.encode(utenteAggiornato.getPassword()));
		}
		System.out.println(utente.getUsername() + " " + utente.getPassword());
		utenteService.save(utente);


		return "redirect:/sviluppatore/"+sviluppatore.getId();
	}


	@GetMapping("/admin/formAddSviluppatoreSoftware/{idSoftware}")
	public String formAddSviluppatoreSoftware(@PathVariable("idSoftware") Long idSoftware, 
			Model model) {
		Software software=this.softwareService.findById(idSoftware); 
		ArrayList<Sviluppatore> sviluppatori=(ArrayList<Sviluppatore>) this.sviluppatoreService.findAll();
		sviluppatori.removeAll(software.getSviluppatori());
		model.addAttribute("software", software);
		model.addAttribute("sviluppatori", sviluppatori);
		model.addAttribute("sviluppatoriSoftware", software.getSviluppatori());
		//La s è mancante per rendere possibile concatenare la s minuscola o maiuscola a seconda del caso
		model.addAttribute("operazione", "oftware");
		return "admin/formAddSviluppatore.html";
	}

	//addsviluppatore al sofwtare
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

	//remove sviluppatore dal software
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




	//form per mettere uno sviluppatore in una software house
	@GetMapping("/admin/formAddSviluppatoreSoftwareHouse/{idSoftwareHouse}")
	public String formAddSviluppatoreSoftwareHouse(@PathVariable("idSoftwareHouse") Long idSoftwareHouse, 
			Model model) {
		SoftwareHouse softwareHouse=this.softwareHouseService.findById(idSoftwareHouse);
		ArrayList<Sviluppatore> sviluppatori=(ArrayList<Sviluppatore>) (this.sviluppatoreService.findAll());
		sviluppatori.removeAll(softwareHouse.getSviluppatori());
		model.addAttribute("software", softwareHouse);
		model.addAttribute("sviluppatoriSoftware", softwareHouse.getSviluppatori());
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

	//Ricerca sviluppatore pagina gestione
	@PostMapping("/admin/manageSviluppatore")
	public String searchManage(@RequestParam("nome") String nome, 
			@RequestParam("cognome") String cognome, 
			Model model) {
		ArrayList<Sviluppatore> sviluppatori=this.sviluppatoreService.findByNomeContainingAndCognomeContaining(nome, cognome);
		model.addAttribute("sviluppatori", sviluppatori);
		return "admin/manageSviluppatori.html";
	}

	//tipologia è se usi form ricerca sopra o sotto, operazione è se apri la pagina da software o software house
	@PostMapping("/admin/cercaAddSviluppatore/{tipologiaRicerca}/{operazione}/{id}")
	public String searchAddSviluppatore(@PathVariable("operazione") String operazione, 
			@PathVariable("tipologiaRicerca") String tipologiaRicerca, 
			@PathVariable("id") Long id,
			@RequestParam("nome") String nome, 
			@RequestParam("cognome") String cognome, 
			Model model) {

		System.out.println(operazione + " " + tipologiaRicerca);
		List<Sviluppatore> sviluppatoriSoftware = null;
		ArrayList<Sviluppatore> sviluppatoriRicerca = null;
		ArrayList<Sviluppatore> sviluppatori=null;

		if (tipologiaRicerca.equals("software")) {
			if (operazione.equals("oftware")) {
				System.out.println("ok");
				Software software=softwareService.findById(id);
				sviluppatoriSoftware=software.getSviluppatori();
				model.addAttribute("software", software);
			} 
			else if (operazione.equals("oftwareHouse")) {
				SoftwareHouse softwareHouse=softwareHouseService.findById(id);
				sviluppatoriSoftware=softwareHouse.getSviluppatori();
				model.addAttribute("software", softwareHouse);
			}

			sviluppatoriRicerca=this.sviluppatoreService.findByNomeContainingAndCognomeContaining(nome, cognome);
			sviluppatoriRicerca.retainAll(sviluppatoriSoftware);
			sviluppatori=(ArrayList<Sviluppatore>) this.sviluppatoreService.findAll();

			//Rimozione di tutti gli sviluppatori che già fanno parte del software/software house
			sviluppatori.removeAll(sviluppatoriSoftware);
			model.addAttribute("sviluppatori", sviluppatori);
			model.addAttribute("sviluppatoriSoftware", sviluppatoriRicerca);
		}
		else if (tipologiaRicerca.equals("sviluppatori")) {
			System.out.println("else");
			if (operazione.equals("oftware")) {
				Software software=softwareService.findById(id);
				sviluppatoriSoftware=software.getSviluppatori();
				model.addAttribute("software", software);
			} 
			else if (operazione.equals("oftwareHouse")) {
				SoftwareHouse softwareHouse=softwareHouseService.findById(id);
				sviluppatoriSoftware=softwareHouse.getSviluppatori();
				model.addAttribute("software", softwareHouse);
			}

			sviluppatoriRicerca=this.sviluppatoreService.findByNomeContainingAndCognomeContaining(nome, cognome);

			//Rimozione di tutti gli sviluppatori che già fanno parte del software/software house
			sviluppatoriRicerca.removeAll(sviluppatoriSoftware);
			model.addAttribute("sviluppatori", sviluppatoriRicerca);
			model.addAttribute("sviluppatoriSoftware", sviluppatoriSoftware);
		}

		model.addAttribute("operazione", operazione);
		return "admin/formAddSviluppatore.html";
	}
}
