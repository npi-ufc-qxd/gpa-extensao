package ufc.quixada.npi.gpa.controller;

import static ufc.quixada.npi.gpa.util.Constants.PAGINA_INICIAL;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import ufc.quixada.npi.gpa.model.Pessoa;
import ufc.quixada.npi.gpa.repository.PessoaRepository;
import ufc.quixada.npi.gpa.service.AcaoExtensaoService;

@Controller
public class ExtensaoController {

	@RequestMapping("/")
	public String index() {
		return PAGINA_INICIAL;
	}
	@Inject
	private AcaoExtensaoService acaoExtensaoService;
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@RequestMapping("/listagem")
	public String listagem(Model model, Authentication authentication) {
		Pessoa pessoa = pessoaRepository.getByCpf(authentication.getName());
		model.addAttribute("acoesTramitacao", acaoExtensaoService.getTramitacao(pessoa.getId()));
		model.addAttribute("acoesNovas", acaoExtensaoService.getNovos(pessoa.getId()));
		model.addAttribute("acoesHomologadas", acaoExtensaoService.getHomologados(pessoa.getId()));
		return "coordenacao/listagem/listagem";
	}
}
