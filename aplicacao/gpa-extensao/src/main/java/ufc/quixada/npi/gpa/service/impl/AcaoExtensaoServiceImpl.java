package ufc.quixada.npi.gpa.service.impl;

import static ufc.quixada.npi.gpa.util.Constants.MESSAGE_CADASTRO_ERROR;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import ufc.quixada.npi.gpa.exception.GpaExtensaoException;
import ufc.quixada.npi.gpa.model.AcaoExtensao;
import ufc.quixada.npi.gpa.model.AcaoExtensao.Status;
import ufc.quixada.npi.gpa.model.Documento;
import ufc.quixada.npi.gpa.model.Participacao;
import ufc.quixada.npi.gpa.model.Participacao.Funcao;
import ufc.quixada.npi.gpa.model.Participacao.Instituicao;
import ufc.quixada.npi.gpa.model.Pessoa;
import ufc.quixada.npi.gpa.model.Servidor;
import ufc.quixada.npi.gpa.repository.AcaoExtensaoRepository;
import ufc.quixada.npi.gpa.repository.DocumentoRepository;
import ufc.quixada.npi.gpa.repository.PessoaRepository;
import ufc.quixada.npi.gpa.repository.ServidorRepository;
import ufc.quixada.npi.gpa.service.AcaoExtensaoService;

@Named
public class AcaoExtensaoServiceImpl implements AcaoExtensaoService{

	@Autowired
	private AcaoExtensaoRepository acaoExtensaoRepository;
	@Autowired
	private DocumentoRepository documentoRepository;
	@Autowired
	private PessoaRepository pessoaRepository;
	@Autowired
	private ServidorRepository servidorRepository;
		
	@Override
	public void salvarAcaoExtensao(AcaoExtensao acaoExtensao,MultipartFile arquivo, String cpf) throws GpaExtensaoException {
		Pessoa coordenador = pessoaRepository.getByCpf(cpf);
		Participacao participante = new Participacao();
		Servidor servidor = new Servidor();
		List<Participacao> equipeDeTrabalho = new ArrayList<Participacao>();
		
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
		
		servidor = servidorRepository.getByPessoa(coordenador);
		
		if(servidor.getFuncao().equals(Servidor.Funcao.DOCENTE)){
			participante.setFuncao(Funcao.DOCENTE);
		}else if(servidor.getFuncao().equals(Servidor.Funcao.STA)){
			participante.setFuncao(Funcao.STA);
		}
		participante.setParticipante(coordenador);
		participante.setInstituicao(Instituicao.UFC);
		
		equipeDeTrabalho.add(participante);
		acaoExtensao.setEquipeDeTrabalho(equipeDeTrabalho);
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
	
}
