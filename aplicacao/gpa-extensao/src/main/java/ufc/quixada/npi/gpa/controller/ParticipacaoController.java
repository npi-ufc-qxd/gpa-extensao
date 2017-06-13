package ufc.quixada.npi.gpa.controller;

import static ufc.quixada.npi.gpa.util.Constants.ERRO;
import static ufc.quixada.npi.gpa.util.Constants.FRAGMENTS_TABLE_PARTICIPACOES;
import static ufc.quixada.npi.gpa.util.Constants.MESSAGE_CADASTRO_SUCESSO;
import static ufc.quixada.npi.gpa.util.Constants.MESSAGE_STATUS_RESPONSE;
import static ufc.quixada.npi.gpa.util.Constants.RESPONSE_DATA;
import static ufc.quixada.npi.gpa.util.Constants.STATUS_MESSAGE_SUCCESS;
import static ufc.quixada.npi.gpa.util.Constants.TITULO_MESSAGE_PARTICIPACAO_ADICIONADA;
import static ufc.quixada.npi.gpa.util.Constants.CONTEUDO_MESSAGE_PARTICIPACAO_ADICIONADA;
import static ufc.quixada.npi.gpa.util.Constants.TITULO_MESSAGE_PARTICIPACAO_ALTERADA;
import static ufc.quixada.npi.gpa.util.Constants.CONTEUDO_MESSAGE_PARTICIPACAO_ALTERADA;
import static ufc.quixada.npi.gpa.util.Constants.STATUS_MESSAGE_ERROR;
import static ufc.quixada.npi.gpa.util.Constants.TITULO_MESSAGE_PARTICIPACAO_ADICIONADA_ERROR;
import static ufc.quixada.npi.gpa.util.Constants.TITULO_MESSAGE_PARTICIPACAO_ALTERADA_ERROR;
import static ufc.quixada.npi.gpa.util.Constants.CONTEUDO_MESSAGE_PARTICIPACAO_DATA_ERROR;
import static ufc.quixada.npi.gpa.util.Constants.CONTEUDO_MESSAGE_PARTICIPACAO_PERMISSAO_ERROR;
import static ufc.quixada.npi.gpa.util.Constants.CONTEUDO_MESSAGE_PARTICIPACAO_PESSOA_ERROR;
import static ufc.quixada.npi.gpa.util.Constants.CONTEUDO_MESSAGE_PARTICIPACAO_QTD_HORAS_ERROR;
import static ufc.quixada.npi.gpa.util.Constants.CONTEUDO_MESSAGE_PARTICIPACAO_STATUS_ERROR;
import static ufc.quixada.npi.gpa.util.Constants.CONTEUDO_MESSAGE_PARTICIPACAO_VALOR_ERROR;
import static ufc.quixada.npi.gpa.util.Constants.CONTEUDO_MESSAGE_PARTICIPAÇÃO_TEMPO_ERROR;
import static ufc.quixada.npi.gpa.util.Constants.ERROR_ADICIONAR_PARTICIPANTE_NAO_PERMITIDO;
import static ufc.quixada.npi.gpa.util.Constants.ERROR_PARTICIPACAO_ACAO_ENCERRADA;
import static ufc.quixada.npi.gpa.util.Constants.EXCEPTION_DATA_INVALIDA;
import static ufc.quixada.npi.gpa.util.Constants.EXCEPTION_STATUS_ACAO_NAO_PERMITE_ALTERACAO_TEMPO_PARTICIPACAO;
import static ufc.quixada.npi.gpa.util.Constants.MENSAGEM_PERMISSAO_NEGADA;
import static ufc.quixada.npi.gpa.util.Constants.ERROR_PESSOA_JA_PARTICIPANTE;
import static ufc.quixada.npi.gpa.util.Constants.ERROR_QTD_HORAS_NAO_PERMITIDA;
import static ufc.quixada.npi.gpa.util.Constants.VALOR_INVALIDO;
import static ufc.quixada.npi.gpa.util.Constants.CONTEUDO_MESSAGE_PARTICIPAÇÃO_ACAO_ENCERRADA_ERROR;
import static ufc.quixada.npi.gpa.util.RedirectConstants.R_ACAO;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.MalformedURLException;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.format.annotation.DateTimeFormat;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.itextpdf.text.DocumentException;

