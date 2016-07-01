package ufc.quixada.npi.gpa.controller;

import static ufc.quixada.npi.gpa.util.Constants.ERRO;
import static ufc.quixada.npi.gpa.util.Constants.REDIRECT_PAGINA_DETALHES_ACAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import ufc.quixada.npi.gpa.exception.GpaExtensaoException;
import ufc.quixada.npi.gpa.model.AcaoExtensao;
import ufc.quixada.npi.gpa.model.Pendencia;
import ufc.quixada.npi.gpa.service.ParecerService;

@Controller
public class ParecerController {
	
	@Autowired
	private ParecerService parecerService;
	
	@RequestMapping(value = "/parecerista", method = RequestMethod.POST)
	public String atribuirParecerista(AcaoExtensao acaoExtensao, Model model) {
		try {
			parecerService.atribuirParecerista(acaoExtensao);
		} catch (GpaExtensaoException e) {
			model.addAttribute(ERRO, e.getMessage());
		}
		return REDIRECT_PAGINA_DETALHES_ACAO + acaoExtensao.getId();
	}
	
	@RequestMapping(value = "/relator", method = RequestMethod.POST)
	public String atribuirRelator(AcaoExtensao acaoExtensao, Model model) {
		try {
			parecerService.atribuirRelator(acaoExtensao);
		} catch (GpaExtensaoException e) {
			model.addAttribute(ERRO, e.getMessage());
		}
		return REDIRECT_PAGINA_DETALHES_ACAO + acaoExtensao.getId();
	}
	
	@RequestMapping(value = "/acoes/{idAcao}/pendencias", method = RequestMethod.POST)
	public String solicitarResolucaoPendenciasParecer (@PathVariable Integer idAcao, Pendencia pendencia) {
		parecerService.solicitarResolucaoPendencias(idAcao, pendencia);
		
		return REDIRECT_PAGINA_DETALHES_ACAO + idAcao;
	}

	@RequestMapping(value="/emitirParecerRelator", method=RequestMethod.POST)
	public String emitirParecerRelator(@RequestParam("arquivo-relator") MultipartFile arquivo, AcaoExtensao acaoExtensao, Model model){
		try {
			parecerService.emitirParecer(acaoExtensao, arquivo);
		} catch (GpaExtensaoException e) {
			model.addAttribute(ERRO, e.getMessage());
		}
		return REDIRECT_PAGINA_DETALHES_ACAO + acaoExtensao.getId();
	}
	
	@RequestMapping(value="/emitirParecerTecnico", method=RequestMethod.POST)
	public String emitirParecerTecnico(@RequestParam("arquivo-parecerista") MultipartFile arquivo, AcaoExtensao acaoExtensao, Model model){
		try {
			parecerService.emitirParecer(acaoExtensao, arquivo);
		} catch (GpaExtensaoException e) {
			model.addAttribute(ERRO, e.getMessage());
		}
		return REDIRECT_PAGINA_DETALHES_ACAO + acaoExtensao.getId();
	}
	
}
