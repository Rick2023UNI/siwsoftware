package it.uniroma3.siwsoftware.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity
public class Sviluppatore {
	public Immagine getFoto() {
		return foto;
	}

	public void setFoto(Immagine foto) {
		this.foto = foto;
	}

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

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public List<Software> getSoftware() {
		return software;
	}

	public void setSoftware(List<Software> software) {
		this.software = software;
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
	
	private String cognome;
	
	@ManyToMany(mappedBy = "sviluppatori")
	private List<Software> software;
	
	@ManyToOne
	private SoftwareHouse softwareHouse;
	
	@OneToOne
	private Immagine foto;
}
