package ufc.quixada.npi.gpa.controller;

import static ufc.quixada.npi.gpa.util.Constants.PESSOA_LOGADA;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import ufc.quixada.npi.gpa.repository.PessoaRepository;

@ControllerAdvice
public class AttributesController {

	@Autowired
	private PessoaRepository pessoaRepository;

	@ModelAttribute(PESSOA_LOGADA)
	public String pessoaLogada(Authentication authentication) {
		return pessoaRepository.findByCpf(authentication.getName()).getNome();
	}

}
