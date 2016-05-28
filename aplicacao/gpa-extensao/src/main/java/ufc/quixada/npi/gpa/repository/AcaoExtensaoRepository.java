package ufc.quixada.npi.gpa.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ufc.quixada.npi.gpa.model.AcaoExtensao;

@Repository
public interface AcaoExtensaoRepository extends CrudRepository<AcaoExtensao, Integer> {

}
