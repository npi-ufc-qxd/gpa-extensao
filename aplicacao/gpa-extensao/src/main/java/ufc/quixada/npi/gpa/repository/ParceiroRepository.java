package ufc.quixada.npi.gpa.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ufc.quixada.npi.gpa.model.Parceiro;

@Repository
public interface ParceiroRepository extends CrudRepository<Parceiro, Long>{

}
