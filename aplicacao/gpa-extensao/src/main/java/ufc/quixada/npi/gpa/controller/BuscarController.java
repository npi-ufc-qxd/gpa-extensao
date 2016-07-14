package ufc.quixada.npi.gpa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ufc.quixada.npi.gpa.model.AcaoExtensao.Modalidade;
import ufc.quixada.npi.gpa.repository.AcaoExtensaoRepository;
import ufc.quixada.npi.gpa.repository.ServidorRepository;

@Controller
@RequestMapping("buscar")
@Transactional
public class BuscarController {
	
	@Autowired
	ServidorRepository servidorRespository;
	
	@Autowired
	AcaoExtensaoRepository acaoExtensaoRepository;

	@RequestMapping("/acao-extensao")
	public String buscarAcaoForm(Model model) {
		model.addAttribute("coordenadores", servidorRespository.findAll());
		model.addAttribute("acoes", acaoExtensaoRepository.findAll());
		model.addAttribute("modalidades", Modalidade.values());
		
		return "buscar/acao-extensao";
	}
	
	@RequestMapping(value="/acao-extensao", method = RequestMethod.POST)
	public String buscarAcao(@RequestParam("coordenador") Integer idCoordenador, @RequestParam("modalidade") Modalidade modalidade,
			@RequestParam("ano") String ano)  {
		return "buscar/acao-extensao";
	}
}
