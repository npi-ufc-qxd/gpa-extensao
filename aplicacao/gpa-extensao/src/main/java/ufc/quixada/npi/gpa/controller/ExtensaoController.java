package ufc.quixada.npi.gpa.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import ufc.quixada.npi.gpa.model.AcaoExtensao;
import ufc.quixada.npi.gpa.model.AcaoExtensao.Modalidade;



@Controller
public class ExtensaoController {
	

	@RequestMapping("/")
	public String index(Model model, AcaoExtensao acaoExtensao) {
		model.addAttribute("modalidades", Modalidade.values());
		return "index";
	}

	@RequestMapping("/paginaCadastro")
	public String index2(Model model, AcaoExtensao acaoExtensao) {
		model.addAttribute("modalidades", Modalidade.values());
		return "coordenacao/crud/cadastrarExtensao";
	}
}
