package ufc.quixada.npi.gpa.controller;

import static ufc.quixada.npi.gpa.util.Constants.PAGINA_CRIAR_PARCEIRO;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ufc.quixada.npi.gpa.model.Parceiro;
import ufc.quixada.npi.gpa.service.ParceiroService;

@Controller
@RequestMapping("/parceiro")
public class ParceiroController {
	@Autowired
	private ParceiroService parceiroService;
	@RequestMapping
	public String parceiroForm(Parceiro parceiro, Model model){
		model.addAttribute("parceiros",parceiroService.buscarTodos());
		return PAGINA_CRIAR_PARCEIRO;
	}
	@RequestMapping(value="/novo", method=RequestMethod.POST)
	public String novoParceiro(@Valid Parceiro parceiro, Model model, BindingResult binding){
		if(binding.hasErrors()){
			return PAGINA_CRIAR_PARCEIRO;
		}
		parceiroService.salvar(parceiro);
		model.addAttribute("parceiros",parceiroService.buscarTodos());
		return "redirect:/parceiro";
	}
}
