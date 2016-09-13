package ufc.quixada.npi.gpa.controller;

import static ufc.quixada.npi.gpa.util.Constants.ACOES;
import static ufc.quixada.npi.gpa.util.Constants.BUSCAR;
import static ufc.quixada.npi.gpa.util.Constants.COORDENADORES;
import static ufc.quixada.npi.gpa.util.Constants.CURSOS;
import static ufc.quixada.npi.gpa.util.Constants.ERRO;
import static ufc.quixada.npi.gpa.util.Constants.ESTADO;
import static ufc.quixada.npi.gpa.util.Constants.MODALIDADES;
import static ufc.quixada.npi.gpa.util.Constants.PAGINA_ACAO_EXTENSAO;
import static ufc.quixada.npi.gpa.util.Constants.PAGINA_BUSCAR_ACAO_EXTENSAO;
import static ufc.quixada.npi.gpa.util.Constants.PAGINA_DETALHES_SERVIDOR;
import static ufc.quixada.npi.gpa.util.Constants.PARTICIPACOES;
import static ufc.quixada.npi.gpa.util.Constants.PESSOA_LOGADA;
import static ufc.quixada.npi.gpa.util.Constants.REDIRECT_PAGINA_BUSCAR_ACAO_EXTENSAO;
import static ufc.quixada.npi.gpa.util.Constants.SERVIDOR;
import static ufc.quixada.npi.gpa.util.Constants.SERVIDORES;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ufc.quixada.npi.gpa.exception.GpaExtensaoException;
import ufc.quixada.npi.gpa.model.AcaoExtensao;
import ufc.quixada.npi.gpa.model.AcaoExtensao.Modalidade;
import ufc.quixada.npi.gpa.model.AcaoExtensao.Status;
import ufc.quixada.npi.gpa.model.Aluno.Curso;
import ufc.quixada.npi.gpa.model.Pessoa;
import ufc.quixada.npi.gpa.model.Servidor;
import ufc.quixada.npi.gpa.repository.AcaoExtensaoRepository;
import ufc.quixada.npi.gpa.repository.ParticipacaoRepository;
import ufc.quixada.npi.gpa.repository.PessoaRepository;
import ufc.quixada.npi.gpa.repository.ServidorRepository;
import ufc.quixada.npi.gpa.service.AcaoExtensaoService;
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
	private ParticipacaoRepository participacaoRepository;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private AcaoExtensaoService acaoExtensaoService;
	
	@ModelAttribute(PESSOA_LOGADA)
	public String pessoaLogada(Authentication authentication){
		return pessoaRepository.findByCpf(authentication.getName()).getNome();
	}
	
	@RequestMapping(PAGINA_ACAO_EXTENSAO)
	public String buscarAcaoForm(Model model) {
		model.addAttribute(COORDENADORES, servidorRespository.findAll());
		model.addAttribute(ACOES, acaoExtensaoRepository.findByStatusAndAtivoIn(Status.APROVADO, true));
		model.addAttribute(MODALIDADES, Modalidade.values());
		model.addAttribute(CURSOS, Curso.values());
		
		return PAGINA_BUSCAR_ACAO_EXTENSAO;
	}
	
	@RequestMapping(value=PAGINA_ACAO_EXTENSAO, params = {"coordenador", "modalidade", "ano"}, method = RequestMethod.GET)
	public String buscarAcao(@RequestParam("coordenador") Integer idCoordenador, @RequestParam("modalidade") Modalidade modalidade,
			@RequestParam("estado") String estado, @RequestParam("ano") Integer ano, Model model, RedirectAttributes attr)  {
		
		if(idCoordenador == null && modalidade == null && ano == null && estado.isEmpty()) {
			return REDIRECT_PAGINA_BUSCAR_ACAO_EXTENSAO;
		}
		
		Pessoa coordenador = null;
		Specification<AcaoExtensao> specification = AcaoExtensaoEspecification.buscar(coordenador, modalidade, estado, ano);
		List<AcaoExtensao> acoes =  acaoExtensaoRepository.findAll(specification);
		
		
		if(idCoordenador != null) {
			coordenador = pessoaRepository.findOne(idCoordenador);
			List<AcaoExtensao> acoesComParticipacao = participacaoRepository.findByParticipante(coordenador);
			
			try {
				acoes = acaoExtensaoService.buscarTodasParticipacoes(acoes, acoesComParticipacao);
			} catch (GpaExtensaoException e) {
				attr.addFlashAttribute(ERRO, e);
			}
			
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
			
		model.addAttribute(ACOES, acoes);
		model.addAttribute(COORDENADORES, servidorRespository.findAll());
		model.addAttribute(MODALIDADES, Modalidade.values());
		model.addAttribute(CURSOS, Curso.values());
		
		return PAGINA_BUSCAR_ACAO_EXTENSAO;
	}
	
	@RequestMapping(value = PAGINA_ACAO_EXTENSAO, params = {"curso", "ano"}, method = RequestMethod.GET)
	public String buscarAcaoCurso(@RequestParam("curso") Curso curso, @RequestParam("ano") Integer ano, Model model, RedirectAttributes attr){
		
		List<AcaoExtensao> acoes;
		
		if(curso == null && ano == null){
			return REDIRECT_PAGINA_BUSCAR_ACAO_EXTENSAO;
		}
		
		try {
			acoes = acaoExtensaoService.buscarAcoesCursoAno(curso, ano);
			
			if(ano != null){
				model.addAttribute("ano", ano);
			}
			if(curso != null){
				model.addAttribute("curso", curso.getDescricao());
			}
			
			model.addAttribute(ACOES, acoes);
		} catch (GpaExtensaoException e) {
			attr.addFlashAttribute(ERRO, e);
		}
		
		model.addAttribute(COORDENADORES, servidorRespository.findAll());
		model.addAttribute(MODALIDADES, Modalidade.values());
		model.addAttribute(CURSOS, Curso.values());
		
		return PAGINA_BUSCAR_ACAO_EXTENSAO;
	}
	
	@RequestMapping("/servidor")
	public String detalhesServidor(Model model) {
		
		model.addAttribute(SERVIDORES, servidorRespository.findAll());
		return PAGINA_DETALHES_SERVIDOR;
	}
	
	@RequestMapping(value="/servidor", params = {"servidor"})
	public String detalhesServidor(@RequestParam("servidor") Servidor servidor, Model model) {
		
		model.addAttribute(SERVIDORES, servidorRespository.findAll());
		model.addAttribute(SERVIDOR, servidor);
		model.addAttribute(PARTICIPACOES,
				participacaoRepository.findByParticipanteAndAcaoExtensao_status(servidor.getPessoa(), Status.APROVADO));

		return PAGINA_DETALHES_SERVIDOR;
	}
	
	@RequestMapping("/servidor/{id}")
	public String detalhesServidorPessoa(@PathVariable("id") Integer id, Model model) {
		Servidor servidor = servidorRespository.findByPessoa_id(id);
		return detalhesServidor(servidor, model);
	}
	
}
