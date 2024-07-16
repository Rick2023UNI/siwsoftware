package it.uniroma3.siwsoftware.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
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
import it.uniroma3.siwsoftware.model.SoftwareHouse;
import it.uniroma3.siwsoftware.service.ImmagineService;
import it.uniroma3.siwsoftware.service.SoftwareHouseService;

@Controller 
public class SoftwareHouseController {
	@Autowired SoftwareHouseService softwareHouseService;
	@Autowired ImmagineService immagineService;

	//tutte le software house
	@GetMapping("/softwareHouses")
	public String getsoftwareHouses(Model model) {
		model.addAttribute("softwareHouses", this.softwareHouseService.findAll());
		return "admin/manageSoftwareHouse.html";
	}
	
	//dettagli di una software house
	@GetMapping("/softwareHouse/{id}")
	public String getsoftwareHouse(@PathVariable("id") Long id, Model model) {
		model.addAttribute("softwareHouse", this.softwareHouseService.findById(id));
		return "SoftwareHouse.html";
	}
	
	//form per nuova software house
	@GetMapping("/admin/formNewSoftwareHouse")
	public String formNewSoftwareHouse(Model model) {
		model.addAttribute("softwareHouse", new SoftwareHouse());
		return "admin/formNewSoftwareHouse.html";
	}
	
	//nuova software house
	@PostMapping("/admin/softwareHouse")
	public String newSoftwareHouse(@ModelAttribute("softwareHouse") SoftwareHouse softwareHouse,
			@RequestParam("input-immagine") MultipartFile multipartFile) throws IOException {
		softwareHouseService.save(softwareHouse);
		
		String fileName=StringUtils.cleanPath(multipartFile.getOriginalFilename());
		Immagine immagine=new Immagine();
		immagine.setFolder("softwareHouse");
		fileName=softwareHouse.getId()+fileName.substring(fileName.lastIndexOf('.'));
		immagine.uploadImage(fileName, multipartFile);
		this.immagineService.save(immagine);
		
		softwareHouse.setLogo(immagine);
		softwareHouseService.save(softwareHouse);
		return "redirect:/softwareHouse/"+softwareHouse.getId();
	}
	
	//form per modifica software house
	@GetMapping("/admin/formUpdateSoftwareHouse/{id}")
	public String formUpdateSoftwareHouse(@PathVariable("id") Long id, Model model) {
		model.addAttribute("softwareHouse", this.softwareHouseService.findById(id));
		return "admin/formNewSoftwareHouse.html";
	}
	
	//post per modifica software house
	@PostMapping("/admin/updateSoftwareHouse/{id}")
	public String updateSoftwareHouse(@PathVariable("id") Long id, 
			@ModelAttribute("softwareHouse") SoftwareHouse softwareHouseAggiornato,
			@RequestParam("input-immagine") MultipartFile multipartFile) throws IOException {
		SoftwareHouse softwareHouse=softwareHouseService.findById(id);
		softwareHouse.aggiorna(softwareHouseAggiornato);
		String fileName=StringUtils.cleanPath(multipartFile.getOriginalFilename());
		if (fileName!="") {
			Immagine vecchiaImmagine=softwareHouse.getLogo();
			softwareHouse.setLogo(null);
			this.immagineService.delete(vecchiaImmagine);
			
			Immagine immagine=new Immagine();
			immagine.setFolder("softwareHouse");
			fileName=softwareHouse.getId()+fileName.substring(fileName.lastIndexOf('.'));
			immagine.uploadImage(fileName, multipartFile);
			this.immagineService.save(immagine);
			
			softwareHouse.setLogo(immagine);
		}
		
		softwareHouseService.save(softwareHouse);
		return "redirect:/softwareHouse/"+softwareHouse.getId();
	}
	
	//rimuove software house
	@GetMapping("/admin/removeSoftwareHouse/{id}")
	public String removeSoftwareHouse(@PathVariable("id") Long id, Model model) {
		SoftwareHouse softwareHouse=this.softwareHouseService.findById(id);
		this.softwareHouseService.delete(softwareHouse);
		return "redirect:/admin/manageSoftwareHouses";
	}
	
	//gestisci software house
	@GetMapping("/admin/manageSoftwareHouses")
	public String manageSoftware(Model model) {
		model.addAttribute("softwareHouses", this.softwareHouseService.findAll());
		return "admin/manageSoftwareHouse.html";
	}
	
	//Ricerca software house pagina gestione, rimanda sempre a manage ma solo con software house che matchano
	@PostMapping("/manageSoftwareHouses")
	public String searchManage(@RequestParam("nome") String nome, Model model) {
		model.addAttribute("softwareHouses", this.softwareHouseService.findByNomeContaining(nome));
		return "admin/manageSoftwareHouse.html";
	}
}
