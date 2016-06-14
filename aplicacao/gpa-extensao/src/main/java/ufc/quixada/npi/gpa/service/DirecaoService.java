package ufc.quixada.npi.gpa.service;

import ufc.quixada.npi.gpa.exception.GpaExtensaoException;
import ufc.quixada.npi.gpa.model.Parecer;

public interface DirecaoService {

	void atribuirParecerista(Integer idAcaoExtensao, Parecer parecerTecnico) throws GpaExtensaoException;
}
