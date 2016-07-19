package ufc.quixada.npi.gpa.repository;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import ufc.quixada.npi.gpa.model.AcaoExtensao;
import ufc.quixada.npi.gpa.model.Aluno;
import ufc.quixada.npi.gpa.model.Bolsa;
import ufc.quixada.npi.gpa.model.Bolsa.TipoBolsa;

public interface BolsaRepository extends CrudRepository<Bolsa, Integer>{
	List<Bolsa> findAll();
	
	List<Bolsa> findByBolsista(Aluno bolsista);

	List<Bolsa> findByAcaoExtensao_id(Integer id);
	
	Integer countByAcaoExtensaoAndTipoAndAtivo(AcaoExtensao acao, TipoBolsa tipo, boolean ativo);

	List<Bolsa> findByBolsista_id(Integer idAluno);
	
	@Modifying
	@Transactional
	@Query("update Bolsa set ativo=FALSE WHERE acao_id=:acaoId")
	public abstract int inativarBolsas(@Param("acaoId") Integer acaoId);
}
