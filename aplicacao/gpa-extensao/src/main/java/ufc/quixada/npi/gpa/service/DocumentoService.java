package ufc.quixada.npi.gpa.service;


import org.springframework.web.multipart.MultipartFile;

import ufc.quixada.npi.gpa.exception.GpaExtensaoException;
import ufc.quixada.npi.gpa.model.Documento;

public interface DocumentoService {
	
	public Documento getDocumento(Integer idDocumento);
	
	public byte[] getArquivo(Documento documento);

	Documento save(MultipartFile arquivo, String message) throws GpaExtensaoException;
}

