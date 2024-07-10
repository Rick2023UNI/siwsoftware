package it.uniroma3.siwsoftware.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;

import it.uniroma3.siwsoftware.service.ImmagineService;

@ControllerAdvice
public class ImmaginiController {
	@Autowired ImmagineService immagineService;
	
	@GetMapping("/login")
	public String formLoginUtente(Model model) {
		return "authentication/login.html";
	}
}
