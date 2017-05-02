package ufc.quixada.npi.gpa.controller;

import static ufc.quixada.npi.gpa.util.Constants.PAGINA_INICIAL_DIRECAO;
import static ufc.quixada.npi.gpa.util.Constants.REDIRECT_PAGINA_DETALHES_ACAO;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ufc.quixada.npi.gpa.model.AcaoExtensao;
import ufc.quixada.npi.gpa.model.AcaoExtensao.Status;
import ufc.quixada.npi.gpa.repository.AcaoExtensaoRepository;
import ufc.quixada.npi.gpa.service.DirecaoService;

@Controller
@RequestMapping("direcao")
@Transactional
@EnableGlobalAuthentication
public class DirecaoController {

	@Autowired
	private DirecaoService direcaoService;

	@Autowired
	private AcaoExtensaoRepository acaoExtensaoRepository;

	@RequestMapping("/")
	public String listagem(Model model, Authentication authentication) {

		List<Status> statusAguardandoParecer = Arrays.asList(Status.AGUARDANDO_PARECER_TECNICO,
				Status.RESOLVENDO_PENDENCIAS_PARECER);
		List<Status> statusAguardandoParecerista = Arrays.asList(Status.AGUARDANDO_PARECERISTA);
		List<Status> statusAguardandoRelato = Arrays.asList(Status.AGUARDANDO_PARECER_RELATOR,
				Status.RESOLVENDO_PENDENCIAS_RELATO);
		List<Status> statusAguardandoRelator = Arrays.asList(Status.AGUARDANDO_RELATOR);
		List<Status> statusAguardandoHomologacao = Arrays.asList(Status.AGUARDANDO_HOMOLOGACAO);

		model.addAttribute("acoesAguardandoParecer", acaoExtensaoRepository.findByStatusIn(statusAguardandoParecer));
		model.addAttribute("acoesAguardandoParecerista",
				acaoExtensaoRepository.findByStatusIn(statusAguardandoParecerista));
		model.addAttribute("acoesAguardandoRelato", acaoExtensaoRepository.findByStatusIn(statusAguardandoRelato));
		model.addAttribute("acoesAguardandoRelator", acaoExtensaoRepository.findByStatusIn(statusAguardandoRelator));
		model.addAttribute("acoesAguardandoHomologacao",
				acaoExtensaoRepository.findByStatusIn(statusAguardandoHomologacao));

		return PAGINA_INICIAL_DIRECAO;
	}

	@RequestMapping(value = "/homologar/{id}", method = RequestMethod.POST)
	public String homologar(@PathVariable("id") Integer id, @ModelAttribute("acaoextensao") AcaoExtensao acaoExtensao,
			Model model, Authentication authentication, RedirectAttributes redirectAttributes) {
		direcaoService.homologarAcao(id, acaoExtensao);

		redirectAttributes.addFlashAttribute("homologado", true);
		return REDIRECT_PAGINA_DETALHES_ACAO + id;
}

}
