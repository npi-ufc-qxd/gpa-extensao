package ufc.quixada.npi.gpa.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ufc.quixada.npi.gpa.model.AcaoExtensao;
import ufc.quixada.npi.gpa.model.Participacao;

@Repository
public interface ParticipacaoRepository extends CrudRepository<Participacao, Integer> {

	public List<Participacao> findByAcaoExtensao(AcaoExtensao acao);
}
