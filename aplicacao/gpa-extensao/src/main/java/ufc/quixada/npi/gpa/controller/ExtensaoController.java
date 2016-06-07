package ufc.quixada.npi.gpa.controller;

import static ufc.quixada.npi.gpa.util.Constants.PAGINA_CRIAR_PARCERIA_EXTERNA;
import static ufc.quixada.npi.gpa.util.Constants.REDIRECT_PAGINA_CRIAR_PARCERIA_EXTERNA;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ufc.quixada.npi.gpa.model.AcaoExtensao;
import ufc.quixada.npi.gpa.model.Parceiro;
import ufc.quixada.npi.gpa.model.ParceriaExterna;
import ufc.quixada.npi.gpa.service.AcaoExtensaoService;
import ufc.quixada.npi.gpa.service.ParceiroService;
@Controller
public class ExtensaoController {
	
	@Autowired
	private ParceiroService parceiroService;
	@Autowired
	private AcaoExtensaoService acaoExtensaoService;
	@RequestMapping("/")
	public String index() {
		return "index";
	}
	
	@RequestMapping(value="/novaParceria/{id}",method=RequestMethod.GET)
	public String novaParceriaExternaForm(@PathVariable("id") Integer id, Model model, ParceriaExterna parceriaExterna, Parceiro parceiro){
		model.addAttribute("acaoExtensaoId", id);
		model.addAttribute("parceiros",parceiroService.buscarTodos());
		return PAGINA_CRIAR_PARCERIA_EXTERNA;
	}
	@RequestMapping(value="/salvarParceria/{id}", method=RequestMethod.POST)
	public String novaParceriaExterna(@PathVariable("id") Integer id, @ModelAttribute ParceriaExterna parceria,
			Model model, Authentication auth, BindingResult binding){
		if(binding.hasErrors()){
			return PAGINA_CRIAR_PARCERIA_EXTERNA;
		}
		AcaoExtensao acaoExtensao = acaoExtensaoService.buscarPorId(id);
		parceria.setAcaoExtensao(acaoExtensao);
		acaoExtensao.addParceriaExterna(parceria);
		acaoExtensaoService.salvar(acaoExtensao);
		return REDIRECT_PAGINA_CRIAR_PARCERIA_EXTERNA + id;
	}
}