import ufc.quixada.npi.gpa.exception.GpaExtensaoException;
import ufc.quixada.npi.gpa.model.AcaoExtensao;
import ufc.quixada.npi.gpa.model.Participacao;
import ufc.quixada.npi.gpa.model.Pessoa;
import ufc.quixada.npi.gpa.model.Servidor;
import ufc.quixada.npi.gpa.model.Servidor.Funcao;
import ufc.quixada.npi.gpa.repository.AcaoExtensaoRepository;
import ufc.quixada.npi.gpa.repository.ParticipacaoRepository;
import ufc.quixada.npi.gpa.service.ParticipacaoService;
import ufc.quixada.npi.gpa.service.PessoaService;
import ufc.quixada.npi.gpa.service.ServidorService;
import ufc.quixada.npi.gpa.validator.ParticipacaoValidator;

@Controller
@Transactional
@RequestMapping("/participacao")
public class ParticipacaoController {

	@Autowired
	private AcaoExtensaoRepository acaoExtensaoRepository;

	@Autowired
	private ParticipacaoRepository participacaoRepository;

	@Autowired
	private ParticipacaoValidator participacaoValidator;

	@Autowired
	private ServidorService servidorService;

	@Autowired
	private PessoaService pessoaService;

	@Autowired
	private ParticipacaoService participacaoService;

	/**
	 * Adiciona um novo participante a equipe de trabalho
	 */

	@PostMapping("/adicionar-participacao/{acao}")
	public String adicionarParticipante(@PathVariable("acao") AcaoExtensao acaoExtensao, Participacao participacao,
			Authentication authentication, RedirectAttributes redirectAttribute) {

		Pessoa coordenador = pessoaService.buscarPorCpf(authentication.getName());
		try {
			participacaoService.adicionarParticipanteEquipeTrabalho(acaoExtensao, participacao, coordenador);
			redirectAttribute.addFlashAttribute("status", STATUS_MESSAGE_SUCCESS);
			redirectAttribute.addFlashAttribute("titulo", TITULO_MESSAGE_PARTICIPACAO_ADICIONADA);
			redirectAttribute.addFlashAttribute("conteudo", CONTEUDO_MESSAGE_PARTICIPACAO_ADICIONADA);
		} catch (GpaExtensaoException e) {
			if(ERROR_ADICIONAR_PARTICIPANTE_NAO_PERMITIDO.equals(e.getMessage())) {
				redirectAttribute.addFlashAttribute("status", STATUS_MESSAGE_ERROR);
				redirectAttribute.addFlashAttribute("titulo", TITULO_MESSAGE_PARTICIPACAO_ADICIONADA_ERROR);
				redirectAttribute.addFlashAttribute("conteudo", CONTEUDO_MESSAGE_PARTICIPACAO_STATUS_ERROR);
			} else if(EXCEPTION_DATA_INVALIDA.equals(e.getMessage())) {
				redirectAttribute.addFlashAttribute("status", STATUS_MESSAGE_ERROR);
				redirectAttribute.addFlashAttribute("titulo", TITULO_MESSAGE_PARTICIPACAO_ADICIONADA_ERROR);
				redirectAttribute.addFlashAttribute("conteudo", CONTEUDO_MESSAGE_PARTICIPACAO_DATA_ERROR);
			} else if(MENSAGEM_PERMISSAO_NEGADA.equals(e.getMessage())) {
				redirectAttribute.addFlashAttribute("status", STATUS_MESSAGE_ERROR);
				redirectAttribute.addFlashAttribute("titulo", TITULO_MESSAGE_PARTICIPACAO_ADICIONADA_ERROR);
				redirectAttribute.addFlashAttribute("conteudo", CONTEUDO_MESSAGE_PARTICIPACAO_PERMISSAO_ERROR);
			} else if(VALOR_INVALIDO.equals(e.getMessage())) {
				redirectAttribute.addFlashAttribute("status", STATUS_MESSAGE_ERROR);
				redirectAttribute.addFlashAttribute("titulo", TITULO_MESSAGE_PARTICIPACAO_ADICIONADA_ERROR);
				redirectAttribute.addFlashAttribute("conteudo", CONTEUDO_MESSAGE_PARTICIPACAO_VALOR_ERROR);
			} else if(ERROR_QTD_HORAS_NAO_PERMITIDA.equals(e.getMessage())) {
				redirectAttribute.addFlashAttribute("status", STATUS_MESSAGE_ERROR);
				redirectAttribute.addFlashAttribute("titulo", TITULO_MESSAGE_PARTICIPACAO_ADICIONADA_ERROR);
				redirectAttribute.addFlashAttribute("conteudo", CONTEUDO_MESSAGE_PARTICIPACAO_QTD_HORAS_ERROR);
			} else if(ERROR_PESSOA_JA_PARTICIPANTE.equals(e.getMessage())) {
				redirectAttribute.addFlashAttribute("status", STATUS_MESSAGE_ERROR);
				redirectAttribute.addFlashAttribute("titulo", TITULO_MESSAGE_PARTICIPACAO_ADICIONADA_ERROR);
				redirectAttribute.addFlashAttribute("conteudo", CONTEUDO_MESSAGE_PARTICIPACAO_PESSOA_ERROR);
			} else if(ERROR_PARTICIPACAO_ACAO_ENCERRADA.equals(e.getMessage())) {
				redirectAttribute.addFlashAttribute("status", STATUS_MESSAGE_ERROR);
				redirectAttribute.addFlashAttribute("titulo", TITULO_MESSAGE_PARTICIPACAO_ADICIONADA_ERROR);
				redirectAttribute.addFlashAttribute("conteudo", CONTEUDO_MESSAGE_PARTICIPAÇÃO_ACAO_ENCERRADA_ERROR);
			}
		}

		return R_ACAO + acaoExtensao.getId();

	}

