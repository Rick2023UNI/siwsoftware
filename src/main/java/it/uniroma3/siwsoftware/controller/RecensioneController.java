package it.uniroma3.siwsoftware.controller;

import java.util.Date;

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
	@Autowired
	RecensioneService recensioneService;
	@Autowired
	SoftwareService softwareService;
	@Autowired
	UtenteService utenteService;

	// aggiunge una recensione per il software e controlla se esiste già
	@PostMapping("/recensione/{idSoftware}/{idRecensione}")
	public String newRecensione(@PathVariable("idSoftware") Long idSoftware,
			@PathVariable("idRecensione") Long idRecensione, @ModelAttribute("recensione") Recensione recensione) {
		Software software = softwareService.findById(idSoftware);

		if (recensioneService.existsById(idRecensione)) {
			Recensione vecchiaRecensione = recensioneService.findById(idRecensione);
			recensioneService.delete(vecchiaRecensione);
		}

		// Utente corrente
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Utente utente = utenteService.getCredentials(user.getUsername());

		Date oggi = new Date();
		recensione.setData(oggi);

		software.addRecensione(recensione);
		recensione.setSoftware(software);
		recensione.setUtente(utente);
		recensioneService.save(recensione);
		softwareService.save(software);

		// Aggiornamento numero medio stelle software
		int recensioniTotali = recensioneService.countBySoftware(software);
		if (recensioniTotali != 0) {
			int softwareSommaStelle = recensioneService.sumNumeroStelleBySoftware(software);
			software.setMediaStelle(Math.round(softwareSommaStelle / recensioniTotali));
		} else {
			software.setMediaStelle(0);
		}

		softwareService.save(software);

		return "redirect:/software/" + idSoftware;
	}

	// aggiunge una recensione al software
	@PostMapping("/recensione/{idSoftware}")
	public String newRecensione(@PathVariable("idSoftware") Long idSoftware,
			@ModelAttribute("recensione") Recensione recensione) {
		Software software = softwareService.findById(idSoftware);

		// Utente corrente
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Utente utente = utenteService.getCredentials(user.getUsername());

		Date oggi = new Date();
		recensione.setData(oggi);

		software.addRecensione(recensione);
		recensione.setSoftware(software);
		recensione.setUtente(utente);
		recensioneService.save(recensione);

		softwareService.save(software);

		// Aggiornamento numero medio stelle software
		int recensioniTotali = recensioneService.countBySoftware(software);
		if (recensioniTotali != 0) {
			int softwareSommaStelle = recensioneService.sumNumeroStelleBySoftware(software);
			software.setMediaStelle(Math.round(softwareSommaStelle / recensioniTotali));
		} else {
			software.setMediaStelle(0);
		}

		softwareService.save(software);

		return "redirect:/software/" + idSoftware;
	}

	// rimuove la recensione
	@GetMapping("/removeRecensione/{idRecensione}")
	public String removeRecensione(@PathVariable("idRecensione") Long id, Model model) {
		Recensione recensione = this.recensioneService.findById(id);
		Software software = recensione.getSoftware();

		// Utente corrente
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Utente utente = utenteService.getCredentials(user.getUsername());

		// Verifica se l'utente corrente è l'autore della Recensione o è un
		// amministratore
		if (recensione.getUtente() == utente
				|| auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("admin"))) {
			recensioneService.delete(recensione);

			// Aggiornamento numero medio stelle software
			int recensioniTotali = recensioneService.countBySoftware(software);
			if (recensioniTotali != 0) {
				int softwareSommaStelle = recensioneService.sumNumeroStelleBySoftware(software);
				software.setMediaStelle(Math.round(softwareSommaStelle / recensioniTotali));
			} else {
				software.setMediaStelle(0);
			}

			softwareService.save(software);
		}

		return "redirect:/software/" + software.getId();
	}
}
