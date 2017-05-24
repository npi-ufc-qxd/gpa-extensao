package ufc.quixada.npi.gpa.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.MalformedURLException;

import com.itextpdf.text.DocumentException;

import ufc.quixada.npi.gpa.exception.GpaExtensaoException;
import ufc.quixada.npi.gpa.model.AcaoExtensao;
import ufc.quixada.npi.gpa.model.Participacao;
import ufc.quixada.npi.gpa.model.Pessoa;

public interface ParticipacaoService {

	Participacao participacaoCoordenador(AcaoExtensao acaoExtensao, Integer cargaHoraria);

	void adicionarParticipanteEquipeTrabalho(AcaoExtensao acaoExtensao, Participacao participacao, Pessoa pessoa)
			throws GpaExtensaoException;
	
	ByteArrayInputStream emitirDeclaracaoParticipanteEquipeTrabalho(Integer acaoExtensao, Integer pessoa) throws DocumentException, MalformedURLException, IOException;
	

	
}
