package ufc.quixada.npi.gpa.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ufc.quixada.npi.gpa.model.Documento;

@Repository
public interface DocumentoRepository extends CrudRepository<Documento, Long> {

}
