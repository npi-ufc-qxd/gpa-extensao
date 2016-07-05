package ufc.quixada.npi.gpa.service.impl;

import static ufc.quixada.npi.gpa.util.Constants.MESSAGE_SUBMETER_ERROR;
import static ufc.quixada.npi.gpa.util.PersonalConstants.PASTA_DOCUMENTOS_GPA;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import ufc.quixada.npi.gpa.exception.GpaExtensaoException;
import ufc.quixada.npi.gpa.model.AcaoExtensao;
import ufc.quixada.npi.gpa.model.Documento;
import ufc.quixada.npi.gpa.repository.DocumentoRepository;
import ufc.quixada.npi.gpa.service.DocumentoService;

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
	public Documento save(MultipartFile arquivo, AcaoExtensao acaoExtensao) throws GpaExtensaoException {
		if(arquivo != null && !(arquivo.getOriginalFilename().toString().equals(""))){
			try{
				String data = String.valueOf(System.currentTimeMillis());
				
				Documento documento = new Documento();
				documento.setArquivo(arquivo.getBytes());
				documento.setNome(data + "_" + arquivo.getOriginalFilename().toString());
				documento.setCaminho(PASTA_DOCUMENTOS_GPA+"/" + acaoExtensao.getIdentificador() + "/" + documento.getNome());
				documentoRepository.save(documento);
				
				return documento;
			}catch(IOException e){
				throw new GpaExtensaoException(MESSAGE_SUBMETER_ERROR);
			}
		}
		return null;
	}
}

