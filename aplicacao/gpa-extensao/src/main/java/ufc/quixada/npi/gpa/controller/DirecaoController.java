package ufc.quixada.npi.gpa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ufc.quixada.npi.gpa.service.DirecaoService;

@Controller
@RequestMapping("direcao")
public class DirecaoController {

	@Autowired
	private DirecaoService direcaoService;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String atribuirPareceristaForm() {
		return null;
	}

}
