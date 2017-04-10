package ufc.quixada.npi.gpa.service;

import ufc.quixada.npi.gpa.exception.GpaExtensaoException;
import ufc.quixada.npi.gpa.model.AcaoExtensao;
import ufc.quixada.npi.gpa.model.Pendencia;

public interface ParecerService {
	void atribuirParecerista(AcaoExtensao acaoExtensao) throws GpaExtensaoException;

	void atribuirRelator(AcaoExtensao acaoExtensao) throws GpaExtensaoException;

	void solicitarResolucaoPendencias(Integer idAcao, Pendencia pendencia);

	void emitirParecer(AcaoExtensao acaoExtensao) throws GpaExtensaoException;
}
