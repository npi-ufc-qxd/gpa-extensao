package ufc.quixada.npi.gpa.service.impl;

import static ufc.quixada.npi.gpa.util.Constants.MENSAGEM_PERMISSAO_NEGADA;

import java.util.List;

import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.multipart.MultipartFile;

import ufc.quixada.npi.gpa.exception.GpaExtensaoException;
import ufc.quixada.npi.gpa.model.AcaoExtensao;
import ufc.quixada.npi.gpa.model.Aluno;
import ufc.quixada.npi.gpa.model.Aluno.Curso;
import ufc.quixada.npi.gpa.model.AcaoExtensao.Status;
import ufc.quixada.npi.gpa.model.Documento;
import ufc.quixada.npi.gpa.repository.AcaoExtensaoRepository;
import ufc.quixada.npi.gpa.repository.AlunoRepository;
import ufc.quixada.npi.gpa.repository.BolsaRepository;
import ufc.quixada.npi.gpa.service.AcaoExtensaoService;
import ufc.quixada.npi.gpa.service.DocumentoService;
import ufc.quixada.npi.gpa.service.ParticipacaoService;
import ufc.quixada.npi.gpa.specification.AcaoExtensaoEspecification;

@Named
public class AcaoExtensaoServiceImpl implements AcaoExtensaoService{

	@Autowired
	private AcaoExtensaoRepository acaoExtensaoRepository;
	
	@Autowired
	private DocumentoService documentoService;
	
	@Autowired
	private ParticipacaoService participacaoService;
		
	@Autowired
	private BolsaRepository bolsaRepository;
	
	@Autowired
	private AlunoRepository alunoRepository;
	
	@Override
	public void salvarAcaoExtensao(AcaoExtensao acaoExtensao, MultipartFile arquivo) throws GpaExtensaoException {
		acaoExtensao.setStatus(Status.NOVO);
		salvarAcao(acaoExtensao, arquivo);
	}
	
	@Override
	public void salvarAcaoRetroativa(AcaoExtensao acaoExtensao, MultipartFile arquivo, Integer cargaHorariaCoordenador) throws GpaExtensaoException {
		acaoExtensao.setStatus(Status.APROVADO);
		acaoExtensao.setAtivo(true);
		salvarAcao(acaoExtensao, arquivo);
		participacaoService.participacaoCoordenador(acaoExtensao, cargaHorariaCoordenador);
	}
	
	private void salvarAcao(AcaoExtensao acaoExtensao, MultipartFile arquivo) throws GpaExtensaoException{
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
		old.setBolsasSolicitadas(nova.getBolsasSolicitadas());
		return old;
	}

	@Override
	public void encerrarAcao(Integer idAcao) throws GpaExtensaoException {
		AcaoExtensao acao = acaoExtensaoRepository.findOne(idAcao);
		acao.setAtivo(false);
		bolsaRepository.inativarBolsas(idAcao);
		acaoExtensaoRepository.save(acao);
	}

	@Override
	public List<AcaoExtensao> buscarAcoesCursoAno(Curso curso, Integer ano) {
		
		List<AcaoExtensao> acoes = null;
		
		if(curso != null && ano != null){
			Specification<AcaoExtensao> specification = AcaoExtensaoEspecification.buscarAno(ano);
			List<AcaoExtensao> acoesAno = acaoExtensaoRepository.findAll(specification);
			
			List<Aluno> alunos = alunoRepository.findByCurso(curso.getDescricao()); 
			List<AcaoExtensao> acoesCurso = bolsaRepository.findByBolsistaIn(alunos);
			acoes = acaoExtensaoRepository.findByAnoAndCursoIn(acoesAno, acoesCurso);
			
			return acoes;
		}
		if(curso != null){
			List<Aluno> alunos = alunoRepository.findByCurso(curso.getDescricao());
			acoes = bolsaRepository.findByBolsistaIn(alunos);
			return acoes;
		}
		if(ano != null){
			Specification<AcaoExtensao> specification = AcaoExtensaoEspecification.buscarAno(ano);
			acoes = acaoExtensaoRepository.findAll(specification);
			return acoes;
		}
		return acoes;
	}
	
	public List<AcaoExtensao> buscarTodasParticipacoes(List<AcaoExtensao> acoesEspecification, List<AcaoExtensao> acoesParticipacao){
		List<AcaoExtensao> acoes = null;
		
		if(acoesEspecification.size() > 0){
			acoes = acaoExtensaoRepository.findByParticipante(acoesEspecification, acoesParticipacao);
		}
		return acoes;
	}
}
