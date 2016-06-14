package ufc.quixada.npi.gpa.service;

import java.util.List;

import ufc.quixada.npi.gpa.model.AcaoExtensao;

public interface AcaoExtensaoService {
	List<AcaoExtensao> getParecerRelator(Integer id);
	List<AcaoExtensao> getParecerTecnico(Integer id);
	List<AcaoExtensao> getParticipacao(Integer id);
}
