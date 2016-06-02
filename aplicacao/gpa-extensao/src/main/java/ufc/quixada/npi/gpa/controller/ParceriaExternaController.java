package ufc.quixada.npi.gpa.controller;

import static ufc.quixada.npi.gpa.util.Constants.PAGINA_CRIAR_PARCERIA_EXTERNA;
import static ufc.quixada.npi.gpa.util.Constants.REDIRECT_PAGINA_CRIAR_PARCERIA_EXTERNA;

import javax.validation.Valid;

import org.apache.tomcat.util.net.jsse.openssl.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ufc.quixada.npi.gpa.model.ParceriaExterna;
import ufc.quixada.npi.gpa.service.ParceiroService;
import ufc.quixada.npi.gpa.service.ParceriaExternaService;
@Controller
@RequestMapping("/parceriaExterna")
public class ParceriaExternaController {
	@Autowired
	private ParceriaExternaService parceriaExternaService;
	@Autowired
	private ParceiroService parceiroService;
	@RequestMapping(method=RequestMethod.GET)
	public String parceriaExternaForm(ParceriaExterna parceriaExterna, Model model){
		model.addAttribute("parceiros",parceiroService.buscarTodos());
		return PAGINA_CRIAR_PARCERIA_EXTERNA;
	}
	@RequestMapping(value="/novo",method=RequestMethod.POST)
	public String novaParceriaExterna(@Valid ParceriaExterna parceriaExterna, Authentication authentication,
			BindingResult binding,Model model){
		if(binding.hasErrors()){
			return PAGINA_CRIAR_PARCERIA_EXTERNA;
		}
		parceriaExternaService.salvar(parceriaExterna);
		return REDIRECT_PAGINA_CRIAR_PARCERIA_EXTERNA;
	}
}
