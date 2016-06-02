package ufc.quixada.npi.gpa.service;

import java.util.List;

import ufc.quixada.npi.gpa.model.AcaoExtensao;
import ufc.quixada.npi.gpa.model.Pessoa;

public interface AcaoExtensaoService {
	AcaoExtensao buscarPorId(Integer id);
	
	List<AcaoExtensao> buscarAcaoExtensaoPorCoordenador(Pessoa pessoa);
	
	void salvar(AcaoExtensao acaoExtensao);
}
