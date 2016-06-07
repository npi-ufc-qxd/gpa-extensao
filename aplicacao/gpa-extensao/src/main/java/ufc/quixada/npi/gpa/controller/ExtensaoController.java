package ufc.quixada.npi.gpa.controller;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

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
import ufc.quixada.npi.gpa.service.validation.ParticipacaoValidator;

@Controller
public class ExtensaoController {
	
	@Autowired
	ServidorRepository servirdorRepository;
	
	@Autowired
	ParticipacaoRepository participacaoRepository;
	
	@Autowired
	AlunoRepository alunoRepository;
	
	@Autowired
	AcaoExtensaoRepository AcaoExtensaoRepository;
	
	@Inject
	PessoaService pessoaService;
	
	@Inject
	ParticipacaoValidator participacaoValidator;
	
	@RequestMapping("/")
	public String index() {
		return "index";
	}
	
	@RequestMapping(value="/participacoes/{id}", method=RequestMethod.GET)
	public String formAdicionarParticipacao(@PathVariable("id") Integer id, Model model, HttpSession session) {
		
		List<Funcao> funcoes = new ArrayList<Funcao>();
		for(Funcao funcao: Funcao.values()) {
			if(funcao != Funcao.ALUNO_BOLSISTA) {
				funcoes.add(funcao);
			}
		}
		model.addAttribute("idAcao", id);
		model.addAttribute("participacao", new Participacao());
		model.addAttribute("funcoes", funcoes);
		model.addAttribute("instituicoes", Instituicao.values());
		
		return "coordenacao/crud/adicionar-participacao";
	}
	
	@RequestMapping(value="/participacoes/{idAcao}", method=RequestMethod.POST)
	public String AdicionarParticipacao(@ModelAttribute("participacao") Participacao participacao, @PathVariable("idAcao") Integer idAcao, 
			BindingResult result, Model model, HttpSession session, RedirectAttributes redirectAttributes, Authentication authentication) {
		
		Pessoa usuario = pessoaService.getByCpf(authentication.getName());
		AcaoExtensao acao = AcaoExtensaoRepository.findOne(idAcao);
		
		if(acao == null) {
			redirectAttributes.addFlashAttribute("erro", "Projeto inexistente");
			return "/";
		}
		participacao.setAcaoExtensao(acao);
		participacaoValidator.validate(participacao, result);
		
		if(result.hasErrors()) {
			return "coordenacao/crud/adicionar-participacao";
		}
		
		if(participacao.getParticipante() != null && participacao.getParticipante().getId() == usuario.getId()) {
			participacao.setCoordenador(true);
		}
		
		participacao.setDataInicio(acao.getInicio());
		participacao.setDataTermino(acao.getTermino());
		
		participacaoRepository.save(participacao);
		
		return "redirect:/participacoes/"+idAcao;
	}
	
	
	@RequestMapping("/buscarServidores")
	public @ResponseBody List<Servidor> buscarServidores() {
		return servirdorRepository.findAll();
	}
	
	@RequestMapping("/buscarAlunos")
	public @ResponseBody List<Aluno> buscarAlunos() {
		return alunoRepository.findAll();
	}
}
