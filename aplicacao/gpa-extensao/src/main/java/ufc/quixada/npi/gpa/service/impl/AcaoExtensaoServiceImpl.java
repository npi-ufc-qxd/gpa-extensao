package ufc.quixada.npi.gpa.service.impl;

import static ufc.quixada.npi.gpa.util.Constants.MENSAGEM_PERMISSAO_NEGADA;

import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import ufc.quixada.npi.gpa.exception.GpaExtensaoException;
import ufc.quixada.npi.gpa.model.AcaoExtensao;
import ufc.quixada.npi.gpa.model.AcaoExtensao.Status;
import ufc.quixada.npi.gpa.model.Documento;
import ufc.quixada.npi.gpa.repository.AcaoExtensaoRepository;
import ufc.quixada.npi.gpa.service.AcaoExtensaoService;
import ufc.quixada.npi.gpa.service.DocumentoService;

@Named
public class AcaoExtensaoServiceImpl implements AcaoExtensaoService{

	@Autowired
	private AcaoExtensaoRepository acaoExtensaoRepository;
	
	@Autowired
	private DocumentoService documentoService;
		
	@Override
	public void salvarAcaoExtensao(AcaoExtensao acaoExtensao, MultipartFile arquivo) throws GpaExtensaoException {

		acaoExtensao.setStatus(Status.NOVO);
		acaoExtensaoRepository.save(acaoExtensao);
		
		String idAcao = acaoExtensao.getId().toString();
		idAcao = completeToLeft(idAcao, '0', 4);
		acaoExtensao.setIdentificador("EXT-".concat(idAcao));
		
		if(!(arquivo.getOriginalFilename().toString().equals(""))){	
			Documento documento = documentoService.save(arquivo, acaoExtensao);
			
			if(documento != null){
				acaoExtensao.setAnexo(documento);
			}
		}
		
		acaoExtensaoRepository.save(acaoExtensao);
	}
	
	@Override
	public void submeterAcaoExtensao(AcaoExtensao acaoExtensao, MultipartFile arquivo) throws GpaExtensaoException {
		AcaoExtensao old = acaoExtensaoRepository.findOne(acaoExtensao.getId());
		old=checkAcaoExtensao(old,acaoExtensao);
		
		Documento documento = documentoService.save(arquivo, old);
		
		if(documento != null) {
			old.setAnexo(documento);
		}
		
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
	public void editarAcaoExtensao(AcaoExtensao acaoExtensao, MultipartFile arquivo) throws GpaExtensaoException {
		AcaoExtensao old = acaoExtensaoRepository.findOne(acaoExtensao.getId());
		
		Documento documento = documentoService.save(arquivo, acaoExtensao);
		
		if(documento != null) {
			acaoExtensao.setAnexo(documento);
		}
		
		old=checkAcaoExtensao(old,acaoExtensao);
		acaoExtensaoRepository.save(old);
	}
	
	@Override
	public void deletarAcaoExtensao(Integer idAcao, String cpfCoordenador) throws GpaExtensaoException{
		AcaoExtensao acao = acaoExtensaoRepository.findOne(idAcao);
		
		if(acao.getStatus().equals(Status.NOVO) && acao.getCoordenador().getCpf().equals(cpfCoordenador)){
			acaoExtensaoRepository.delete(acao);
			
		}else{
			throw new GpaExtensaoException(MENSAGEM_PERMISSAO_NEGADA);
		}
	}
	
	private String completeToLeft(String value, char c, int size) {
		String result = value;
		while (result.length() < size) {
			result = c + result;
		}
		return result;
	}
	
	private AcaoExtensao checkAcaoExtensao(AcaoExtensao old, AcaoExtensao nova){
		old.setTitulo(nova.getTitulo());
		old.setResumo(nova.getResumo());
		old.setInicio(nova.getInicio());
		old.setTermino(nova.getTermino());
		old.setModalidade(nova.getModalidade());
		old.setHorasPraticas(nova.getHorasPraticas());
		old.setHorasTeoricas(nova.getHorasTeoricas());
		old.setBolsasSolicitadas(nova.getBolsasSolicitadas());
		old.setEmenta(nova.getEmenta());
		old.setProgramacao(nova.getProgramacao());
		old.setAnexo(nova.getAnexo());
		return old;
	}
}