	@RequestMapping(value = "/cadastrar/{idAcao}", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> adicionarParticipacao(
			@Valid @ModelAttribute("participacao") Participacao participacao, @PathVariable("idAcao") Integer idAcao,
			BindingResult result, Model model, RedirectAttributes redirectAttributes, Authentication authentication) {

		AcaoExtensao acao = acaoExtensaoRepository.findOne(idAcao);

		participacao.setAcaoExtensao(acao);
		participacaoValidator.validate(participacao, result);

		Map<String, Object> map = new HashMap<String, Object>();
		if (result.hasErrors()) {
			map.put(MESSAGE_STATUS_RESPONSE, "ERROR");
			map.put(RESPONSE_DATA, result.getFieldErrors());
			return map;
		}

		if (participacao.getParticipante() != null
				&& participacao.getParticipante().getId() == acao.getCoordenador().getId()) {
			participacao.setCoordenador(true);
		}

		participacao.setDataInicio(acao.getInicio());
		participacao.setDataTermino(acao.getTermino());

		participacaoRepository.save(participacao);

		map.put(MESSAGE_STATUS_RESPONSE, "OK");
		map.put(RESPONSE_DATA, MESSAGE_CADASTRO_SUCESSO);
		return map;
	}

	@PostMapping(value = "/excluir/{participacao}/{acao}")
	public @ResponseBody Map<String, Object> deleteParticipacao(@PathVariable("participacao") Participacao participacao,
			@PathVariable("acao") AcaoExtensao acaoExtensao,
			Authentication authentication) {

		Pessoa coordenador = pessoaService.buscarPorCpf(authentication.getName());
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			participacaoService.excluirParticipanteEquipeTrabalho(acaoExtensao, participacao, coordenador);
		} catch (GpaExtensaoException e) {
			map.put(ERRO, e.getMessage());
		}	
		return map;
	}

