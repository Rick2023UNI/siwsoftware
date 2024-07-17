package it.uniroma3.siwsoftware.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;

import it.uniroma3.siwsoftware.service.RecensioneService;
import jakarta.persistence.CascadeType;
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
	
	public void addRecensione(Recensione recensione) {
		if (this.recensioni==null) {
			this.recensioni= new ArrayList<Recensione>();
		}
		else {
			this.recensioni.add(recensione);
		}
	}

	public void removeRecensione(Recensione recensione) {
		this.recensioni.remove(this.recensioni.indexOf(recensione));
	}
	
	public void addImmagine(Immagine immagine) {
		if (this.immagini==null) {
			this.immagini= new ArrayList<Immagine>();
		}
		this.immagini.add(immagine);
	}

	public void removeImmagine(Immagine immagine) {
		this.immagini.remove(this.immagini.indexOf(immagine));
	}
	
	public void addSviluppatore(Sviluppatore sviluppatore) {
		if (this.sviluppatori==null) {
			this.sviluppatori=new ArrayList<Sviluppatore>();
		}
		System.out.println(sviluppatori);
		this.sviluppatori.add(sviluppatore);
	}

	public void removeSviluppatore(Sviluppatore sviluppatore) {
		this.sviluppatori.remove(this.sviluppatori.indexOf(sviluppatore));
	}

	public int getMediaStelle() {
		return mediaStelle;
	}
	
	public void setMediaStelle(int mediaStelle) {
		this.mediaStelle = mediaStelle;
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
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "software")
	private List<Recensione> recensioni;
	
	@ManyToMany
	private List<Sviluppatore> sviluppatori;
	
	@ManyToOne
	private SoftwareHouse softwareHouse;

	private int mediaStelle;

	public void aggiorna(Software softwareAggiornato) {
		this.setNome(softwareAggiornato.getNome());
		this.setAnnoPubblicazione(softwareAggiornato.getAnnoPubblicazione());
		this.setCategoria(softwareAggiornato.getCategoria());
		this.setSoftwareHouse(softwareAggiornato.getSoftwareHouse());
	}
}
