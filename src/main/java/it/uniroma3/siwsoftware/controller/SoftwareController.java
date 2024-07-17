package it.uniroma3.siwsoftware.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
import it.uniroma3.siwsoftware.model.Recensione;
import it.uniroma3.siwsoftware.model.Software;
import it.uniroma3.siwsoftware.model.SoftwareHouse;
import it.uniroma3.siwsoftware.model.Utente;
import it.uniroma3.siwsoftware.repository.SoftwareRepository;
import it.uniroma3.siwsoftware.service.ImmagineService;
import it.uniroma3.siwsoftware.service.RecensioneService;
import it.uniroma3.siwsoftware.service.SoftwareHouseService;
import it.uniroma3.siwsoftware.service.SoftwareService;
import it.uniroma3.siwsoftware.service.SviluppatoreService;
import it.uniroma3.siwsoftware.service.UtenteService;

@Controller 
public class SoftwareController {
	@Autowired SoftwareService softwareService;
	@Autowired RecensioneService recensioneService;
	@Autowired UtenteService utenteService;
	@Autowired ImmagineService immagineService;
	@Autowired SviluppatoreService sviluppatoreService;
	@Autowired SoftwareHouseService softwareHouseService;

	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("software", this.softwareService.findAll());
		return "index.html";
	}
	
	//Ricerca software dalla barra ricerca nella home
	@PostMapping("/")
	public String search(@RequestParam("nome") String nome, Model model) {
		model.addAttribute("software", this.softwareService.findByNomeContaining(nome));
		return "index.html";
	}

	//form per nuovo software
	@GetMapping("/admin/formNewSoftware")
	public String formNewSoftware(Model model) {
		model.addAttribute("software", new Software());
		model.addAttribute("softwareHouse", softwareHouseService.findAll());
		return "admin/formUpdateSoftware.html";
	}

	//aggiunge un software con la sua immagine
	@PostMapping("/admin/software")
	public String newSoftware(@ModelAttribute("software") Software software,
			@RequestParam("fileImage") MultipartFile[] multipartFiles) throws IOException {
		softwareService.save(software);
		for (MultipartFile multipartFile : multipartFiles) {
			//Caricamento delle immagini
			String fileName=StringUtils.cleanPath(multipartFile.getOriginalFilename());
			/*Evita tentativo di caricare il file vuoto causato
			 dall'ultimo input che viene aggiunto in automatico
			 ed è sempre vuoto
			 */
			if (fileName!="") {
				Immagine immagine=new Immagine();
				immagine.setFolder("software/"+software.getId());
				immagine.uploadImage(fileName, multipartFile);
				this.immagineService.save(immagine);
				
				software.addImmagine(immagine);
				this.immagineService.save(immagine);
			}
		}
		software.setMediaStelle(0);
		softwareService.save(software);
		
		return "redirect:/admin/formAddSviluppatoreSoftware/"+software.getId();
	}

	@GetMapping("/software/{id}")
	public String getSoftware(@PathVariable("id") Long id, Model model) {
		Software software=this.softwareService.findById(id);
		model.addAttribute("software", software);
		List<Recensione> recensioni=software.getRecensioni(); //tutte le recensioni
			
		//Utente corrente
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Utente utente=utenteService.getCredentials(user.getUsername());
		Recensione recensione=recensioneService.findBySoftwareAndUtente(software, utente); //cerca recensione dell utete corrente
		if (recensione==null) {
			recensione=new Recensione(); //se non ha una rceensione per il software ne crea una nuova
		}
		
		//Rimozione recensione utente corrente (serve per dividerle nel html)
		recensioni.remove(recensione);
		model.addAttribute("recensione", recensione);
		model.addAttribute("recensioni", recensioni);
		
		int recensioniTotali=recensioneService.countBySoftware(software);
		System.out.println(recensioniTotali + " condizione "+(recensioniTotali==0));
		List<Integer> stelle=new ArrayList<Integer>();
		if (recensioniTotali==0) {
			Collections.addAll(stelle, 0, 0, 0, 0, 0, 0);
		} else {
			for (Integer i=0;i<=5;i++) {
				Integer numeroRecensioniConStelle=recensioneService.countBySoftwareAndNumeroStelle(software, i);
				stelle.add((numeroRecensioniConStelle*100)/recensioniTotali);
			}
		};
		System.out.println(stelle);
				
		model.addAttribute("recensioniTotali", recensioniTotali);
		model.addAttribute("stelle", stelle);
		return "software.html";
	}
	
	@GetMapping("/admin/formUpdateSoftware/{id}")
	public String formUpdateSoftware(@PathVariable("id") Long id, Model model) {
		model.addAttribute("software", this.softwareService.findById(id));
		model.addAttribute("softwareHouse", softwareHouseService.findAll());
		return "admin/formUpdateSoftware.html";
	}
	
	//aggiorna software
	@PostMapping("/admin/updateSoftware/{id}")
	public String updateSoftware(@PathVariable("id") Long id,
			@ModelAttribute("software") Software softwareAggiornato,
			@RequestParam("fileImage") MultipartFile[] multipartFiles) throws IOException {
		Software software=this.softwareService.findById(id);
		software.aggiorna(softwareAggiornato);
		softwareService.save(software);
		for (MultipartFile multipartFile : multipartFiles) {
			//Caricamento delle immagini
			String fileName=StringUtils.cleanPath(multipartFile.getOriginalFilename());
			/*Evita tentativo di caricare il file vuoto causato
			 dall'ultimo input che viene aggiunto in automatico
			 ed è sempre vuoto
			 */
			if (fileName!="") {
				Immagine immagine=new Immagine();
				immagine.setFolder("software/"+software.getId());
				immagine.uploadImage(fileName, multipartFile);
				this.immagineService.save(immagine);
				
				software.addImmagine(immagine);
				this.immagineService.save(immagine);
			}
		}
		softwareService.save(software);
		return "redirect:/software/"+software.getId();
	}
	
	//rimuovi software
	@GetMapping("/admin/removeSoftware/{id}")
	public String removeSoftware(@PathVariable("id") Long id, Model model) {
		Software software=this.softwareService.findById(id);
		this.softwareService.delete(software);
		return "redirect:/admin/manageSoftware";
	}
	
	@GetMapping("/admin/manageSoftware")
	public String manageSoftware(Model model) {
		model.addAttribute("software", this.softwareService.findAll());
		return "admin/manageSoftware.html";
	}
	
	//Ricerca software pagina gestione
	@PostMapping("/admin/manageSoftware")
	public String searchManage(@RequestParam("nome") String nome, Model model) {
		model.addAttribute("software", this.softwareService.findByNomeContaining(nome));
		return "admin/manageSoftware.html";
	}
}
