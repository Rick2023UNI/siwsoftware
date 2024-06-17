package it.uniroma3.siwsoftware.model;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Software {
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Date getAnnoPubblicazione() {
		return annoPubblicazione;
	}

	public void setAnnoPubblicazione(Date annoPubblicazione) {
		this.annoPubblicazione = annoPubblicazione;
	}

	public List<Immagine> getImmagini() {
		return immagini;
	}

	public void setImmagini(List<Immagine> immagini) {
		this.immagini = immagini;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public List<Recensione> getRecensioni() {
		return recensioni;
	}

	public void setRecensioni(List<Recensione> recensioni) {
		this.recensioni = recensioni;
	}

	public List<Sviluppatore> getSviluppatori() {
		return sviluppatori;
	}

	public void setSviluppatori(List<Sviluppatore> sviluppatori) {
		this.sviluppatori = sviluppatori;
	}

	public SoftwareHouse getSoftwareHouse() {
		return softwareHouse;
	}

	public void setSoftwareHouse(SoftwareHouse softwareHouse) {
		this.softwareHouse = softwareHouse;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String nome;
	
	@DateTimeFormat(pattern ="yyyy")
	private Date annoPubblicazione;
	
	@OneToMany
	private List<Immagine> immagini;
	
	private String categoria;
	
	@OneToMany(mappedBy = "software")
	private List<Recensione> recensioni;
	
	@ManyToMany
	private List<Sviluppatore> sviluppatori;
	
	@ManyToOne
	private SoftwareHouse softwareHouse;
}
