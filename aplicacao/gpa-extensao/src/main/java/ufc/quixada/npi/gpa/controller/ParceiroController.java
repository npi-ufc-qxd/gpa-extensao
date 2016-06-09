package ufc.quixada.npi.gpa.controller;

import static ufc.quixada.npi.gpa.util.Constants.MESSAGE_STATUS_RESPONSE;
import static ufc.quixada.npi.gpa.util.Constants.RESPONSE_DATA;
import static ufc.quixada.npi.gpa.util.Constants.MESSAGE_CADASTRO_SUCESSO;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ufc.quixada.npi.gpa.model.Parceiro;
import ufc.quixada.npi.gpa.model.ParceriaExterna;
import ufc.quixada.npi.gpa.service.ParceiroService;
@Controller
@RequestMapping("/parceiro")
public class ParceiroController {
	@Autowired
	private ParceiroService parceiroService;
	
	@RequestMapping(value="/novo/{id}", method=RequestMethod.POST)
	public @ResponseBody Map<String, Object> novoParceiro(@PathVariable("id") Integer id, @ModelAttribute @Valid Parceiro parceiro,
			BindingResult binding, ParceriaExterna parceriaExterna){
		Map<String, Object> map = new HashMap<String, Object>();
		if(binding.hasErrors()){
			map.put(MESSAGE_STATUS_RESPONSE, "ERROR");
			map.put(RESPONSE_DATA, binding.getFieldErrors());
			return map;
		}
		parceiroService.salvar(new Parceiro(parceiro.getNome(), parceiro.getTipo()));
		map.put(MESSAGE_STATUS_RESPONSE, "OK");
		map.put(RESPONSE_DATA, MESSAGE_CADASTRO_SUCESSO);
		return map;
	}
}
