package ufc.quixada.npi.gpa.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import ufc.quixada.npi.gpa.model.AcaoExtensao;
import ufc.quixada.npi.gpa.model.Documento;
import ufc.quixada.npi.gpa.model.Papel;
import ufc.quixada.npi.gpa.model.Pessoa;
import ufc.quixada.npi.gpa.model.AcaoExtensao.Status;
import ufc.quixada.npi.gpa.repository.AcaoExtensaoRepository;
import ufc.quixada.npi.gpa.repository.DocumentoRepository;
import ufc.quixada.npi.gpa.service.AcaoExtensaoService;

@Named
public class AcaoExtensaoServiceImpl implements AcaoExtensaoService{

	@Autowired
	private AcaoExtensaoRepository acaoExtensaoRepository;
	@Autowired
	private DocumentoRepository documentoRepository;
		
	@Override
	public Void salvarAcaoExtensao(AcaoExtensao acaoExtensao,MultipartFile arquivo, Pessoa coordenador) throws IOException {
		
		if(!(arquivo.getOriginalFilename().toString().equals(""))){
			Documento documento = new Documento();
			documento.setArquivo(arquivo.getBytes());
			documento.setNome(arquivo.getOriginalFilename().toString());
			documentoRepository.save(documento);
			acaoExtensao.setAnexo(documento);
		}
		
		
		acaoExtensao.setCoordenador(coordenador);
		acaoExtensao.setBolsasRecebidas(1);
		acaoExtensao.setStatus(Status.NOVO);
		acaoExtensaoRepository.save(acaoExtensao);
		
		String idAcao = acaoExtensao.getId().toString();
		idAcao = completeToLeft(idAcao, '0', 4);
		acaoExtensao.setIdentificador("EXT-".concat(idAcao));
		acaoExtensaoRepository.save(acaoExtensao);
		
		return null;
	}
	
	public static String completeToLeft(String value, char c, int size) {
		String result = value;
		while (result.length() < size) {
			result = c + result;
		}
		return result;
	}
	
}
