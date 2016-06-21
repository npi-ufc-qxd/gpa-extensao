package ufc.quixada.npi.gpa.service.impl;

import java.io.IOException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import ufc.quixada.npi.gpa.exception.GpaExtensaoException;
import ufc.quixada.npi.gpa.model.AcaoExtensao;
import ufc.quixada.npi.gpa.model.AcaoExtensao.Status;
import ufc.quixada.npi.gpa.model.Documento;
import ufc.quixada.npi.gpa.repository.AcaoExtensaoRepository;
import ufc.quixada.npi.gpa.repository.DocumentoRepository;
import ufc.quixada.npi.gpa.service.AcaoExtensaoService;
import static ufc.quixada.npi.gpa.util.Constants.EXCEPTION_RELATORIO;

@Service
public class AcaoExtensaoImpl implements AcaoExtensaoService{

	@Autowired
	private AcaoExtensaoRepository acaoExtensaoRepository;
	
	@Autowired
	private DocumentoRepository documentoRepository;

	@Override
	public void emitirParecer(AcaoExtensao acaoExtensao, MultipartFile arquivo) throws GpaExtensaoException {
		AcaoExtensao acao = acaoExtensaoRepository.findOne(acaoExtensao.getId());
		
		if(! (arquivo.getOriginalFilename().toString().equals(""))){
			try {
				Documento documento = new Documento();
				documento.setArquivo(arquivo.getBytes());
				documento.setNome(arquivo.getOriginalFilename().toString());
				
				documentoRepository.save(documento);
				
				acao.getParecerRelator().setArquivo(documento);
			} catch (IOException e) {
				throw new GpaExtensaoException(EXCEPTION_RELATORIO);
			}
		}
		
		if(acao != null){
			acao.getParecerRelator().setDataRealizacao(new Date());
			acao.getParecerRelator().setPosicionamento(acaoExtensao.getParecerRelator().getPosicionamento());
			acao.getParecerRelator().setParecer(acaoExtensao.getParecerRelator().getParecer());
			
			acao.setStatus(Status.AGUARDANDO_HOMOLOGACAO);
			
			acaoExtensaoRepository.save(acao);
		}
		else{
			throw new GpaExtensaoException(EXCEPTION_RELATORIO);
		}
	}
}
