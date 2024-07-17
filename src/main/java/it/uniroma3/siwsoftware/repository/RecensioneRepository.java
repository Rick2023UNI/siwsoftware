package it.uniroma3.siwsoftware.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import it.uniroma3.siwsoftware.model.Recensione;
import it.uniroma3.siwsoftware.model.Software;
import it.uniroma3.siwsoftware.model.Utente;

import java.util.List;

public interface RecensioneRepository extends CrudRepository<Recensione, Long> {

	//cerca recensione per software e utente
	Recensione findBySoftwareAndUtente(Software software, Utente utente);
	
	@Query("SELECT sum(r.numeroStelle) from Recensione r where r.software=:software")
	int sumNumeroStelleBySoftware(@Param("software") Software software);

	//conta recensioni del software
	int countBySoftware(Software software);

	//conta recensioni di un certo numero di stelle del software
	int countBySoftwareAndNumeroStelle(Software software, Integer numeroStelle);	
	
}
