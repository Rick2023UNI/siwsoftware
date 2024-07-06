package it.uniroma3.siwsoftware.controller;

import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import it.uniroma3.siwsoftware.model.Sviluppatore;
import it.uniroma3.siwsoftware.service.SviluppatoreService;
import it.uniroma3.siwsoftware.service.UtenteService;

@ControllerAdvice
public class GlobalController {

	@Autowired
	SviluppatoreService sviluppatoreService;
	@Autowired
	UtenteService utenteService;

	@ModelAttribute("utenteAuth")
	public UserDetails dettagliUtente() {
		UserDetails user = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}
		return user;
	}

	@ModelAttribute("sviluppatoreAuth")
	public Sviluppatore getSviluppatore() {
		UserDetails user = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}
		Sviluppatore sviluppatore = null;
		if (user != null) {
			sviluppatore = utenteService.getCredentials(user.getUsername()).getSviluppatore();
		}
		return sviluppatore;
	}

	@ModelAttribute("admin")
	public boolean admin() {
		UserDetails user = null;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return (auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("admin")));
	}
}
