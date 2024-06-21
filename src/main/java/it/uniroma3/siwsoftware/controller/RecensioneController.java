package it.uniroma3.siwsoftware.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siwsoftware.model.Recensione;
import it.uniroma3.siwsoftware.model.Software;
import it.uniroma3.siwsoftware.model.Utente;
import it.uniroma3.siwsoftware.service.RecensioneService;
import it.uniroma3.siwsoftware.service.SoftwareService;
import it.uniroma3.siwsoftware.service.UtenteService;

@Controller 
public class RecensioneController {
	@Autowired RecensioneService recensioneService;
	@Autowired SoftwareService softwareService;
	@Autowired UtenteService utenteService;

	@PostMapping("/recensione/{id}")
	public String newRecensione(@PathVariable("id") Long id, @ModelAttribute("recensione") Recensione recensione) {
		Software software=softwareService.findById(id);

		//Utente corrente
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Utente utente=utenteService.getCredentials(user.getUsername());

		software.addRecensione(recensione);
		recensione.setSoftware(software);
		recensione.setUtente(utente);
		recensioneService.save(recensione);
		softwareService.save(software);
		return "redirect:/software/"+id;
	}

	@GetMapping("/removeRecensione/{idSoftware}/{idRecensione}")
	public String removeRecensione(@PathVariable("idSoftware") Long idSoftware, 
			@PathVariable("idRecensione") Long id,
			Model model) {
		Recensione recensione=this.recensioneService.findById(id);

		//Utente corrente
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Utente utente=utenteService.getCredentials(user.getUsername());
		
		//Verifica se l'utente corrente è l'autore della Recensione o è un amministratore
		if (recensione.getUtente()==utente || auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("admin"))) {
			recensioneService.delete(recensione);
		}
		return "redirect:/software/"+id; 
	}
}
