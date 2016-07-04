package ufc.quixada.npi.gpa.service;

import org.springframework.web.multipart.MultipartFile;

import ufc.quixada.npi.gpa.exception.GpaExtensaoException;
import ufc.quixada.npi.gpa.model.Documento;

public interface DocumentoService {

	Documento save(MultipartFile arquivo) throws GpaExtensaoException;
}
