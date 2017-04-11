package ufc.quixada.npi.gpa.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ufc.quixada.npi.gpa.model.Papel;
import ufc.quixada.npi.gpa.model.Papel.Tipo;

@Repository
public interface PapelRepository extends CrudRepository<Papel, Integer> {

	Papel findByNome(Tipo nome);

}
