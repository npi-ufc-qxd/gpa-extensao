package ufc.quixada.npi.gpa.service;

import org.springframework.web.multipart.MultipartFile;

import ufc.quixada.npi.gpa.exception.GpaExtensaoException;
import ufc.quixada.npi.gpa.model.AcaoExtensao;
import ufc.quixada.npi.gpa.model.Pendencia;

public interface AcaoExtensaoService {

	void salvarAcaoExtensao(AcaoExtensao acaoExtensao,MultipartFile arquivo, String cpf) throws GpaExtensaoException;
	
	void submeterAcaoExtensao(Integer idAcao, AcaoExtensao acaoExtensao, MultipartFile arquivo);
	
	void solicitarResolucaoPendencias(Integer idAcao, Pendencia pendencia);

	void emitirParecerRelator(AcaoExtensao acaoExtensao, MultipartFile arquivo) throws GpaExtensaoException;
	
	void emitirParecerTecnico(AcaoExtensao acaoExtensao, MultipartFile arquivo) throws GpaExtensaoException;
}
