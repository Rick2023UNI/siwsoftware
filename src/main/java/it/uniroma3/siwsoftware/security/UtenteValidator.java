package it.uniroma3.siwsoftware.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import it.uniroma3.siwsoftware.model.Utente;
import it.uniroma3.siwsoftware.service.UtenteService;

@Component
public class UtenteValidator implements Validator {
	@Autowired
	private UtenteService utenteService;

	@Override
	public void validate(Object o, Errors errors) {
		Utente utente = (Utente) o;
		if (utente.getUsername() != null && utenteService.existsByUsername(utente.getUsername())) {
			errors.reject("utente.duplicate");
		}
	}

	@Override
	public boolean supports(Class<?> aClass) {
		return Utente.class.equals(aClass);
	}
}
