package ufc.quixada.npi.gpa.controller;

import static ufc.quixada.npi.gpa.util.Constants.ACOES_AGUARDANDO_HOMOLOGACAO;
import static ufc.quixada.npi.gpa.util.Constants.ACOES_AGUARDANDO_PARECER;
import static ufc.quixada.npi.gpa.util.Constants.ACOES_AGUARDANDO_PARECERISTA;
import static ufc.quixada.npi.gpa.util.Constants.ACOES_AGUARDANDO_RELATO;
import static ufc.quixada.npi.gpa.util.Constants.ACOES_AGUARDANDO_RELATOR;
import static ufc.quixada.npi.gpa.util.Constants.ACOES_COORDENADOR_SIZE;
import static ufc.quixada.npi.gpa.util.Constants.ACOES_DIRECAO_SIZE;
import static ufc.quixada.npi.gpa.util.Constants.ACOES_HOMOLOGADAS;
import static ufc.quixada.npi.gpa.util.Constants.PAGINA_INICIAL_DIRECAO;
import static ufc.quixada.npi.gpa.util.Constants.PESSOA_LOGADA;
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
import ufc.quixada.npi.gpa.repository.PessoaRepository;

@Controller
@RequestMapping("direcao")
@Transactional
@EnableGlobalAuthentication
public class DirecaoController {

	@Autowired
	private AcaoExtensaoRepository acaoExtensaoRepository;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@ModelAttribute(ACOES_DIRECAO_SIZE)
	public Integer acoesDirecaoSize(Authentication authentication){
		return acaoExtensaoRepository.countAcoesTramitacao(Status.NOVO);
	}
	
	@ModelAttribute(ACOES_COORDENADOR_SIZE)
	public Integer acoesCoordenadorSize(Authentication authentication){
		return acaoExtensaoRepository.countAcoesCoordenador(authentication.getName());
	}
	
	@ModelAttribute(PESSOA_LOGADA)
	public String pessoaLogada(Authentication authentication){
		return pessoaRepository.findByCpf(authentication.getName()).getNome();
	}

	@RequestMapping("/")
	public String listagem(Model model, Authentication authentication) {
		
		List<Status> statusAguardandoParecer = Arrays.asList(Status.AGUARDANDO_PARECER_TECNICO, Status.RESOLVENDO_PENDENCIAS_PARECER);
		List<Status> statusAguardandoParecerista = Arrays.asList(Status.AGUARDANDO_PARECERISTA);
		List<Status> statusAguardandoRelato = Arrays.asList(Status.AGUARDANDO_PARECER_RELATOR, Status.RESOLVENDO_PENDENCIAS_RELATO);
		List<Status> statusAguardandoRelator = Arrays.asList(Status.AGUARDANDO_RELATOR);
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
	
	@RequestMapping(value = "/homologar/{id}", method = RequestMethod.POST)
	public String homologar(@PathVariable("id") Integer id, @ModelAttribute("acaoextensao") AcaoExtensao acaoExtensao, Model model, Authentication authentication, RedirectAttributes redirectAttributes){
		AcaoExtensao acao = acaoExtensaoRepository.findOne(id);
		
		acao.setStatus(acaoExtensao.getStatus());
		acao.setDataDeHomologacao(acaoExtensao.getDataDeHomologacao());
		acao.setNumeroProcesso(acaoExtensao.getNumeroProcesso());
		acao.setObservacaoHomologacao(acaoExtensao.getObservacaoHomologacao());
		
		acaoExtensaoRepository.save(acao);
		
		redirectAttributes.addFlashAttribute("homologado", true);
		return REDIRECT_PAGINA_DETALHES_ACAO + id;
	}
}