	@PostMapping("/alterar/{participacao}/{acao}")
	public String alterarParticipacao(@PathVariable("participacao") Integer participacao,
			@PathVariable("acao") AcaoExtensao acaoExtensao, RedirectAttributes redirectAttribute,
			Authentication authentication,
			@RequestParam("inicio") @DateTimeFormat(pattern = "dd/MM/yyyy") Date dataInicio,
			@RequestParam("termino") @DateTimeFormat(pattern = "dd/MM/yyyy") Date dataTermino) {

		Participacao old = participacaoRepository.findOne(participacao);
		Pessoa coordenador = pessoaService.buscarPorCpf(authentication.getName());

		try {
			participacaoService.alterarDataParticipacao(acaoExtensao, old, coordenador, dataInicio, dataTermino);
			redirectAttribute.addFlashAttribute("status", STATUS_MESSAGE_SUCCESS);
			redirectAttribute.addFlashAttribute("titulo", TITULO_MESSAGE_PARTICIPACAO_ALTERADA);
			redirectAttribute.addFlashAttribute("conteudo", CONTEUDO_MESSAGE_PARTICIPACAO_ALTERADA);
		} catch (GpaExtensaoException e) {
			if(MENSAGEM_PERMISSAO_NEGADA.equals(e.getMessage())) {
				redirectAttribute.addFlashAttribute("status", STATUS_MESSAGE_ERROR);
				redirectAttribute.addFlashAttribute("titulo", TITULO_MESSAGE_PARTICIPACAO_ALTERADA_ERROR);
				redirectAttribute.addFlashAttribute("conteudo", CONTEUDO_MESSAGE_PARTICIPACAO_PERMISSAO_ERROR);
			} else if(EXCEPTION_DATA_INVALIDA.equals(e.getMessage())) {
				redirectAttribute.addFlashAttribute("status", STATUS_MESSAGE_ERROR);
				redirectAttribute.addFlashAttribute("titulo", TITULO_MESSAGE_PARTICIPACAO_ALTERADA_ERROR);
				redirectAttribute.addFlashAttribute("conteudo", CONTEUDO_MESSAGE_PARTICIPACAO_DATA_ERROR);
			} else if(EXCEPTION_STATUS_ACAO_NAO_PERMITE_ALTERACAO_TEMPO_PARTICIPACAO.equals(e.getMessage())) {
				redirectAttribute.addFlashAttribute("status", STATUS_MESSAGE_ERROR);
				redirectAttribute.addFlashAttribute("titulo", TITULO_MESSAGE_PARTICIPACAO_ALTERADA_ERROR);
				redirectAttribute.addFlashAttribute("conteudo", CONTEUDO_MESSAGE_PARTICIPAÇÃO_TEMPO_ERROR);
			} 
			
		}
		return R_ACAO + acaoExtensao.getId();
	}

	@RequestMapping(value = "/buscarParticipacoes/{idAcao}", method = RequestMethod.GET)
	public String showGuestList(@PathVariable("idAcao") Integer id, Model model) {
		AcaoExtensao acao = acaoExtensaoRepository.findOne(id);
		model.addAttribute("participacoes", participacaoRepository.findByAcaoExtensao(acao));
		model.addAttribute("cpfCoordenador", acao.getCoordenador().getCpf());
		return FRAGMENTS_TABLE_PARTICIPACOES;
	}

	@RequestMapping("/buscarServidores")
	public @ResponseBody List<Servidor> buscarServidores(@RequestParam("funcao") Funcao funcao) {
		List<Funcao> funcoes = new ArrayList<>();
		funcoes.add(funcao);
		return servidorService.findByFuncao(funcoes);
	}

	@RequestMapping("/buscar")
	public @ResponseBody Participacao buscarParticipacao(@RequestParam("participacao") Participacao participacao) {
		return participacaoService.buscarParticipante(participacao);
	}
	
	/* Método para emissão de declaração da participação de um participante na equipe de trabalho */ 
	
	@RequestMapping(value = "/emitirDeclaracao/{acao}/{idParticipante}", method = RequestMethod.GET) //*
	public ResponseEntity<InputStreamResource> emitirDeclaracao(@PathVariable("idParticipante") Integer idParticipante,
			@PathVariable("acao") Integer idAcaoExtensao, Exception er) throws DocumentException, MalformedURLException, IOException{
	
		
	    ByteArrayInputStream pdf = participacaoService
	    		.emitirDeclaracaoParticipanteEquipeTrabalho(idAcaoExtensao, idParticipante);

		        HttpHeaders headers = new HttpHeaders();
		        headers.add("Content-Disposition", "inline; filename=declaracao.pdf");

		        return ResponseEntity
		                .ok()
		                .headers(headers)
		                .contentType(MediaType.APPLICATION_PDF)
		                .body(new InputStreamResource(pdf));
		        
				
		    }
			
			
	

}

