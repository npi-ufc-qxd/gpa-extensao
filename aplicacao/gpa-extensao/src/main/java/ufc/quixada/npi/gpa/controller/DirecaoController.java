package ufc.quixada.npi.gpa.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ufc.quixada.npi.gpa.model.AcaoExtensao.Status;
import ufc.quixada.npi.gpa.model.Pessoa;
import ufc.quixada.npi.gpa.repository.AcaoExtensaoRepository;
import ufc.quixada.npi.gpa.repository.PessoaRepository;
import ufc.quixada.npi.gpa.service.DirecaoService;

@Controller
@RequestMapping("direcao")
public class DirecaoController {

	@Autowired
	private DirecaoService direcaoService;
	
	@Autowired
	private AcaoExtensaoRepository acaoExtensaoRepository;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String atribuirPareceristaForm() {
		return null;
	}

	@RequestMapping("/listagem")
    public String listagem(Model model, Authentication authentication) {
		
        List<Status> statusParecer = Arrays.asList(Status.AGUARDANDO_PARECERISTA, Status.AGUARDANDO_PARECER_TECNICO, Status.RESOLVENDO_PENDENCIAS_PARECER);
        List<Status> statusRelato = Arrays.asList(Status.AGUARDANDO_RELATOR, Status.AGUARDANDO_PARECER_RELATOR, Status.RESOLVENDO_PENDENCIAS_RELATO);
        List<Status> statusHomologado = Arrays.asList(Status.APROVADO, Status.REPROVADO);
        List<Status> statusAguardandoHomologacao = Arrays.asList(Status.AGUARDANDO_HOMOLOGACAO);
        
        
        model.addAttribute("acoesParecer", acaoExtensaoRepository.findByStatusIn(statusParecer));
        model.addAttribute("acoesRelato", acaoExtensaoRepository.findByStatusIn(statusRelato));
        model.addAttribute("acoesHomologadas", acaoExtensaoRepository.findByStatusIn(statusHomologado));
        model.addAttribute("acoesAguardandoHomologacao", acaoExtensaoRepository.findByStatusIn(statusAguardandoHomologacao));
        
        return "direcao/index";
    }
}
