package it.uniroma3.siwsoftware.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siwsoftware.repository.UtenteRepository;
import it.uniroma3.siwsoftware.model.Utente;

@Service
public class UtenteService {
	@Autowired
	private UtenteRepository utenteRepository;

	public void save(Utente utente) {
		utenteRepository.save(utente);
	}

	public Utente getCredentials(Long id) {
		return utenteRepository.findById(id).get();
	}

	public Utente getCredentials(String username) {
		return utenteRepository.findByUsername(username).get();
	}

	public boolean existsByUsername(String username) {
		return utenteRepository.existsByUsername(username);
	}
}
