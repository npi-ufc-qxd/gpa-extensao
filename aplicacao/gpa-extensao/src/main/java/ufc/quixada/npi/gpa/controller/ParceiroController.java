package ufc.quixada.npi.gpa.controller;

import static ufc.quixada.npi.gpa.util.Constants.PAGINA_CRIAR_PARCERIA_EXTERNA;
import static ufc.quixada.npi.gpa.util.Constants.REDIRECT_PAGINA_CRIAR_PARCERIA_EXTERNA;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ufc.quixada.npi.gpa.model.Parceiro;
import ufc.quixada.npi.gpa.service.ParceiroService;

@Controller
@RequestMapping("/parceiro")
public class ParceiroController {
	@Autowired
	private ParceiroService parceiroService;
	
	@RequestMapping(value="/novo/{id}", method=RequestMethod.POST)
	public String novoParceiro(@PathVariable("id") Integer id, @ModelAttribute Parceiro parceiro, BindingResult binding){
		if(binding.hasErrors()){
			return PAGINA_CRIAR_PARCERIA_EXTERNA;
		}
		parceiroService.salvar(new Parceiro(parceiro.getNome(), parceiro.getTipo()));
		return REDIRECT_PAGINA_CRIAR_PARCERIA_EXTERNA + id;
	}
}
