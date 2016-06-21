package ufc.quixada.npi.gpa.service.impl;

import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import static ufc.quixada.npi.gpa.util.Constants.EXCEPTION_RELATORIO;
import static ufc.quixada.npi.gpa.util.Constants.MESSAGE_CADASTRO_ERROR;

import java.io.IOException;
import java.util.Date;

import ufc.quixada.npi.gpa.exception.GpaExtensaoException;
import ufc.quixada.npi.gpa.model.AcaoExtensao;
import ufc.quixada.npi.gpa.model.AcaoExtensao.Status;
import ufc.quixada.npi.gpa.model.Documento;
import ufc.quixada.npi.gpa.model.Pessoa;
import ufc.quixada.npi.gpa.repository.AcaoExtensaoRepository;
import ufc.quixada.npi.gpa.repository.DocumentoRepository;
import ufc.quixada.npi.gpa.repository.PessoaRepository;
import ufc.quixada.npi.gpa.service.AcaoExtensaoService;

@Named
public class AcaoExtensaoServiceImpl implements AcaoExtensaoService{

	@Autowired
	private AcaoExtensaoRepository acaoExtensaoRepository;
	@Autowired
	private DocumentoRepository documentoRepository;
	@Autowired
	private PessoaRepository pessoaRepository;
		
	@Override
	public void salvarAcaoExtensao(AcaoExtensao acaoExtensao,MultipartFile arquivo, String cpf) throws GpaExtensaoException {
		Pessoa coordenador = pessoaRepository.getByCpf(cpf);
		if(!(arquivo.getOriginalFilename().toString().equals(""))){
			try{
				Documento documento = new Documento();
				documento.setArquivo(arquivo.getBytes());
				documento.setNome(arquivo.getOriginalFilename().toString());
				documentoRepository.save(documento);
				acaoExtensao.setAnexo(documento);
			}catch(IOException e){
				throw new GpaExtensaoException(MESSAGE_CADASTRO_ERROR);
			}
		}
		
		
		acaoExtensao.setCoordenador(coordenador);
		acaoExtensao.setStatus(Status.NOVO);
		acaoExtensaoRepository.save(acaoExtensao);
		
		String idAcao = acaoExtensao.getId().toString();
		idAcao = completeToLeft(idAcao, '0', 4);
		acaoExtensao.setIdentificador("EXT-".concat(idAcao));
		acaoExtensaoRepository.save(acaoExtensao);
		
	}
	
	private String completeToLeft(String value, char c, int size) {
		String result = value;
		while (result.length() < size) {
			result = c + result;
		}
		return result;
	}

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
