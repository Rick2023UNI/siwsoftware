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

	@GetMapping("/softwareHouse/{id}")
	public String getsoftwareHouse(@PathVariable("id") Long id, Model model) {
		model.addAttribute("softwareHouse", this.softwareHouseService.findById(id));
		return "SoftwareHouse.html";
	}
	
	@GetMapping("/admin/newSoftwareHouse")
	public String addsoftwareHouse(Model model) {
		model.addAttribute("softwareHouse", new SoftwareHouse());
		return "admin/formNewSoftwareHouse.html";
	}
	
	@PostMapping("/admin/softwareHouse")
	public String newsoftwareHousee(@ModelAttribute("softwareHouse") SoftwareHouse softwareHouse,
			@RequestParam("input-image") MultipartFile multipartFile) throws IOException {
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
}
