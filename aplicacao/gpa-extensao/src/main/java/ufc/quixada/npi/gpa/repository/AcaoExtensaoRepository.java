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
	@Query("SELECT ac FROM AcaoExtensao as ac WHERE (ac.status != :aprovado AND ac.status != :reprovado AND ac.status != :novo) AND (ac.coordenador.id = :id)")
	List<AcaoExtensao> getTramitacao(@Param("id") Integer id, @Param("aprovado") Status aprovado, @Param("reprovado") Status reprovado, @Param("novo") Status novo);
	
	@Query("SELECT ac FROM AcaoExtensao as ac WHERE (ac.status = :novo) AND (ac.coordenador.id = :id)")
	List<AcaoExtensao> getNovos(@Param("id") Integer id, @Param("novo") Status novo);
	
	@Query("SELECT ac FROM AcaoExtensao as ac WHERE (ac.status = :aprovado OR ac.status = :reprovado) AND (ac.coordenador.id = :id)")
	List<AcaoExtensao> getHomologados(@Param("id") Integer id, @Param("aprovado") Status aprovado, @Param("reprovado") Status reprovado);
	
	@Query("SELECT ac FROM AcaoExtensao as ac WHERE (ac.parecerRelator.responsavel.id = :id)")
	List<AcaoExtensao> getParecer(@Param("id") Integer id);
	
	@Query("SELECT ac FROM AcaoExtensao as ac,equipe_de_trabalho as p WHERE ac.id = p.acaoExtensao.id AND p.participante.id = :id")
	List<AcaoExtensao> getParticipacao(@Param("id") Integer id);
	
	List<AcaoExtensao> findByCoordenador(Pessoa pessoa);

	List<AcaoExtensao> findByCoordenadorAndStatusIn (Pessoa coordenador, Collection<Status> status);
	 
	List<AcaoExtensao> findByStatusIn (Collection<Status> status);
}
