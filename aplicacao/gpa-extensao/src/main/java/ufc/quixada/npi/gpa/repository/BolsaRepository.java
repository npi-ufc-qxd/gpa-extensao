package ufc.quixada.npi.gpa.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ufc.quixada.npi.gpa.model.Bolsa;

public interface BolsaRepository extends CrudRepository<Bolsa, Integer>{
	List<Bolsa> findAll();
}
