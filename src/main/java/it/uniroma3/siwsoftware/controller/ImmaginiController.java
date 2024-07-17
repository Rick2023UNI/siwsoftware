package it.uniroma3.siwsoftware.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import it.uniroma3.siwsoftware.model.Immagine;
import it.uniroma3.siwsoftware.service.ImmagineService;

import it.uniroma3.siwsoftware.model.Software;
import it.uniroma3.siwsoftware.service.SoftwareService;

@Controller
public class ImmaginiController {
	@Autowired
	ImmagineService immagineService;
	@Autowired
	SoftwareService softwareService;

	// rimuove la foto di quel software
	@GetMapping("/admin/removeImageSoftware/{idSoftware}/{idImmagine}")
	public String removeImageSoftware(@PathVariable("idSoftware") Long idSoftware,
			@PathVariable("idImmagine") Long idImmagine) {
		Immagine immagine = immagineService.findById(idImmagine);
		Software software = softwareService.findById(idSoftware);

		software.removeImmagine(immagine);
		softwareService.save(software);

		immagineService.delete(immagine);
		return "redirect:/admin/formUpdateSoftware/" + idSoftware;
	}
}
