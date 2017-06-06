package ufc.quixada.npi.gpa.service;

import java.text.ParseException;

import ufc.quixada.npi.gpa.exception.GpaExtensaoException;
import ufc.quixada.npi.gpa.model.AcaoExtensao;
import ufc.quixada.npi.gpa.model.Pendencia;

public interface ParecerService {
	void atribuirParecerista(AcaoExtensao acaoExtensao, String prazo) throws GpaExtensaoException, ParseException;

	void atribuirRelator(AcaoExtensao acaoExtensao, String prazo) throws GpaExtensaoException, ParseException;

	void solicitarResolucaoPendencias(Integer idAcao, Pendencia pendencia) throws GpaExtensaoException;

	void emitirParecer(AcaoExtensao acaoExtensao) throws GpaExtensaoException;
}
