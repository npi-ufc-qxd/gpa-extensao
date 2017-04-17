package ufc.quixada.npi.gpa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import ufc.quixada.npi.gpa.model.Pessoa;
import ufc.quixada.npi.gpa.service.AcaoExtensaoService;
import ufc.quixada.npi.gpa.service.PessoaService;

@ControllerAdvice
public class AttributesController {
	
	private Pessoa pessoaLogada;
	
	@Autowired
	private PessoaService pessoaService;
	
	@Autowired
	private AcaoExtensaoService acaoService;
	
	@Autowired
	public void setPessoaLogada(Authentication authentication) {
		this.pessoaLogada = pessoaService.findByCpf(authentication.getName());
	}

	@ModelAttribute("pessoaLogada")
	public Pessoa pessoaLogada(Authentication authentication) {
		return this.pessoaLogada;
	}
	
	@ModelAttribute("qtdPendenciasParecerTecnico")
	public int qtdAcoesPendenciasParecerTecnico(Authentication authentication) {
		return acaoService.countAcoesPendenciasParecer(this.pessoaLogada);
	}
	
	@ModelAttribute("qtdPendenciasParecerRelator")
	public int qtdAcoesPendenciasParecerRelator(Authentication authentication) {
		return acaoService.countAcoesPendenciasRelato(this.pessoaLogada);
	}
	
	@ModelAttribute("qtdAcoesPareceristaRelatorHomologacao")
	public int qtdAcoesPareceristaRelator() {
		return acaoService.countAcoesAguardandoPareceristaRelator();
	}
	
	@ModelAttribute("qtdAcoesHomologacao")
	public int qtdAcoesHomologacao() {
		return acaoService.countAcoesAguardandoHomologacao();
	}
}
