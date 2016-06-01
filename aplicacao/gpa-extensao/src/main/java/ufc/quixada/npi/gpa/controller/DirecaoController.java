package ufc.quixada.npi.gpa.controller;

import static ufc.quixada.npi.gpa.util.Constants.LOAD_PARECERISTAS;
import static ufc.quixada.npi.gpa.util.Constants.PARECERISTAS;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ufc.quixada.npi.gpa.model.AcaoExtensao;
import ufc.quixada.npi.gpa.model.Pessoa;
import ufc.quixada.npi.gpa.service.DirecaoService;

@Controller
@RequestMapping("direcao")
public class DirecaoController {

	@Autowired
	private DirecaoService direcaoService;

	@RequestMapping(value = "/parecerista", method = RequestMethod.GET)
	public String atribuirPareceristaForm(AcaoExtensao acaoExtensao, Model model) {
		model.addAttribute(PARECERISTAS, direcaoService.getPossiveisPareceristas(acaoExtensao));
		return LOAD_PARECERISTAS;
	}

	@RequestMapping(value = "/parecerista", method = RequestMethod.POST)
	public String atribuirParecerista(AcaoExtensao acaoExtensao, Pessoa parecerista) {
		direcaoService.atribuirParecerista(acaoExtensao, parecerista);
		return null;
	}

}
