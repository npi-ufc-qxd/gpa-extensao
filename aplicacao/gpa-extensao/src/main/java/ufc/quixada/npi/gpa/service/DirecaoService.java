package ufc.quixada.npi.gpa.service;

import java.util.List;

import ufc.quixada.npi.gpa.exception.GpaExtensaoException;
import ufc.quixada.npi.gpa.model.AcaoExtensao;
import ufc.quixada.npi.gpa.model.Pessoa;

public interface DirecaoService {

	List<Pessoa> getPossiveisPareceristas(Integer idAcaoExtensao);

	void atribuirParecerista(AcaoExtensao acaoExtensao) throws GpaExtensaoException;

	void atribuirRelator(AcaoExtensao acaoExtensao) throws GpaExtensaoException;
}
