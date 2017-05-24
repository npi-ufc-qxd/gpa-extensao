package ufc.quixada.npi.gpa.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import ufc.quixada.npi.gpa.model.AcaoExtensao;
import ufc.quixada.npi.gpa.model.Aluno;
import ufc.quixada.npi.gpa.model.Bolsa;
import ufc.quixada.npi.gpa.model.Bolsa.TipoBolsa;

public interface BolsaRepository extends CrudRepository<Bolsa, Integer>{
	List<Bolsa> findAll();
	
	@Query("SELECT DISTINCT YEAR(b.inicio) FROM Bolsa b")
	List<Integer> findAnosInicio();
	
	@Query("SELECT b FROM Bolsa b where YEAR(b.inicio) = :year OR YEAR(b.termino) = :year")
	List<Bolsa> findByYear(@Param("year") Integer year);

	List<Bolsa> findByBolsista(Aluno bolsista);

	List<Bolsa> findByAcaoExtensao_id(Integer id);
	
	Integer countByAcaoExtensaoAndTipoAndAtivo(AcaoExtensao acao, TipoBolsa tipo, boolean ativo);

	List<Bolsa> findByBolsista_id(Integer idAluno);
	
	Bolsa findByAcaoExtensaoAndBolsista(AcaoExtensao acao, Aluno bolsista);
	
	@Query("SELECT b.acaoExtensao FROM Bolsa b WHERE b.bolsista IN(:alunos)")
	List<AcaoExtensao> findByBolsistaIn (@Param("alunos") Collection<Aluno> alunos);
	
	@Modifying
	@Transactional
	@Query("update Bolsa set ativo=FALSE WHERE acao_id=:acaoId")
	public abstract int inativarBolsas(@Param("acaoId") Integer acaoId);
}
