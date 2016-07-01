package ufc.quixada.npi.gpa.service.impl;

import static ufc.quixada.npi.gpa.util.Constants.MESSAGE_CADASTRO_ERROR;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import ufc.quixada.npi.gpa.exception.GpaExtensaoException;
import ufc.quixada.npi.gpa.model.Documento;
import ufc.quixada.npi.gpa.repository.DocumentoRepository;
import ufc.quixada.npi.gpa.service.DocumentoService;

public class DucumentoServiceImpl implements DocumentoService{
	
	@Autowired
	DocumentoRepository documentoRepository;

	@Override
	public Documento save(MultipartFile arquivo) throws GpaExtensaoException {
		Documento documento = new Documento();
		if(!(arquivo.getOriginalFilename().toString().equals(""))){
			try{
				documento.setArquivo(arquivo.getBytes());
				documento.setNome(arquivo.getOriginalFilename().toString());
				documentoRepository.save(documento);
			}catch(IOException e){
				throw new GpaExtensaoException(MESSAGE_CADASTRO_ERROR);
			}
		}
		return documento;
	}

}
