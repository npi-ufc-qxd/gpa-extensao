package ufc.quixada.npi.gpa.repository;




import java.util.Collection;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ufc.quixada.npi.gpa.model.AcaoExtensao;
import ufc.quixada.npi.gpa.model.AcaoExtensao.Status;
import ufc.quixada.npi.gpa.model.Pessoa;

@Repository
public interface AcaoExtensaoRepository extends CrudRepository<AcaoExtensao, Integer> {
	
	 List<AcaoExtensao> findByCoordenadorAndStatusIn (Pessoa coordenador, Collection<Status> status);
	 
	 List<AcaoExtensao> findByStatusIn (Collection<Status> status);
}
