package it.uniroma3.siwsoftware.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siwsoftware.model.SoftwareHouse;

import java.util.List;

public interface SoftwareHouseRepository extends CrudRepository<SoftwareHouse, Long> {

	// cerca per nome
	Iterable<SoftwareHouse> findByNomeContaining(String nome);

}
