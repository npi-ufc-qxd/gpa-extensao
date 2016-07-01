package ufc.quixada.npi.gpa.service;

import org.springframework.web.multipart.MultipartFile;

import ufc.quixada.npi.gpa.exception.GpaExtensaoException;
import ufc.quixada.npi.gpa.model.AcaoExtensao;

public interface AcaoExtensaoService {

	void salvarAcaoExtensao(AcaoExtensao acaoExtensao, MultipartFile arquivo) throws GpaExtensaoException;
	
	void submeterAcaoExtensao(Integer idAcao, AcaoExtensao acaoExtensao, MultipartFile arquivo);
	
	void deletarAcaoExtensao(Integer idAcao,String cpfCoordenador) throws GpaExtensaoException;
}
