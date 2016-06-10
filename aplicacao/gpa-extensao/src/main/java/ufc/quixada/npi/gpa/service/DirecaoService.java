package ufc.quixada.npi.gpa.service;

import ufc.quixada.npi.gpa.model.AcaoExtensao;
import ufc.quixada.npi.gpa.model.Pessoa;

public interface DirecaoService {

	void atribuirParecerista(AcaoExtensao acaoExtensao, Pessoa parecerista);
}
