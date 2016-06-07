package ufc.quixada.npi.gpa.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ufc.quixada.npi.gpa.model.AcaoExtensao;
import ufc.quixada.npi.gpa.model.AcaoExtensao.Status;
import ufc.quixada.npi.gpa.model.Pessoa;

@Repository
public interface AcaoExtensaoRepository extends CrudRepository<AcaoExtensao, Integer> {
	List<AcaoExtensao> findByCoordenadorAndStatusIn (Pessoa coordenador, Collection<Status> status);
	
	@Query("SELECT ac FROM AcaoExtensao as ac WHERE (ac.status != :aprovado AND ac.status != :reprovado AND ac.status != :novo) AND (ac.coordenador.id = :id)")
	List<AcaoExtensao> getTramitacao(@Param("id") Integer id, @Param("aprovado") Status aprovado, @Param("reprovado") Status reprovado, @Param("novo") Status novo);
	
	@Query("SELECT ac FROM AcaoExtensao as ac WHERE (ac.status = :novo) AND (ac.coordenador.id = :id)")
	List<AcaoExtensao> getNovos(@Param("id") Integer id, @Param("novo") Status novo);
	
	@Query("SELECT ac FROM AcaoExtensao as ac WHERE (ac.status = :aprovado OR ac.status = :reprovado) AND (ac.coordenador.id = :id)")
	List<AcaoExtensao> getHomologados(@Param("id") Integer id, @Param("aprovado") Status aprovado, @Param("reprovado") Status reprovado);
	
	List<AcaoExtensao> findByStatusIn (Collection<Status> status);
}