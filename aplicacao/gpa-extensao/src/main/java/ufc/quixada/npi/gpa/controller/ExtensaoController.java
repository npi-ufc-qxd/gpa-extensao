package ufc.quixada.npi.gpa.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import ufc.quixada.npi.gpa.model.AcaoExtensao.Status;
import ufc.quixada.npi.gpa.model.Pessoa;
import ufc.quixada.npi.gpa.repository.AcaoExtensaoRepository;
import ufc.quixada.npi.gpa.repository.PessoaRepository;

@Controller
public class ExtensaoController {
	
	@RequestMapping("/")
	public String index() {
		return "index";
	}
	@Autowired
	private AcaoExtensaoRepository acaoExtensaoRepository;
	@Autowired
	private PessoaRepository pessoaRepository;
	@RequestMapping("/listagem")
	public String listagem(Model model, Authentication authentication) {
		Pessoa pessoa = pessoaRepository.getByCpf(authentication.getName());
		//"NOVO", "AGUARDANDO PARECERISTA"
		List<Status> status = Arrays.asList(Status.values());
		model.addAttribute("acoes", acaoExtensaoRepository.findByCoordenadorAndStatusIn(pessoa, status));
		return "coordenacao/listagem/listagem";
	}
}
