package ufc.quixada.npi.gpa.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ufc.quixada.npi.gpa.model.AcaoExtensao;
import ufc.quixada.npi.gpa.model.Participacao;
import ufc.quixada.npi.gpa.model.Participacao.Funcao;
import ufc.quixada.npi.gpa.model.Pessoa;

@Repository
public interface ParticipacaoRepository extends CrudRepository<Participacao, Integer> {

	public List<Participacao> findByAcaoExtensao(AcaoExtensao acao);
	
	public List<Participacao> findByAcaoExtensaoAndFuncao(AcaoExtensao acao, Funcao funcao);
	
	public Participacao findByParticipanteAndAcaoExtensao(Pessoa participante, AcaoExtensao acaoExtensao);
}
