package it.uniroma3.siwsoftware.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siwsoftware.model.Sviluppatore;

import java.util.List;
import java.util.Optional;

public interface SviluppatoreRepository extends CrudRepository<Sviluppatore, Long> {

}
