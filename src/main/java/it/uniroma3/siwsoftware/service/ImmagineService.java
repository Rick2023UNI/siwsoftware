package it.uniroma3.siwsoftware.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siwsoftware.model.Immagine;
import it.uniroma3.siwsoftware.repository.ImmagineRepository;

@Service
public class ImmagineService {
	@Autowired
	private ImmagineRepository immagineRepository;

	public void save(Immagine immagine) {
		immagineRepository.save(immagine);
	}

	public Immagine findById(Long id) {
		return immagineRepository.findById(id).get();
	}
}
