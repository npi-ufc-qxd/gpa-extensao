package ufc.quixada.npi.gpa.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ufc.quixada.npi.gpa.model.AcaoExtensao;
import ufc.quixada.npi.gpa.model.Aluno;
import ufc.quixada.npi.gpa.model.Bolsa;
import ufc.quixada.npi.gpa.model.Bolsa.TipoBolsa;

public interface BolsaRepository extends CrudRepository<Bolsa, Integer>{
	List<Bolsa> findAll();
	
	List<Bolsa> findByBolsista(Aluno bolsista);

	List<Bolsa> findByAcaoExtensao_id(Integer id);
	
	Integer countByAcaoExtensaoAndTipoAndAtivo(AcaoExtensao acao, TipoBolsa tipo, boolean ativo);
}
