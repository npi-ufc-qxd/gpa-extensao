package ufc.quixada.npi.gpa.service.impl;

import static ufc.quixada.npi.gpa.util.Constants.EXCEPTION_RELATORIO;
import static ufc.quixada.npi.gpa.util.Constants.MESSAGE_CADASTRO_ERROR;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
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
import ufc.quixada.npi.gpa.model.Pendencia;
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
	public Integer salvarAcaoExtensao(AcaoExtensao acaoExtensao,MultipartFile arquivo, String cpf) throws GpaExtensaoException {
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
		return acaoExtensao.getId();
	}
	
	@Override
	public void submeterAcaoExtensao(Integer idAcao, AcaoExtensao acaoExtensao, MultipartFile arquivo) {
		AcaoExtensao old = acaoExtensaoRepository.findOne(idAcao);
		old=checkAcaoExtensao(old,acaoExtensao);
		
		switch (old.getStatus()) {
			case RESOLVENDO_PENDENCIAS_PARECER:
				old.setStatus(Status.AGUARDANDO_PARECER_TECNICO);
				old.getParecerTecnico().setPendenciasResolvidas();
				break;
				
			case RESOLVENDO_PENDENCIAS_RELATO:
				old.setStatus(Status.AGUARDANDO_PARECER_RELATOR);
				old.getParecerRelator().setPendenciasResolvidas();
				break;
				
			default:
				old.setStatus(Status.AGUARDANDO_PARECERISTA);
				break;
		}
		
		acaoExtensaoRepository.save(old);
	}
	
	@Override
	public void solicitarResolucaoPendencias(Integer idAcao, Pendencia pendencia){
		AcaoExtensao acaoExtensao = acaoExtensaoRepository.findOne(idAcao);
		
		pendencia.setDataDeSolicitacao(new Date());
		
		switch (acaoExtensao.getStatus()) {
		case AGUARDANDO_PARECER_TECNICO:
			acaoExtensao.getParecerTecnico().addPendencia(pendencia);
			acaoExtensao.setStatus(Status.RESOLVENDO_PENDENCIAS_PARECER);
			break;
			
		case AGUARDANDO_PARECER_RELATOR:
			acaoExtensao.getParecerRelator().addPendencia(pendencia);
			acaoExtensao.setStatus(Status.RESOLVENDO_PENDENCIAS_RELATO);
			break;

		default:
			break;
		}
		
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
	public void emitirParecerRelator(AcaoExtensao acaoExtensao, MultipartFile arquivo) throws GpaExtensaoException {
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

	@Override
	public void emitirParecerTecnico(AcaoExtensao acaoExtensao, MultipartFile arquivo) throws GpaExtensaoException {
		AcaoExtensao acao = acaoExtensaoRepository.findOne(acaoExtensao.getId());
		
		if(! (arquivo.getOriginalFilename().toString().equals(""))){
			try {
				Documento documento = new Documento();
				documento.setArquivo(arquivo.getBytes());
				documento.setNome(arquivo.getOriginalFilename().toString());
				
				documentoRepository.save(documento);
				acao.getParecerTecnico().setArquivo(documento);
				
			} catch (IOException e) {
				throw new GpaExtensaoException(EXCEPTION_RELATORIO);
			}
		}
		
		if(acao != null){
				acao.getParecerTecnico().setDataRealizacao(new Date());
				acao.getParecerTecnico().setPosicionamento(acaoExtensao.getParecerTecnico().getPosicionamento());
				acao.getParecerTecnico().setParecer(acaoExtensao.getParecerTecnico().getParecer());
				
				acao.setStatus(Status.AGUARDANDO_RELATOR);
				
				acaoExtensaoRepository.save(acao);
		}
		else{
			throw new GpaExtensaoException(EXCEPTION_RELATORIO);
		}	
	}
	
	private AcaoExtensao checkAcaoExtensao(AcaoExtensao old, AcaoExtensao nova){
		old.setTitulo(nova.getTitulo());
		old.setResumo(nova.getResumo());
		old.setInicio(nova.getInicio());
		old.setTermino(nova.getTermino());
		old.setModalidade(nova.getModalidade());
		old.setHorasPraticas(nova.getHorasPraticas());
		old.setHorasTeoricas(nova.getHorasTeoricas());
		old.setEmenta(nova.getEmenta());
		old.setProgramacao(nova.getProgramacao());
		return old;
	}
}
