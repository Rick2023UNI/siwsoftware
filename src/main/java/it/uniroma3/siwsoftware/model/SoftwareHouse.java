package it.uniroma3.siwsoftware.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class SoftwareHouse {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String nome;
	
	@OneToMany(mappedBy = "softwareHouse")
	private List<Sviluppatore> sviluppatori;
	
	@OneToMany(mappedBy = "softwareHouse")
	private List<Software> software;
}
