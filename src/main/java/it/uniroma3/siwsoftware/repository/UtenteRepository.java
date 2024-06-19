package it.uniroma3.siwsoftware.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siwsoftware.model.Utente;

import java.util.List;
import java.util.Optional;

public interface UtenteRepository extends CrudRepository<Utente, Long> {

	Optional<Utente> findByUsername(String username);

	boolean existsByUsername(String username);



}
