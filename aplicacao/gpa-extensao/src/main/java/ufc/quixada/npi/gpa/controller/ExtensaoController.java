package ufc.quixada.npi.gpa.controller;

import static ufc.quixada.npi.gpa.util.Constants.PAGINA_INICIAL;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ExtensaoController {

	@RequestMapping("/")
	public String index() {
		return PAGINA_INICIAL;
	}

}
