package it.uniroma3.siwsoftware.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siwsoftware.model.Recensione;
import it.uniroma3.siwsoftware.model.Software;
import it.uniroma3.siwsoftware.model.Utente;
import it.uniroma3.siwsoftware.repository.RecensioneRepository;
import it.uniroma3.siwsoftware.repository.SoftwareRepository;

@Service
public class RecensioneService {
	@Autowired
	private RecensioneRepository recensioneRepository;

	//tutte recensioni
	public Iterable<Recensione> findAll() {
		return recensioneRepository.findAll();
	}

	public void save(Recensione recensione) {
		recensioneRepository.save(recensione);
	}

	//cerca per id
	public Recensione findById(Long id) {
		return recensioneRepository.findById(id).get();
	}

	//cerca per software e utente
	public Recensione findBySoftwareAndUtente(Software software, Utente utente) {
		return recensioneRepository.findBySoftwareAndUtente(software, utente);
	}

	//elimina
	public void delete(Recensione recensione) {
		recensioneRepository.delete(recensione);
		
	}

	//esiste per id
	public boolean existsById(Long id) {
		return recensioneRepository.existsById(id);
	}

	public int sumNumeroStelleBySoftware(Software software) {
		return recensioneRepository.sumNumeroStelleBySoftware(software);
	}

	public int countBySoftware(Software software) {
		return recensioneRepository.countBySoftware(software);
	}

	public Integer countBySoftwareAndNumeroStelle(Software software, Integer numeroStelle) {
		return recensioneRepository.countBySoftwareAndNumeroStelle(software, numeroStelle);
	}
}
