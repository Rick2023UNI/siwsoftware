package it.uniroma3.siwsoftware.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity
public class Recensione {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String Titolo;
	
	private String Descrizione;
	
	private Integer numeroStelle;
	
	@DateTimeFormat(pattern ="yyyy-MM-d")
	private Date data;
	
	@ManyToOne
	private Utente utente;
	
	@ManyToOne
	private Software software;
}
