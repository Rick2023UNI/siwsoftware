package it.uniroma3.siwsoftware.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siwsoftware.model.Recensione;
import it.uniroma3.siwsoftware.model.Software;
import it.uniroma3.siwsoftware.model.Utente;

import java.util.List;

public interface RecensioneRepository extends CrudRepository<Recensione, Long> {

	//cerca recensione per software e utente
	Recensione findBySoftwareAndUtente(Software software, Utente utente);

	int countBySoftware(Software software);

	int countBySoftwareAndNumeroStelle(Software software, int numeroStelle);

}
