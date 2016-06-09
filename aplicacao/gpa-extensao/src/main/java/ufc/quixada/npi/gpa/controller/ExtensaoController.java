package ufc.quixada.npi.gpa.controller;

import static ufc.quixada.npi.gpa.util.Constants.ACAO_EXTENSAO_ID;
import static ufc.quixada.npi.gpa.util.Constants.MESSAGE_CADASTRO_SUCESSO;
import static ufc.quixada.npi.gpa.util.Constants.MESSAGE_STATUS_RESPONSE;
import static ufc.quixada.npi.gpa.util.Constants.PAGINA_CRIAR_PARCERIA_EXTERNA;
import static ufc.quixada.npi.gpa.util.Constants.PARCEIROS;
import static ufc.quixada.npi.gpa.util.Constants.REDIRECT_PAGINA_CRIAR_PARCERIA_EXTERNA;
import static ufc.quixada.npi.gpa.util.Constants.RESPONSE_DATA;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
	public String novaParceriaExternaForm(@PathVariable("id") Integer id, Model model){
		model.addAttribute(ACAO_EXTENSAO_ID, id);
		model.addAttribute("parceiro",new Parceiro());
		model.addAttribute("parceriaExterna",new ParceriaExterna());
		model.addAttribute(PARCEIROS,parceiroService.buscarTodos());
		return PAGINA_CRIAR_PARCERIA_EXTERNA;
	}

	@RequestMapping(value="/salvarParceria/{id}", method=RequestMethod.POST)
	public @ResponseBody Map<String, Object> novaParceriaExterna(@PathVariable("id") Integer id, @ModelAttribute @Valid ParceriaExterna parceria,
			Model model, Authentication auth, BindingResult binding){
		Map<String, Object> map = new HashMap<String, Object>();
		if(binding.hasErrors()){
			map.put(MESSAGE_STATUS_RESPONSE, "ERROR");
			map.put(RESPONSE_DATA, binding.getFieldErrors());
			return map;
		}
		AcaoExtensao acaoExtensao = acaoExtensaoService.buscarPorId(id);
		parceria.setAcaoExtensao(acaoExtensao);
		acaoExtensao.addParceriaExterna(parceria);
		acaoExtensaoService.salvar(acaoExtensao);
		map.put(MESSAGE_STATUS_RESPONSE, "OK");
		map.put(RESPONSE_DATA, MESSAGE_CADASTRO_SUCESSO);
		return map;
	}
}
