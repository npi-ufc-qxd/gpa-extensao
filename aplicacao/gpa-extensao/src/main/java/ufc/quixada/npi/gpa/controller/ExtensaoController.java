package ufc.quixada.npi.gpa.controller;

import static ufc.quixada.npi.gpa.util.Constants.PAGINA_ADICIONAR_PARTICIPACAO;
import static ufc.quixada.npi.gpa.util.Constants.PAGINA_INICIAL;
import static ufc.quixada.npi.gpa.util.Constants.PAGINA_LISTAR_PARTICIPACOES;
import static ufc.quixada.npi.gpa.util.Constants.REDIRECT_PAGINA_ADICIONAR_PARTICIPACAO;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ufc.quixada.npi.gpa.model.AcaoExtensao;
import ufc.quixada.npi.gpa.model.Aluno;
import ufc.quixada.npi.gpa.model.Participacao;
import ufc.quixada.npi.gpa.model.Participacao.Funcao;
import ufc.quixada.npi.gpa.model.Participacao.Instituicao;
import ufc.quixada.npi.gpa.model.Pessoa;
import ufc.quixada.npi.gpa.model.Servidor;
import ufc.quixada.npi.gpa.repository.AcaoExtensaoRepository;
import ufc.quixada.npi.gpa.repository.AlunoRepository;
import ufc.quixada.npi.gpa.repository.ParticipacaoRepository;
import ufc.quixada.npi.gpa.repository.ServidorRepository;
import ufc.quixada.npi.gpa.service.PessoaService;
import ufc.quixada.npi.gpa.validator.ParticipacaoValidator;

@Controller
public class ExtensaoController {
	
	@Autowired
	private ServidorRepository servirdorRepository;
	
	@Autowired
	private ParticipacaoRepository participacaoRepository;
	
	@Autowired
	private AlunoRepository alunoRepository;
	
	@Autowired
	private AcaoExtensaoRepository acaoExtensaoRepository;
	
	@Inject
	private PessoaService pessoaService;
	
	@Inject
	private ParticipacaoValidator participacaoValidator;
	
	@RequestMapping("/")
	public String index() {
		return PAGINA_INICIAL;
	}
	
	@RequestMapping(value="/participacoes/{id}", method=RequestMethod.GET)
	public String formAdicionarParticipacao(@PathVariable("id") Integer id, Model model) {
		
		
		model.addAttribute("idAcao", id);
		model.addAttribute("participacao", new Participacao());
		model.addAttribute("funcoes", listaDeFuncoes());
		model.addAttribute("instituicoes", Instituicao.values());
		
		return PAGINA_ADICIONAR_PARTICIPACAO;
	}
	
	@RequestMapping(value="/participacoes/{idAcao}", method=RequestMethod.POST)
	public String adicionarParticipacao(@ModelAttribute("participacao") Participacao participacao, @PathVariable("idAcao") Integer idAcao, 
			BindingResult result, Model model, RedirectAttributes redirectAttributes, Authentication authentication) {
		
		AcaoExtensao acao = acaoExtensaoRepository.findOne(idAcao);
		
		if(acao == null) {
			redirectAttributes.addFlashAttribute("erro", "Projeto inexistente");
			return "/";
		}
		
		participacao.setCpfParticipante(participacao.getCpfParticipante().replaceAll("[^0-9]", ""));
		participacao.setAcaoExtensao(acao);
		participacaoValidator.validate(participacao, result);
		
		if(result.hasErrors()) {
			model.addAttribute("idAcao", idAcao);
			model.addAttribute("participacao", participacao);
			model.addAttribute("funcoes", listaDeFuncoes());
			model.addAttribute("instituicoes", Instituicao.values());
			return PAGINA_ADICIONAR_PARTICIPACAO;
		}
		
		Pessoa usuario = pessoaService.getByCpf(authentication.getName());
		
		if(participacao.getParticipante() != null && participacao.getParticipante().getId() == usuario.getId()) {
			participacao.setCoordenador(true);
		}
		
		participacao.setDataInicio(acao.getInicio());
		participacao.setDataTermino(acao.getTermino());
		
		participacaoRepository.save(participacao);
		
		return REDIRECT_PAGINA_ADICIONAR_PARTICIPACAO+idAcao;
	}
	
	@RequestMapping(value="/listar-participacoes/{id}", method=RequestMethod.GET)
	public String listarParticipacoes(@PathVariable("id") Integer id, Model model) {
		AcaoExtensao acao = acaoExtensaoRepository.findOne(id);
		model.addAttribute("participacoes", participacaoRepository.findByAcaoExtensao(acao));
		
		return PAGINA_LISTAR_PARTICIPACOES;
	}
	
	
	@RequestMapping("/buscarServidores")
	public @ResponseBody List<Servidor> buscarServidores() {
		return servirdorRepository.findAll();
	}
	
	@RequestMapping("/buscarAlunos")
	public @ResponseBody List<Aluno> buscarAlunos() {
		return alunoRepository.findAll();
	}
	
	private List<Funcao> listaDeFuncoes() {
		List<Funcao> funcoes = new ArrayList<Funcao>();
		for(Funcao funcao: Funcao.values()) {
			if(funcao != Funcao.ALUNO_BOLSISTA) {
				funcoes.add(funcao);
			}
		}
		return funcoes;
	}
}
