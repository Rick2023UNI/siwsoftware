package it.uniroma3.siwsoftware.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class Utente {
	
	public String getRuolo() {
		return ruolo;
	}

	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Sviluppatore getSviluppatore() {
		return sviluppatore;
	}

	public void setSviluppatore(Sviluppatore sviluppatore) {
		this.sviluppatore = sviluppatore;
	}

	public List<Recensione> getRecensioni() {
		return recensioni;
	}

	public void setRecensioni(List<Recensione> recensioni) {
		this.recensioni = recensioni;
	}
	
	public Immagine getFoto() {
		return foto;
	}

	public void setFoto(Immagine foto) {
		this.foto = foto;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotBlank(message = "Lo username e' richiesto")
	@NotNull
	private String username;
	
	@NotBlank(message = "La password e' richiesta")
	@Size(min = 8, message = "La password deve essere lunga almeno 8 caratteri")
	@NotNull
	private String password;
	
	private String ruolo;
	
	@OneToOne(cascade = CascadeType.ALL)
	private Sviluppatore sviluppatore;
	
	@OneToMany(mappedBy = "utente")
	private List<Recensione> recensioni;
	
	@OneToOne
	private Immagine foto;
}
