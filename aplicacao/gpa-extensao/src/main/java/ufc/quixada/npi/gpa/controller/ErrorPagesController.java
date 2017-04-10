package ufc.quixada.npi.gpa.controller;

import static ufc.quixada.npi.gpa.util.Constants.PAGINA_ERRO_403;
import static ufc.quixada.npi.gpa.util.Constants.PAGINA_ERRO_404;
import static ufc.quixada.npi.gpa.util.Constants.PAGINA_ERRO_500;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorPagesController {

	@RequestMapping("/404")
	public String notFound() {
		return PAGINA_ERRO_404;
	}

	@RequestMapping("/403")
	public String forbidden() {
		return PAGINA_ERRO_403;
	}

	@RequestMapping("/500")
	public String internalServerError() {
		return PAGINA_ERRO_500;
	}

}
