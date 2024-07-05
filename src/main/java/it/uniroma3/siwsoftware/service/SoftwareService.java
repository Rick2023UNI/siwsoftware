package it.uniroma3.siwsoftware.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siwsoftware.model.Software;
import it.uniroma3.siwsoftware.repository.SoftwareRepository;

@Service
public class SoftwareService {
	@Autowired
	private SoftwareRepository softwareRepository;

	public Iterable<Software> findAll() {
		return softwareRepository.findAll();
	}

	public void save(Software software) {
		softwareRepository.save(software);
	}

	public Software findById(Long id) {
		return softwareRepository.findById(id).get();
	}

	public void delete(Software software) {
		softwareRepository.delete(software);
		
	}

	public Iterable<Software> findByNomeContaining(String nome) {
		return softwareRepository.findByNomeContaining(nome);
	}
}
