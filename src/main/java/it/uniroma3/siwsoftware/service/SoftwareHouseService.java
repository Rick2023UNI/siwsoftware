package it.uniroma3.siwsoftware.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siwsoftware.model.SoftwareHouse;
import it.uniroma3.siwsoftware.repository.SoftwareHouseRepository;

@Service
public class SoftwareHouseService {
	@Autowired
	private SoftwareHouseRepository softwareHouseRepository;

	public Iterable<SoftwareHouse> findAll() {
		return softwareHouseRepository.findAll();
	}

	public void save(SoftwareHouse softwareHouse) {
		softwareHouseRepository.save(softwareHouse);
	}

	public SoftwareHouse findById(Long id) {
		return softwareHouseRepository.findById(id).get();
	}

	public void delete(SoftwareHouse softwareHouse) {
		softwareHouseRepository.delete(softwareHouse);
		
	}
}
