package ufc.quixada.npi.gpa.controller;

import static ufc.quixada.npi.gpa.util.Constants.CONTEUDO_MESSAGE_EMITIR_SOLICITACAO_PENDENCIAS;
import static ufc.quixada.npi.gpa.util.Constants.CONTEUDO_MESSAGE_PARECERISTA_ADICIONADO;
import static ufc.quixada.npi.gpa.util.Constants.CONTEUDO_MESSAGE_PARECERISTA_AGUARDANDO_ERROR;
import static ufc.quixada.npi.gpa.util.Constants.CONTEUDO_MESSAGE_PARECERISTA_EQUIPE_ERROR;
import static ufc.quixada.npi.gpa.util.Constants.CONTEUDO_MESSAGE_PARECER_EMITIDO;
import static ufc.quixada.npi.gpa.util.Constants.CONTEUDO_MESSAGE_PARECER_EMITIDO_ERROR;
import static ufc.quixada.npi.gpa.util.Constants.CONTEUDO_MESSAGE_RELATOR_ADICIONADO;
import static ufc.quixada.npi.gpa.util.Constants.EXCEPTION_ATRIBUIR_PARECERISTA;
import static ufc.quixada.npi.gpa.util.Constants.EXCEPTION_PARECERISTA_DA_EQUIPE;
import static ufc.quixada.npi.gpa.util.Constants.REDIRECT_PAGINA_DETALHES_ACAO;
import static ufc.quixada.npi.gpa.util.Constants.STATUS_MESSAGE_ERROR;
import static ufc.quixada.npi.gpa.util.Constants.STATUS_MESSAGE_SUCCESS;
import static ufc.quixada.npi.gpa.util.Constants.TITULO_MESSAGE_EMITIR_SOLICITACAO_PENDENCIAS;
import static ufc.quixada.npi.gpa.util.Constants.TITULO_MESSAGE_PARECERISTA_ADICIONADO;
import static ufc.quixada.npi.gpa.util.Constants.TITULO_MESSAGE_PARECERISTA_ADICIONADO_ERROR;
import static ufc.quixada.npi.gpa.util.Constants.TITULO_MESSAGE_PARECER_EMITIDO;
import static ufc.quixada.npi.gpa.util.Constants.TITULO_MESSAGE_PARECER_EMITIDO_ERROR;
import static ufc.quixada.npi.gpa.util.Constants.TITULO_MESSAGE_RELATOR_ADICIONADO;
import static ufc.quixada.npi.gpa.util.Constants.TITULO_MESSAGE_RELATOR_ADICIONADO_ERROR;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ufc.quixada.npi.gpa.exception.GpaExtensaoException;
import ufc.quixada.npi.gpa.model.AcaoExtensao;
import ufc.quixada.npi.gpa.model.Pendencia;
import ufc.quixada.npi.gpa.service.ParecerService;

@Controller
public class ParecerController {

	@Autowired
	private ParecerService parecerService;

	@RequestMapping(value = "/parecerista", method = RequestMethod.POST)
	public String atribuirParecerista(AcaoExtensao acaoExtensao, @RequestParam("prazo") String prazo, RedirectAttributes redirect) throws ParseException {
		try {
			parecerService.atribuirParecerista(acaoExtensao, prazo);
			redirect.addFlashAttribute("status", STATUS_MESSAGE_SUCCESS);
			redirect.addFlashAttribute("titulo", TITULO_MESSAGE_PARECERISTA_ADICIONADO);
			redirect.addFlashAttribute("conteudo", CONTEUDO_MESSAGE_PARECERISTA_ADICIONADO);
		} catch (GpaExtensaoException e) {
			if(EXCEPTION_PARECERISTA_DA_EQUIPE.equals(e.getMessage())) {
				redirect.addFlashAttribute("status", STATUS_MESSAGE_SUCCESS);
				redirect.addFlashAttribute("titulo", TITULO_MESSAGE_PARECERISTA_ADICIONADO_ERROR);
				redirect.addFlashAttribute("conteudo", CONTEUDO_MESSAGE_PARECERISTA_EQUIPE_ERROR);
			} else if(EXCEPTION_ATRIBUIR_PARECERISTA.equals(e.getMessage())) {
				redirect.addFlashAttribute("status", STATUS_MESSAGE_SUCCESS);
				redirect.addFlashAttribute("titulo", TITULO_MESSAGE_PARECERISTA_ADICIONADO_ERROR);
				redirect.addFlashAttribute("conteudo", CONTEUDO_MESSAGE_PARECERISTA_AGUARDANDO_ERROR);
			}
		}
		return REDIRECT_PAGINA_DETALHES_ACAO + acaoExtensao.getId();
	}

