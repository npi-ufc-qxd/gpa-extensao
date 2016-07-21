package ufc.quixada.npi.gpa.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ufc.quixada.npi.gpa.model.AcaoExtensao;
import ufc.quixada.npi.gpa.model.AcaoExtensao.Modalidade;
import ufc.quixada.npi.gpa.model.AcaoExtensao.Status;
import ufc.quixada.npi.gpa.model.Pessoa;

@Repository
public interface AcaoExtensaoRepository extends CrudRepository<AcaoExtensao, Integer>, JpaSpecificationExecutor<AcaoExtensao> {
	
	@Query("SELECT ac FROM AcaoExtensao as ac WHERE (ac.parecerRelator.responsavel.id = :id)")
	List<AcaoExtensao> getParecerRelator(@Param("id") Integer id);
	
	@Query("SELECT ac FROM AcaoExtensao as ac WHERE (ac.parecerTecnico.responsavel.id = :id)")
	List<AcaoExtensao> getParecerTecnico(@Param("id") Integer id);
	
	@Query("SELECT ac FROM AcaoExtensao as ac,equipe_de_trabalho as p WHERE ac.id = p.acaoExtensao.id AND p.participante.id = :id AND ac.coordenador.id != :id")
	List<AcaoExtensao> getParticipacao(@Param("id") Integer id);
	
	List<AcaoExtensao> findByCoordenador(Pessoa pessoa);

	List<AcaoExtensao> findByCoordenadorAndStatusIn (Pessoa coordenador, Collection<Status> status);
	 
	List<AcaoExtensao> findByStatusIn (Collection<Status> status);
	
	List<AcaoExtensao> findByModalidadeAndStatus(Modalidade modalidade, Status status);
	
	@Query("SELECT COUNT(*) FROM AcaoExtensao as ac WHERE ac.coordenador.cpf =:cpf")
	Integer countAcoesCoordenador(@Param("cpf") String cpf);
	
	@Query("SELECT COUNT(*) FROM AcaoExtensao as ac WHERE ac.status !=:status")
	Integer countAcoesTramitacao(@Param("status") Status status);
	
	List<AcaoExtensao> findAll(Specification<AcaoExtensao> spec);
	
	List<AcaoExtensao> findByStatus(Status status);
	
	@Query("SELECT coordenador.cpf FROM AcaoExtensao WHERE id=:idAcao")
	String findCoordenadorById(@Param("idAcao") Integer id);
	
}
