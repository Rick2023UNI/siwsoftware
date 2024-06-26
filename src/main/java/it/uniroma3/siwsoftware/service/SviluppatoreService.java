package it.uniroma3.siwsoftware.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siwsoftware.model.Sviluppatore;
import it.uniroma3.siwsoftware.repository.SviluppatoreRepository;

@Service
public class SviluppatoreService {
	@Autowired
	private SviluppatoreRepository sviluppatoreRepository;

	public Iterable<Sviluppatore> findAll() {
		return sviluppatoreRepository.findAll();
	}

	public void save(Sviluppatore sviluppatore) {
		sviluppatoreRepository.save(sviluppatore);
	}

	public Sviluppatore findById(Long id) {
		return sviluppatoreRepository.findById(id).get();
	}

	public void delete(Sviluppatore sviluppatore) {
		sviluppatoreRepository.delete(sviluppatore);
		
	}
}
