package ufc.quixada.npi.gpa.controller;

import static ufc.quixada.npi.gpa.util.Constants.ERROR_UPPERCASE;
import static ufc.quixada.npi.gpa.util.Constants.FRAGMENTS_TABLE_PARTICIPACOES;
import static ufc.quixada.npi.gpa.util.Constants.MESSAGE_CADASTRO_SUCESSO;
import static ufc.quixada.npi.gpa.util.Constants.MESSAGE_STATUS_RESPONSE;
import static ufc.quixada.npi.gpa.util.Constants.OK_UPPERCASE;
import static ufc.quixada.npi.gpa.util.Constants.RESPONSE_DATA;
import static ufc.quixada.npi.gpa.util.Constants.PESSOA_LOGADA;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ufc.quixada.npi.gpa.model.AcaoExtensao;
import ufc.quixada.npi.gpa.model.Aluno;
import ufc.quixada.npi.gpa.model.Participacao;
import ufc.quixada.npi.gpa.model.Pessoa;
import ufc.quixada.npi.gpa.model.Servidor;
import ufc.quixada.npi.gpa.repository.AcaoExtensaoRepository;
import ufc.quixada.npi.gpa.repository.AlunoRepository;
import ufc.quixada.npi.gpa.repository.ParticipacaoRepository;
import ufc.quixada.npi.gpa.repository.PessoaRepository;
import ufc.quixada.npi.gpa.repository.ServidorRepository;
import ufc.quixada.npi.gpa.validator.ParticipacaoValidator;

@Controller
@Transactional
@RequestMapping("/participacao")
public class ParticipacaoController {

	@Autowired
	private AcaoExtensaoRepository acaoExtensaoRepository;
	
	@Autowired
	private AlunoRepository alunoRepository;
	
	@Autowired
	private ParticipacaoRepository participacaoRepository;
	
	@Autowired
	private ParticipacaoValidator participacaoValidator;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private ServidorRepository servirdorRepository;
	
	@ModelAttribute(PESSOA_LOGADA)
	public String pessoaLogada(Authentication authentication){
		return pessoaRepository.findByCpf(authentication.getName()).getNome();
	}
	
	@RequestMapping(value="/cadastrar/{idAcao}", method=RequestMethod.POST)
	public @ResponseBody Map<String, Object> adicionarParticipacao(@Valid @ModelAttribute("participacao") Participacao participacao, @PathVariable("idAcao") Integer idAcao, 
			BindingResult result, Model model, RedirectAttributes redirectAttributes, Authentication authentication) {
		
		AcaoExtensao acao = acaoExtensaoRepository.findOne(idAcao);
		
		participacao.setAcaoExtensao(acao);
		participacaoValidator.validate(participacao, result);
		
		Map<String, Object> map = new HashMap<String, Object>();
		if(result.hasErrors()) {
			map.put(MESSAGE_STATUS_RESPONSE, ERROR_UPPERCASE);
			map.put(RESPONSE_DATA, result.getFieldErrors());
			return map;
		}
		
		Pessoa usuario = pessoaRepository.findByCpf(authentication.getName());
		
		if(participacao.getParticipante() != null && participacao.getParticipante().getId() == usuario.getId()) {
			participacao.setCoordenador(true);
		}
		
		participacao.setDataInicio(acao.getInicio());
		participacao.setDataTermino(acao.getTermino());
		
		participacaoRepository.save(participacao);
		
		map.put(MESSAGE_STATUS_RESPONSE, "OK");
		map.put(RESPONSE_DATA, MESSAGE_CADASTRO_SUCESSO);
		return map;
	}
	
	@RequestMapping(value="/excluir/{id}")
	public @ResponseBody void deleteParticipacao(@PathVariable("id") Integer id){
		participacaoRepository.delete(id);
	}	
	
	@RequestMapping(value = "/buscarParticipacoes/{idAcao}", method = RequestMethod.GET)
	public String showGuestList(@PathVariable("idAcao") Integer id, Model model) {
		AcaoExtensao acao = acaoExtensaoRepository.findOne(id);
	    model.addAttribute("participacoes", participacaoRepository.findByAcaoExtensao(acao));
	    
	    return FRAGMENTS_TABLE_PARTICIPACOES;
	}
	
	@RequestMapping(value = "/vincularBolsistas/{idParticipacao}/{idAluno}", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> vincularBolsistas(@PathVariable("idAluno") Integer idAluno,
			@PathVariable("idParticipacao") Integer idParticipacao) {
		Participacao participacao = participacaoRepository.findOne(idParticipacao);
		Pessoa aluno = pessoaRepository.findOne(idAluno);
		
		participacao.setNomeParticipante("");
		participacao.setCpfParticipante("");
		participacao.setParticipante(aluno);
		participacaoRepository.save(participacao);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(MESSAGE_STATUS_RESPONSE, OK_UPPERCASE);
		map.put(RESPONSE_DATA, MESSAGE_CADASTRO_SUCESSO);
		return map;
	}

	@RequestMapping("/buscarServidores")
	public @ResponseBody List<Servidor> buscarServidores() {
		return servirdorRepository.findAll();
	}
	
	@RequestMapping("/buscarAlunos")
	public @ResponseBody List<Aluno> buscarAlunos() {
		return alunoRepository.findAll();
	}
}
