package it.uniroma3.siwsoftware.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siwsoftware.model.Sviluppatore;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface SviluppatoreRepository extends CrudRepository<Sviluppatore, Long> {

	Iterable<Sviluppatore> findByNomeContaining(String nome);

	Iterable<Sviluppatore> findByCognomeContaining(String cognome);

	ArrayList<Sviluppatore> findByNomeAndCognomeContaining(String nome, String cognome);

}
