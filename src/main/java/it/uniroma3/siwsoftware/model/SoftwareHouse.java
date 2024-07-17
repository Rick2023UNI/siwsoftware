package it.uniroma3.siwsoftware.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PreRemove;

@Entity
public class SoftwareHouse {
	
	//Prima della cancellazione
	@PreRemove
    private void preRemove() {
        sviluppatori.forEach(sviluppatori -> sviluppatori.setSoftwareHouse(null));
    }
	
	public Immagine getLogo() {
		return logo;
	}

	public void setLogo(Immagine logo) {
		this.logo = logo;
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

	public List<Sviluppatore> getSviluppatori() {
		return sviluppatori;
	}

	public void setSviluppatori(List<Sviluppatore> sviluppatori) {
		this.sviluppatori = sviluppatori;
	}

	public List<Software> getSoftware() {
		return software;
	}

	public void setSoftware(List<Software> software) {
		this.software = software;
	}
	
	public void addSviluppatore(Sviluppatore sviluppatore) {
		if (this.sviluppatori==null) {
			this.sviluppatori= new ArrayList<Sviluppatore>();
		}
		this.sviluppatori.add(sviluppatore);
	}

	public void removeSviluppatore(Sviluppatore sviluppatore) {
		this.sviluppatori.remove(this.sviluppatori.indexOf(sviluppatore));
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String nome;
	
	@OneToOne
	private Immagine logo;
	
	@OneToMany(mappedBy = "softwareHouse")
	private List<Sviluppatore> sviluppatori;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "softwareHouse")
	private List<Software> software;
	
	public void aggiorna(SoftwareHouse softwareHouseAggiornato) {
		this.setNome(softwareHouseAggiornato.getNome());
	}
}
