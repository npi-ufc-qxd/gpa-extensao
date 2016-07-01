package ufc.quixada.npi.gpa.service;

import java.util.List;

import ufc.quixada.npi.gpa.model.Pessoa;

public interface DirecaoService {

	List<Pessoa> getPossiveisPareceristas(Integer idAcaoExtensao);
}
