package ufc.quixada.npi.gpa.controller;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ufc.quixada.npi.gpa.model.Participacao;
import ufc.quixada.npi.gpa.model.Participacao.Funcao;
import ufc.quixada.npi.gpa.model.Participacao.Instituicao;
import ufc.quixada.npi.gpa.model.Pessoa;
import ufc.quixada.npi.gpa.service.PessoaService;

@Controller
public class ExtensaoController {
	
	@Inject
	PessoaService pessoaService;
	
	@RequestMapping("/")
	public String index() {
		return "index";
	}
	
	@RequestMapping(value="/participacoes/{id}", method=RequestMethod.GET)
	public String formAdicionarParticipacao(@PathVariable("id") Long id, Model model, HttpSession session) {
		
		List<Funcao> funcoes = new ArrayList<Funcao>();
		for(Funcao funcao: Funcao.values()) {
			if(funcao != Funcao.ALUNO_BOLSISTA) {
				funcoes.add(funcao);
			}
		}
		model.addAttribute("participacao", new Participacao());
		model.addAttribute("funcoes", funcoes);
		model.addAttribute("instituicoes", Instituicao.values());
		
		return "coordenacao/crud/adicionar-participacao";
	}
	
	@RequestMapping(value="/participacoes/{id}", method=RequestMethod.POST)
	public String AdicionarParticipacao(@ModelAttribute("participacao") Participacao participacao,@PathVariable("id") Long id, Model model, HttpSession session) {
		
		return "redirect:/participacoes/1";
	}
	
	@ModelAttribute("pessoas")
	public List<Pessoa> pessoas() {
		return this.pessoaService.findAll();
	}
}
