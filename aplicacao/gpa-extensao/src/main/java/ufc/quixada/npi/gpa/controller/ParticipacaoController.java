package ufc.quixada.npi.gpa.controller;

import static ufc.quixada.npi.gpa.util.Constants.ERRO;
import static ufc.quixada.npi.gpa.util.Constants.FRAGMENTS_TABLE_PARTICIPACOES;
import static ufc.quixada.npi.gpa.util.Constants.MESSAGE_CADASTRO_SUCESSO;
import static ufc.quixada.npi.gpa.util.Constants.MESSAGE_STATUS_RESPONSE;
import static ufc.quixada.npi.gpa.util.Constants.REDIRECT_PAGINA_DETALHES_ACAO;
import static ufc.quixada.npi.gpa.util.Constants.RESPONSE_DATA;
import static ufc.quixada.npi.gpa.util.RedirectConstants.R_ACAO;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.itextpdf.text.DocumentException;

import ufc.quixada.npi.gpa.exception.GpaExtensaoException;
import ufc.quixada.npi.gpa.generation.pdf.BuilderPDFReport;
import ufc.quixada.npi.gpa.model.AcaoExtensao;
import ufc.quixada.npi.gpa.model.Participacao;
import ufc.quixada.npi.gpa.model.Pessoa;
import ufc.quixada.npi.gpa.model.Servidor;
import ufc.quixada.npi.gpa.model.Servidor.Funcao;
import ufc.quixada.npi.gpa.repository.AcaoExtensaoRepository;
import ufc.quixada.npi.gpa.repository.ParticipacaoRepository;
import ufc.quixada.npi.gpa.repository.ServidorRepository;
import ufc.quixada.npi.gpa.service.ParticipacaoService;
import ufc.quixada.npi.gpa.service.PessoaService;
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
	private ServidorRepository servirdorRepository;

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
		} catch (GpaExtensaoException e) {
			redirectAttribute.addAttribute(ERRO, e.getMessage());
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

	@RequestMapping(value = "/excluir/{id}")
	public @ResponseBody void deleteParticipacao(@PathVariable("id") Integer id) {
		participacaoRepository.delete(id);
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
		return servirdorRepository.findByFuncao(funcao);
	}
	
	@RequestMapping(value = "/emitirDeclaracao/{acao}/{idParticipante}", method = RequestMethod.GET) //*
	public ResponseEntity<InputStreamResource> emitirDeclaracao(@PathVariable("idParticipante") Integer idParticipante,
			@PathVariable("acao") Integer idAcaoExtensao, RedirectAttributes attr, Authentication auth, Model model, Exception er) throws DocumentException{
	
		Pessoa pessoa = pessoaService.buscarPorId(idParticipante);
	    AcaoExtensao acaoExtensao = acaoExtensaoRepository.findOne(idAcaoExtensao);
	    Participacao participacao = participacaoRepository.findByParticipanteAndAcaoExtensao(pessoa, acaoExtensao);

			
			
			 ByteArrayInputStream bis = BuilderPDFReport.gerarPdf(acaoExtensao,participacao);

		        HttpHeaders headers = new HttpHeaders();
		        headers.add("Content-Disposition", "inline; filename=declaracao.pdf");

		        return ResponseEntity
		                .ok()
		                .headers(headers)
		                .contentType(MediaType.APPLICATION_PDF)
		                .body(new InputStreamResource(bis));
		        
				//Model mv = model.addAttribute("participante",pessoa);
				
				
				//participacaoService.emitirDeclaracaoParticipanteEquipeTrabalho(idParticipante,idAcaoExtensao,auth.getName());
				
		    }
			
			
	

}

