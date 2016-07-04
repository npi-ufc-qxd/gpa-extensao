package ufc.quixada.npi.gpa.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import ufc.quixada.npi.gpa.exception.GpaExtensaoException;
import ufc.quixada.npi.gpa.model.Documento;
import ufc.quixada.npi.gpa.repository.DocumentoRepository;
import ufc.quixada.npi.gpa.service.DocumentoService;

import static ufc.quixada.npi.gpa.util.Constants.MESSAGE_CADASTRO_ERROR;;


@Service
public class DocumentoServiceImpl implements DocumentoService{

	@Autowired
	private DocumentoRepository documentoRepository;
	
	@Override
	public Documento getDocumento(Integer idDocumento) {
		
		Documento documento = documentoRepository.findOne(idDocumento);
		return documento;
	}

	@Override
	public byte[] getArquivo(Documento documento) {
		return documento.getArquivo();
	}
	
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

