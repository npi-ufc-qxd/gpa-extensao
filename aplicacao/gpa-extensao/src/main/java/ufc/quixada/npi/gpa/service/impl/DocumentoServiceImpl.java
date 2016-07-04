package ufc.quixada.npi.gpa.service.impl;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import ufc.quixada.npi.gpa.exception.GpaExtensaoException;
import ufc.quixada.npi.gpa.model.Documento;
import ufc.quixada.npi.gpa.repository.DocumentoRepository;
import ufc.quixada.npi.gpa.service.DocumentoService;

@Service
public class DocumentoServiceImpl implements DocumentoService{
	
	@Autowired
	private DocumentoRepository documentoRepository;

	@Override
	public Documento save(MultipartFile arquivo, String message) throws GpaExtensaoException {
		if(arquivo != null && !(arquivo.getOriginalFilename().toString().equals(""))){
			try{
				Documento documento = new Documento();
				documento.setArquivo(arquivo.getBytes());
				documento.setNome(arquivo.getOriginalFilename().toString());
				documentoRepository.save(documento);
				
				return documento;
			}catch(IOException e){
				throw new GpaExtensaoException(message);
			}
		}
		return null;
	}

}
