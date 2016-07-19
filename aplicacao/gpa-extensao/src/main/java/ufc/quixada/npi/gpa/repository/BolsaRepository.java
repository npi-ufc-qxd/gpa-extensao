package ufc.quixada.npi.gpa.repository;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import ufc.quixada.npi.gpa.model.Bolsa;

public interface BolsaRepository extends CrudRepository<Bolsa, Integer>{
	List<Bolsa> findAll();
	List<Bolsa> findByBolsista_id(Integer idAluno);
	
	@Modifying
	@Transactional
	@Query("update Bolsa set ativo=FALSE WHERE acao_id=:acaoId")
	public abstract int inativarBolsas(@Param("acaoId") Integer acaoId);
}
