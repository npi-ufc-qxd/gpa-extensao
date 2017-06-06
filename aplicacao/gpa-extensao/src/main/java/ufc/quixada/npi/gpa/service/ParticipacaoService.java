package ufc.quixada.npi.gpa.service;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.MalformedURLException;

import com.itextpdf.text.DocumentException;

import java.util.Date;


import ufc.quixada.npi.gpa.exception.GpaExtensaoException;
import ufc.quixada.npi.gpa.model.AcaoExtensao;
import ufc.quixada.npi.gpa.model.Participacao;
import ufc.quixada.npi.gpa.model.Pessoa;

public interface ParticipacaoService {

	Participacao participacaoCoordenador(AcaoExtensao acaoExtensao, Integer cargaHoraria);

	void adicionarParticipanteEquipeTrabalho(AcaoExtensao acaoExtensao, Participacao participacao, Pessoa pessoa)
			throws GpaExtensaoException;
	
	ByteArrayInputStream emitirDeclaracaoParticipanteEquipeTrabalho(Integer acaoExtensao, Integer pessoa) throws DocumentException, MalformedURLException, IOException;
	

	void excluirParticipanteEquipeTrabalho(AcaoExtensao acaoExtensao, Participacao participacao, Pessoa pessoa)
			throws GpaExtensaoException;

	void alterarDataParticipacao(AcaoExtensao acaoExtensao, Participacao participacao, Pessoa pessoa, Date dataInicio,
			Date dataTermino) throws GpaExtensaoException;

	Participacao buscarParticipante(Participacao participacao);

}
