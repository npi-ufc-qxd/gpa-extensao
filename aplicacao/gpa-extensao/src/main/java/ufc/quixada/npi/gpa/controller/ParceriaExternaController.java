package ufc.quixada.npi.gpa.controller;

import static ufc.quixada.npi.gpa.util.Constants.CONTEUDO_MESSAGE_PARCERIA_ADICIONADO;
import static ufc.quixada.npi.gpa.util.Constants.CONTEUDO_MESSAGE_PARCERIA_PARCEIRO_ERROR;
import static ufc.quixada.npi.gpa.util.Constants.CONTEUDO_MESSAGE_PARCERIA_PERMISSAO_ERROR;
import static ufc.quixada.npi.gpa.util.Constants.CONTEUDO_MESSAGE_PARCERIA_STATUS_ERROR;
import static ufc.quixada.npi.gpa.util.Constants.ERRO;
import static ufc.quixada.npi.gpa.util.Constants.ERROR_PARCEIRO_JA_PARTICIPANTE;
import static ufc.quixada.npi.gpa.util.Constants.EXCEPTION_ADICAO_PARCERIA_NAO_PERMITIDA;
import static ufc.quixada.npi.gpa.util.Constants.FRAGMENTS_TABLE_PARCERIAS_EXTERNAS;
import static ufc.quixada.npi.gpa.util.Constants.MENSAGEM_PERMISSAO_NEGADA;
import static ufc.quixada.npi.gpa.util.Constants.STATUS_MESSAGE_ERROR;
import static ufc.quixada.npi.gpa.util.Constants.STATUS_MESSAGE_SUCCESS;
import static ufc.quixada.npi.gpa.util.Constants.TITULO_MESSAGE_PARCERIA_ADICIONADO;
import static ufc.quixada.npi.gpa.util.Constants.TITULO_MESSAGE_PARCERIA_ADICIONADO_ERROR;
import static ufc.quixada.npi.gpa.util.RedirectConstants.R_ACAO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ufc.quixada.npi.gpa.exception.GpaExtensaoException;
import ufc.quixada.npi.gpa.model.AcaoExtensao;
import ufc.quixada.npi.gpa.model.Parceiro;
import ufc.quixada.npi.gpa.model.ParceriaExterna;
import ufc.quixada.npi.gpa.model.Pessoa;
import ufc.quixada.npi.gpa.repository.AcaoExtensaoRepository;
import ufc.quixada.npi.gpa.repository.ParceiroRepository;
import ufc.quixada.npi.gpa.repository.ParceriaExternaRepository;
import ufc.quixada.npi.gpa.service.ParceriaExternaService;
import ufc.quixada.npi.gpa.service.PessoaService;

@Controller
@Transactional
@RequestMapping(value = "/parceria")
public class ParceriaExternaController {

	@Autowired
	private ParceriaExternaRepository parceriaExternaRepository;
	@Autowired
	private AcaoExtensaoRepository acaoExtensaoRepository;
	@Autowired
	private ParceiroRepository parceiroRepository;

	@Autowired
	private PessoaService pessoaService;

	@Autowired
	private ParceriaExternaService parceriaExternaService;

	@RequestMapping(value = "/buscarParceriasExternas/{idAcao}", method = RequestMethod.GET)
	public String buscarParceriasExternas(@PathVariable("idAcao") Integer idAcao, Model model) {
		AcaoExtensao acao = acaoExtensaoRepository.findOne(idAcao);
		model.addAttribute("parceriasExternas", parceriaExternaRepository.findByAcaoExtensao(acao));
		model.addAttribute("cpfCoordenador", acao.getCoordenador().getCpf());
		return FRAGMENTS_TABLE_PARCERIAS_EXTERNAS;
	}

	@PostMapping(value = "/excluir/{id}/{acao}")
	public  @ResponseBody Map<String, Object> deletarParceriaExterna(@PathVariable("id") ParceriaExterna parceria,
			@PathVariable("acao") AcaoExtensao acao, RedirectAttributes redirectAttribute,
			Authentication authentication) {
		Pessoa coordenador = pessoaService.buscarPorCpf(authentication.getName());
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			parceriaExternaService.excluirParceriaExterna(coordenador, parceria);
		} catch (GpaExtensaoException e) {
			map.put(ERRO, e.getMessage());
		}
		return map;
	}

	@RequestMapping(value = "/salvar/{idAcao}", method = RequestMethod.POST)
	public String novaParceriaExterna(@PathVariable("idAcao") AcaoExtensao idAcao,

			@ModelAttribute @Valid ParceriaExterna parceria,
			@RequestParam(required = false) Parceiro parceiro, RedirectAttributes redirectAttribute, Authentication authentication) {


		Pessoa coordenador = pessoaService.buscarPorCpf(authentication.getName());
		try {
			parceriaExternaService.adicionarParceriaExterna(coordenador, parceria, idAcao, parceiro);
			redirectAttribute.addFlashAttribute("status", STATUS_MESSAGE_SUCCESS);
			redirectAttribute.addFlashAttribute("titulo", TITULO_MESSAGE_PARCERIA_ADICIONADO);
			redirectAttribute.addFlashAttribute("conteudo", CONTEUDO_MESSAGE_PARCERIA_ADICIONADO);
		} catch (GpaExtensaoException e) {
			if(MENSAGEM_PERMISSAO_NEGADA.equals(e.getMessage())) {
				redirectAttribute.addFlashAttribute("status", STATUS_MESSAGE_ERROR);
				redirectAttribute.addFlashAttribute("titulo", TITULO_MESSAGE_PARCERIA_ADICIONADO_ERROR);
				redirectAttribute.addFlashAttribute("conteudo", CONTEUDO_MESSAGE_PARCERIA_PERMISSAO_ERROR);
			} else if(EXCEPTION_ADICAO_PARCERIA_NAO_PERMITIDA.equals(e.getMessage())) {
				redirectAttribute.addFlashAttribute("status", STATUS_MESSAGE_ERROR);
				redirectAttribute.addFlashAttribute("titulo", TITULO_MESSAGE_PARCERIA_ADICIONADO_ERROR);
				redirectAttribute.addFlashAttribute("conteudo", CONTEUDO_MESSAGE_PARCERIA_STATUS_ERROR);
			} else if(ERROR_PARCEIRO_JA_PARTICIPANTE.equals(e.getMessage())) {
				redirectAttribute.addFlashAttribute("status", STATUS_MESSAGE_ERROR);
				redirectAttribute.addFlashAttribute("titulo", TITULO_MESSAGE_PARCERIA_ADICIONADO_ERROR);
				redirectAttribute.addFlashAttribute("conteudo", CONTEUDO_MESSAGE_PARCERIA_PARCEIRO_ERROR);
			}
		}

		return R_ACAO + idAcao.getId();
	}

	@RequestMapping(value = "/buscarParceiros")
	public @ResponseBody List<Parceiro> buscarParceiros() {
		return parceiroRepository.findAll();
	}
}
