package ufc.quixada.npi.gpa.service;

import java.util.List;

import ufc.quixada.npi.gpa.model.AcaoExtensao;

public interface AcaoExtensaoService {

	List<AcaoExtensao> getTramitacao(Integer id);
	List<AcaoExtensao> getNovos(Integer id);
	List<AcaoExtensao> getHomologados(Integer id);

}
