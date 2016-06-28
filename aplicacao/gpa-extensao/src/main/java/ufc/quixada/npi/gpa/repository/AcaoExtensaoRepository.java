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
	
	@Query("SELECT ac FROM AcaoExtensao as ac WHERE (ac.parecerRelator.responsavel.id = :id)")
	List<AcaoExtensao> getParecerRelator(@Param("id") Integer id);
	
	@Query("SELECT ac FROM AcaoExtensao as ac WHERE (ac.parecerTecnico.responsavel.id = :id)")
	List<AcaoExtensao> getParecerTecnico(@Param("id") Integer id);
	
	@Query("SELECT ac FROM AcaoExtensao as ac,equipe_de_trabalho as p WHERE ac.id = p.acaoExtensao.id AND p.participante.id = :id")
	List<AcaoExtensao> getParticipacao(@Param("id") Integer id);
	
	List<AcaoExtensao> findByCoordenador(Pessoa pessoa);

	List<AcaoExtensao> findByCoordenadorAndStatusIn (Pessoa coordenador, Collection<Status> status);
	 
	List<AcaoExtensao> findByStatusIn (Collection<Status> status);
	
	@Query("SELECT COUNT(*) FROM AcaoExtensao as ac WHERE ac.coordenador.cpf =:cpf")
	Integer countAcoesCoordenador(@Param("cpf") String cpf);
	
	@Query("SELECT COUNT(*) FROM AcaoExtensao as ac WHERE ac.status !=:status")
	Integer countAcoesTramitacao(@Param("status") Status status);
}
