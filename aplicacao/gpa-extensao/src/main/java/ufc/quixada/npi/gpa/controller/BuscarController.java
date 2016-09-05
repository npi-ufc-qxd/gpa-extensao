package ufc.quixada.npi.gpa.controller;

import static ufc.quixada.npi.gpa.util.Constants.ESTADO;
import static ufc.quixada.npi.gpa.util.Constants.BUSCAR;
import static ufc.quixada.npi.gpa.util.Constants.ACOES;
import static ufc.quixada.npi.gpa.util.Constants.ACOES_COORDENADOR_SIZE;
import static ufc.quixada.npi.gpa.util.Constants.ACOES_DIRECAO_SIZE;
import static ufc.quixada.npi.gpa.util.Constants.COORDENADORES;
import static ufc.quixada.npi.gpa.util.Constants.PAGINA_BUSCAR_ACAO_EXTENSAO;
import static ufc.quixada.npi.gpa.util.Constants.PESSOA_LOGADA;
import static ufc.quixada.npi.gpa.util.Constants.PAGINA_ACAO_EXTENSAO;
import static ufc.quixada.npi.gpa.util.Constants.REDIRECT_PAGINA_BUSCAR_ACAO_EXTENSAO;

import java.util.Arrays;
import java.util.List;

import static ufc.quixada.npi.gpa.util.Constants.MODALIDADES;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ufc.quixada.npi.gpa.model.AcaoExtensao;
import ufc.quixada.npi.gpa.model.AcaoExtensao.Modalidade;
import ufc.quixada.npi.gpa.model.AcaoExtensao.Status;
import ufc.quixada.npi.gpa.model.Aluno;
import ufc.quixada.npi.gpa.model.Bolsa;
import ufc.quixada.npi.gpa.model.Pessoa;
import ufc.quixada.npi.gpa.repository.AcaoExtensaoRepository;
import ufc.quixada.npi.gpa.repository.AlunoRepository;
import ufc.quixada.npi.gpa.repository.BolsaRepository;
import ufc.quixada.npi.gpa.repository.PessoaRepository;
import ufc.quixada.npi.gpa.repository.ServidorRepository;
import ufc.quixada.npi.gpa.specification.AcaoExtensaoEspecification;

@Controller
@RequestMapping(BUSCAR)
@Transactional
public class BuscarController {
	
	@Autowired
	private ServidorRepository servidorRespository;
	
	@Autowired
	private AcaoExtensaoRepository acaoExtensaoRepository;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private AlunoRepository alunoRepository;
	
	@Autowired
	private BolsaRepository bolsaRepository;
	
	@ModelAttribute(ACOES_DIRECAO_SIZE)
	public Integer acoesDirecaoSize(Authentication authentication) {
		List<Status> statusDirecao = Arrays.asList(Status.AGUARDANDO_PARECERISTA, Status.AGUARDANDO_PARECER_TECNICO,
				Status.AGUARDANDO_RELATOR, Status.AGUARDANDO_PARECER_RELATOR, Status.AGUARDANDO_HOMOLOGACAO);
		return acaoExtensaoRepository.countByStatusIn(statusDirecao);
	}
	
	@ModelAttribute(ACOES_COORDENADOR_SIZE)
	public Integer acoesCoordenadorSize(Authentication authentication){
		return acaoExtensaoRepository.countAcoesCoordenador(authentication.getName());
	}
	
	@ModelAttribute(PESSOA_LOGADA)
	public String pessoaLogada(Authentication authentication){
		return pessoaRepository.findByCpf(authentication.getName()).getNome();
	}
	
	@RequestMapping(PAGINA_ACAO_EXTENSAO)
	public String buscarAcaoForm(Model model) {
		model.addAttribute(COORDENADORES, servidorRespository.findAll());
		model.addAttribute(ACOES, acaoExtensaoRepository.findByStatusAndAtivoIn(Status.APROVADO, true));
		model.addAttribute(MODALIDADES, Modalidade.values());
		
		return PAGINA_BUSCAR_ACAO_EXTENSAO;
	}
	
	@RequestMapping(value=PAGINA_ACAO_EXTENSAO, params = {"coordenador", "modalidade", "ano"}, method = RequestMethod.GET)
	public String buscarAcao(@RequestParam("coordenador") Integer idCoordenador, @RequestParam("modalidade") Modalidade modalidade,
			@RequestParam("estado") String estado, @RequestParam("ano") Integer ano, Model model)  {
		
		if(idCoordenador == null && modalidade == null && ano == null && estado.isEmpty()) {
			return REDIRECT_PAGINA_BUSCAR_ACAO_EXTENSAO;
		}
		
		Pessoa coordenador = null;
		
		if(idCoordenador != null) {
			coordenador = pessoaRepository.findOne(idCoordenador);
			model.addAttribute("coordenador", coordenador.getNome());
		}
		if(modalidade != null) {
			model.addAttribute("modalidade", modalidade.getDescricao());
		}
		if(ano != null) {
			model.addAttribute("ano", ano);
		}
		if(!estado.isEmpty()) {
			if("true".equals(estado)) {
				model.addAttribute(ESTADO, "Ativo");
			} else if("false".equals(estado)) {
				model.addAttribute(ESTADO, "Inativo");
			}
		}
		
		Specification<AcaoExtensao> specification = AcaoExtensaoEspecification.buscar(coordenador, modalidade, estado, ano);
		
		model.addAttribute(ACOES, acaoExtensaoRepository.findAll(specification));
		model.addAttribute(COORDENADORES, servidorRespository.findAll());
		model.addAttribute(MODALIDADES, Modalidade.values());
		
		return PAGINA_BUSCAR_ACAO_EXTENSAO;
	}
	
	@RequestMapping(value = PAGINA_ACAO_EXTENSAO, params = {"curso", "ano"}, method = RequestMethod.GET)
	public String buscarAcaoCurso(@RequestParam("curso") String curso, @RequestParam("ano") Integer ano, Model model){
		
		if(curso == null && ano == null){
			return REDIRECT_PAGINA_BUSCAR_ACAO_EXTENSAO;
		}
		if(ano != null && curso != null){
			
			Specification<AcaoExtensao> specification = AcaoExtensaoEspecification.buscarAno(ano);
			List<AcaoExtensao> acoesAno = acaoExtensaoRepository.findAll(specification);
			
			List<Aluno> alunos = alunoRepository.findByCurso(curso); 
			List<AcaoExtensao> acoesCurso = bolsaRepository.findByBolsistaIn(alunos);
			model.addAttribute(ACOES, acaoExtensaoRepository.findByAnoAndCursoIn(acoesAno, acoesCurso));
			
			return PAGINA_BUSCAR_ACAO_EXTENSAO;

		}
		if(ano != null){
			
			Specification<AcaoExtensao> specification = AcaoExtensaoEspecification.buscarAno(ano);
			List<AcaoExtensao> acoes = acaoExtensaoRepository.findAll(specification);
			model.addAttribute(ACOES, acoes);
			
		}
		if(curso != null){
			
			List<Aluno> alunos = alunoRepository.findByCurso(curso);
			List<AcaoExtensao> acoes = bolsaRepository.findByBolsistaIn(alunos); 
			model.addAttribute(ACOES, acoes);
		}
		
		return PAGINA_BUSCAR_ACAO_EXTENSAO;
	}
}
