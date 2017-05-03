package ufc.quixada.npi.gpa.service;

import ufc.quixada.npi.gpa.exception.GpaExtensaoException;
import ufc.quixada.npi.gpa.model.AcaoExtensao;
import ufc.quixada.npi.gpa.model.Participacao;
import ufc.quixada.npi.gpa.model.Pessoa;

public interface ParticipacaoService {

	Participacao participacaoCoordenador(AcaoExtensao acaoExtensao, Integer cargaHoraria);

	void adicionarParticipanteEquipeTrabalho(AcaoExtensao acaoExtensao, Participacao participacao, Pessoa pessoa)
			throws GpaExtensaoException;

	void excluirParticipanteEquipeTrabalho(AcaoExtensao acaoExtensao, Participacao participacao, Pessoa pessoa) throws GpaExtensaoException;

	void alterarDataParticipacao(AcaoExtensao acaoExtensao, Participacao participacao, Pessoa pessoa)
			throws GpaExtensaoException;

	Participacao buscarParticipante(Participacao participacao);

}