	@RequestMapping(value = "/relator", method = RequestMethod.POST)
	public String atribuirRelator(AcaoExtensao acaoExtensao, @RequestParam("prazo") String prazo, RedirectAttributes redirect) throws ParseException {
		try {
			parecerService.atribuirRelator(acaoExtensao, prazo);
			redirect.addFlashAttribute("status", STATUS_MESSAGE_SUCCESS);
			redirect.addFlashAttribute("titulo", TITULO_MESSAGE_RELATOR_ADICIONADO);
			redirect.addFlashAttribute("conteudo", CONTEUDO_MESSAGE_RELATOR_ADICIONADO);
		} catch (GpaExtensaoException e) {
			if(EXCEPTION_PARECERISTA_DA_EQUIPE.equals(e.getMessage())) {
				redirect.addFlashAttribute("status", STATUS_MESSAGE_SUCCESS);
				redirect.addFlashAttribute("titulo", TITULO_MESSAGE_RELATOR_ADICIONADO_ERROR);
				redirect.addFlashAttribute("conteudo", CONTEUDO_MESSAGE_PARECERISTA_EQUIPE_ERROR);
			} else if(EXCEPTION_ATRIBUIR_PARECERISTA.equals(e.getMessage())) {
				redirect.addFlashAttribute("status", STATUS_MESSAGE_SUCCESS);
				redirect.addFlashAttribute("titulo", TITULO_MESSAGE_RELATOR_ADICIONADO_ERROR);
				redirect.addFlashAttribute("conteudo", CONTEUDO_MESSAGE_PARECERISTA_AGUARDANDO_ERROR);
			}
		}
		return REDIRECT_PAGINA_DETALHES_ACAO + acaoExtensao.getId();
	}
	

	@RequestMapping(value = "/acoes/{idAcao}/pendencias", method = RequestMethod.POST)
	public String solicitarResolucaoPendenciasParecer(@PathVariable Integer idAcao, Pendencia pendencia, 
			RedirectAttributes redirect) throws GpaExtensaoException {
		
		parecerService.solicitarResolucaoPendencias(idAcao, pendencia);
		redirect.addFlashAttribute("status", STATUS_MESSAGE_SUCCESS);
		redirect.addFlashAttribute("titulo", TITULO_MESSAGE_EMITIR_SOLICITACAO_PENDENCIAS);
		redirect.addFlashAttribute("conteudo", CONTEUDO_MESSAGE_EMITIR_SOLICITACAO_PENDENCIAS);
		
		return REDIRECT_PAGINA_DETALHES_ACAO + idAcao;
	}

	@RequestMapping(value = "/emitirParecerRelator", method = RequestMethod.POST)
	public String emitirParecerRelator(AcaoExtensao acaoExtensao, RedirectAttributes redirect) {
		try {
			parecerService.emitirParecer(acaoExtensao);
			redirect.addFlashAttribute("status", STATUS_MESSAGE_SUCCESS);
			redirect.addFlashAttribute("titulo", TITULO_MESSAGE_PARECER_EMITIDO);
			redirect.addFlashAttribute("conteudo", CONTEUDO_MESSAGE_PARECER_EMITIDO);
		} catch (GpaExtensaoException e) {
			redirect.addFlashAttribute("status", STATUS_MESSAGE_ERROR);
			redirect.addFlashAttribute("titulo", TITULO_MESSAGE_PARECER_EMITIDO_ERROR);
			redirect.addFlashAttribute("conteudo", CONTEUDO_MESSAGE_PARECER_EMITIDO_ERROR);

		}
		return REDIRECT_PAGINA_DETALHES_ACAO + acaoExtensao.getId();
	}

	@RequestMapping(value = "/emitirParecerTecnico", method = RequestMethod.POST)
	public String emitirParecerTecnico(AcaoExtensao acaoExtensao, RedirectAttributes redirect) {
		try {
			parecerService.emitirParecer(acaoExtensao);
			redirect.addFlashAttribute("status", STATUS_MESSAGE_SUCCESS);
			redirect.addFlashAttribute("titulo", TITULO_MESSAGE_PARECER_EMITIDO);
			redirect.addFlashAttribute("conteudo", CONTEUDO_MESSAGE_PARECER_EMITIDO);
		} catch (GpaExtensaoException e) {
			redirect.addFlashAttribute("status", STATUS_MESSAGE_ERROR);
			redirect.addFlashAttribute("titulo", TITULO_MESSAGE_PARECER_EMITIDO_ERROR);
			redirect.addFlashAttribute("conteudo", CONTEUDO_MESSAGE_PARECER_EMITIDO_ERROR);
		}
		return REDIRECT_PAGINA_DETALHES_ACAO + acaoExtensao.getId();
	}
	
	

}
