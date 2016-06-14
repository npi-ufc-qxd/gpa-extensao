package ufc.quixada.npi.gpa.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ufc.quixada.npi.gpa.model.Servidor;

public interface ServidorRepository extends CrudRepository<Servidor, Integer>{

	List<Servidor> findAll();
}
