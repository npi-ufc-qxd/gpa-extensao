package ufc.quixada.npi.gpa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import ufc.quixada.npi.gpa.model.Pessoa;
import ufc.quixada.npi.gpa.repository.PessoaRepository;

@ControllerAdvice
public class AttributesController {

	@Autowired
	private PessoaRepository pessoaRepository;

	//@ModelAttribute("pessoaLogada")
	public Pessoa pessoaLogada(Authentication authentication) {
		return pessoaRepository.findByCpf(authentication.getName());
	}

}
