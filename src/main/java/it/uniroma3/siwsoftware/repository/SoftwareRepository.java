package it.uniroma3.siwsoftware.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siwsoftware.model.Software;

import java.util.List;

public interface SoftwareRepository extends CrudRepository<Software, Long> {

	// cerca per nome
	Iterable<Software> findByNomeContaining(String nome);

}
