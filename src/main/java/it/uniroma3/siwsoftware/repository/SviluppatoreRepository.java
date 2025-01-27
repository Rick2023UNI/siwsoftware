package it.uniroma3.siwsoftware.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siwsoftware.model.Sviluppatore;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface SviluppatoreRepository extends CrudRepository<Sviluppatore, Long> {

	// cerca per nome
	Iterable<Sviluppatore> findByNomeContaining(String nome);

	// cerca per cognome
	Iterable<Sviluppatore> findByCognomeContaining(String cognome);

	// cerca per nome e cognome
	ArrayList<Sviluppatore> findByNomeContainingAndCognomeContaining(String nome, String cognome);

}
