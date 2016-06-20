package ufc.quixada.npi.gpa.controller;

import static ufc.quixada.npi.gpa.util.Constants.ACOES_AGUARDANDO_HOMOLOGACAO;
import static ufc.quixada.npi.gpa.util.Constants.ACOES_AGUARDANDO_PARECER;
import static ufc.quixada.npi.gpa.util.Constants.ACOES_AGUARDANDO_PARECERISTA;
import static ufc.quixada.npi.gpa.util.Constants.ACOES_AGUARDANDO_RELATO;
import static ufc.quixada.npi.gpa.util.Constants.ACOES_AGUARDANDO_RELATOR;
import static ufc.quixada.npi.gpa.util.Constants.ACOES_DIRECAO_SIZE;
import static ufc.quixada.npi.gpa.util.Constants.ACOES_HOMOLOGADAS;
import static ufc.quixada.npi.gpa.util.Constants.ERRO;
import static ufc.quixada.npi.gpa.util.Constants.PAGINA_INICIAL_DIRECAO;
import static ufc.quixada.npi.gpa.util.Constants.REDIRECT_PAGINA_ACAO_EXTENSAO;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ufc.quixada.npi.gpa.exception.GpaExtensaoException;
import ufc.quixada.npi.gpa.model.AcaoExtensao;
import ufc.quixada.npi.gpa.model.AcaoExtensao.Status;
import ufc.quixada.npi.gpa.repository.AcaoExtensaoRepository;
import ufc.quixada.npi.gpa.service.DirecaoService;

@Controller
@RequestMapping("direcao")
@Transactional
public class DirecaoController {

	@Autowired
	private DirecaoService direcaoService;

	@Autowired
	private AcaoExtensaoRepository acaoExtensaoRepository;
	
	@ModelAttribute(ACOES_DIRECAO_SIZE)
	public Long acoesDirecaoSize(){
		return acaoExtensaoRepository.count();
	}

	@RequestMapping("/")
	public String listagem(Model model, Authentication authentication) {
		
		List<Status> statusAguardandoParecer = Arrays.asList(Status.AGUARDANDO_PARECER_TECNICO, Status.RESOLVENDO_PENDENCIAS_PARECER);
		List<Status> statusAguardandoParecerista = Arrays.asList(Status.AGUARDANDO_PARECERISTA, Status.RESOLVENDO_PENDENCIAS_PARECER);
		List<Status> statusAguardandoRelato = Arrays.asList(Status.AGUARDANDO_PARECER_RELATOR, Status.RESOLVENDO_PENDENCIAS_RELATO);
		List<Status> statusAguardandoRelator = Arrays.asList(Status.AGUARDANDO_RELATOR, Status.RESOLVENDO_PENDENCIAS_RELATO);
		List<Status> statusHomologado = Arrays.asList(Status.APROVADO, Status.REPROVADO);
		List<Status> statusAguardandoHomologacao = Arrays.asList(Status.AGUARDANDO_HOMOLOGACAO);

		model.addAttribute(ACOES_AGUARDANDO_PARECER, acaoExtensaoRepository.findByStatusIn(statusAguardandoParecer));
		model.addAttribute(ACOES_AGUARDANDO_PARECERISTA, acaoExtensaoRepository.findByStatusIn(statusAguardandoParecerista));
		model.addAttribute(ACOES_AGUARDANDO_RELATO, acaoExtensaoRepository.findByStatusIn(statusAguardandoRelato));
		model.addAttribute(ACOES_AGUARDANDO_RELATOR, acaoExtensaoRepository.findByStatusIn(statusAguardandoRelator));
		model.addAttribute(ACOES_AGUARDANDO_HOMOLOGACAO, acaoExtensaoRepository.findByStatusIn(statusAguardandoHomologacao));
		model.addAttribute(ACOES_HOMOLOGADAS, acaoExtensaoRepository.findByStatusIn(statusHomologado));

		return PAGINA_INICIAL_DIRECAO;
	}

	@RequestMapping(value = "/parecerista", method = RequestMethod.POST)
	public String atribuirParecerista(AcaoExtensao acaoExtensao, Model model) {
		try {
			direcaoService.atribuirParecerista(acaoExtensao);
		} catch (GpaExtensaoException e) {
			model.addAttribute(ERRO, e.getMessage());
		}
		return REDIRECT_PAGINA_ACAO_EXTENSAO + acaoExtensao.getId();
	}
	
	@RequestMapping(value = "/relator", method = RequestMethod.POST)
	public String atribuirRelator(AcaoExtensao acaoExtensao, Model model) {
		try {
			direcaoService.atribuirRelator(acaoExtensao);
		} catch (GpaExtensaoException e) {
			model.addAttribute(ERRO, e.getMessage());
		}
		return REDIRECT_PAGINA_ACAO_EXTENSAO + acaoExtensao.getId();
	}
}
