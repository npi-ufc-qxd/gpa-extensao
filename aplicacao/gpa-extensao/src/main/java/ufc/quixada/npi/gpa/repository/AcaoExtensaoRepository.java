package ufc.quixada.npi.gpa.repository;

import java.util.Collection;
import java.util.Date;
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
import ufc.quixada.npi.gpa.model.Parecer;
import ufc.quixada.npi.gpa.model.Pessoa;

@Repository
public interface AcaoExtensaoRepository extends CrudRepository<AcaoExtensao, Integer>, JpaSpecificationExecutor<AcaoExtensao> {
	
	@Query("SELECT e.acaoExtensao FROM equipe_de_trabalho AS e where e.participante = :pessoa")
	List<AcaoExtensao> findByParticipacao(@Param("pessoa") Pessoa pessoa);

	@Query("FROM AcaoExtensao AS a where a.parecerTecnico.responsavel = :pessoa AND status in :status")
	List<AcaoExtensao> findByPareceristaAndStatus(@Param("pessoa")Pessoa pessoa, @Param("status") List<Status> status);
	
	@Query("FROM AcaoExtensao AS a where a.parecerRelator.responsavel = :pessoa AND status in :status")
	List<AcaoExtensao> findByRelatorAndStatus(@Param("pessoa")Pessoa pessoa, @Param("status") List<Status> status);

	List<AcaoExtensao> findByStatusIn (Collection<Status> status);

	List<AcaoExtensao> findByStatusNotIn (Collection<Status> status);
	
	List<AcaoExtensao> findByStatusInOrderByInicioDesc(Collection<Status> status);
	
	List<AcaoExtensao> findByModalidadeAndStatus(Modalidade modalidade, Status status);
	
	List<AcaoExtensao> findAll(Specification<AcaoExtensao> spec);
	
	List<AcaoExtensao> findByStatusAndParecerTecnico_prazo(Status status, Date now);
	
	List<AcaoExtensao> findByStatusAndParecerRelator_prazo(Status status, Date now);
	
	@Query("SELECT coordenador.cpf FROM AcaoExtensao WHERE id=:idAcao")
	String findCoordenadorById(@Param("idAcao") Integer id);

    List<AcaoExtensao> findByAtivo(boolean ativo);

	List<AcaoExtensao> findByAtivoAndStatus(boolean ativo, Status status);

	int countByStatusNotIn(List<Status> status);
	
	int countByStatusIn(List<Status> status);
	
	int countByStatus(Status status);
	
	int countByCoordenadorAndStatus(Pessoa coordenador, Status status);
	
	int countByParecerTecnicoInAndStatus(List<Parecer> pareceres, Status status);
	
	int countByParecerRelatorInAndStatus(List<Parecer> pareceres, Status status);

	int countByAtivoAndStatus(boolean ativo, Status status);

	int countByAtivo(boolean ativo);

	@Query("SELECT count(e.acaoExtensao) FROM equipe_de_trabalho AS e where e.participante = :pessoa")
	int countByParticipacao(@Param("pessoa") Pessoa pessoa);

	@Query("SELECT count (a.parecerTecnico.responsavel.id) FROM AcaoExtensao AS a where a.parecerTecnico.responsavel = :pessoa AND status in :status")
	int countByPareceristaAndStatus(@Param("pessoa")Pessoa pessoa, @Param("status") List<Status> status);
	
	@Query("SELECT count(a.parecerRelator.responsavel) FROM AcaoExtensao AS a where a.parecerRelator.responsavel = :pessoa AND status in :status")
	int countByRelatorAndStatus(@Param("pessoa")Pessoa pessoa, @Param("status") List<Status> status);
}
