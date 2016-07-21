package ufc.quixada.npi.gpa.controller;

import static ufc.quixada.npi.gpa.util.Constants.ERROR_UPPERCASE;
import static ufc.quixada.npi.gpa.util.Constants.FRAGMENTS_TABLE_BOLSAS;
import static ufc.quixada.npi.gpa.util.Constants.MESSAGE;
import static ufc.quixada.npi.gpa.util.Constants.MESSAGE_CADASTRO_SUCESSO;
import static ufc.quixada.npi.gpa.util.Constants.MESSAGE_DATA_ANTERIOR;
import static ufc.quixada.npi.gpa.util.Constants.MESSAGE_EDITADO_SUCESSO;
import static ufc.quixada.npi.gpa.util.Constants.MESSAGE_STATUS_RESPONSE;
import static ufc.quixada.npi.gpa.util.Constants.PESSOA_LOGADA;
import static ufc.quixada.npi.gpa.util.Constants.RESPONSE_DATA;
import static ufc.quixada.npi.gpa.util.Constants.SUCESSO;
import static ufc.quixada.npi.gpa.util.Constants.BOLSAS;
import static ufc.quixada.npi.gpa.util.Constants.ALUNO;
import static ufc.quixada.npi.gpa.util.Constants.PAGINA_DETALHES_BOLSISTA;
import static ufc.quixada.npi.gpa.util.Constants.CPF_COORDENADOR;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ufc.quixada.npi.gpa.model.AcaoExtensao;
import ufc.quixada.npi.gpa.model.Aluno;
import ufc.quixada.npi.gpa.model.Bolsa;
import ufc.quixada.npi.gpa.repository.AcaoExtensaoRepository;
import ufc.quixada.npi.gpa.repository.AlunoRepository;
import ufc.quixada.npi.gpa.repository.BolsaRepository;
import ufc.quixada.npi.gpa.repository.PessoaRepository;
import ufc.quixada.npi.gpa.validator.BolsaValidator;

@Controller
@Transactional
@RequestMapping("/bolsa")
public class BolsaController {

	@Autowired
	private AcaoExtensaoRepository acaoExtensaoRepository;

	@Autowired
	private AlunoRepository alunoRepository;
	
	@Autowired
	private BolsaRepository bolsaRepository;
	
	@Autowired
	private BolsaValidator bolsaValidator;

	@Autowired
	private PessoaRepository pessoaRepository;

	@ModelAttribute(PESSOA_LOGADA)
	public String pessoaLogada(Authentication authentication) {
		return pessoaRepository.findByCpf(authentication.getName()).getNome();
	}

	@RequestMapping(value = "/salvarBolsas/{idAcao}", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> salvarBolsas(@RequestParam("bolsasRecebidas") Integer numeroBolsas,
			@PathVariable("idAcao") Integer idAcao) {
		Map<String, Object> map = new HashMap<String, Object>();

		AcaoExtensao acao = acaoExtensaoRepository.findOne(idAcao);
		acao.setBolsasRecebidas(numeroBolsas);
		acaoExtensaoRepository.save(acao);
		map.put(MESSAGE_STATUS_RESPONSE, SUCESSO);
		map.put(MESSAGE, MESSAGE_EDITADO_SUCESSO);
		map.put(RESPONSE_DATA, numeroBolsas);
		return map;
	}

	@RequestMapping(value = "/cadastrar/{idAcao}", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> adicionarBolsista(@Valid @ModelAttribute("novaBolsa") Bolsa bolsa,
			@PathVariable("idAcao") Integer idAcao, BindingResult result, Model model,
			RedirectAttributes redirectAttributes, Authentication authentication) {
		
		AcaoExtensao acao = acaoExtensaoRepository.findOne(idAcao);

		bolsa.setAcaoExtensao(acao);
		bolsa.setAtivo(true);
		
		bolsaValidator.validate(bolsa, result);

		Map<String, Object> map = new HashMap<String, Object>();
		if (result.hasErrors()) {
			map.put(MESSAGE_STATUS_RESPONSE, ERROR_UPPERCASE);
			map.put(RESPONSE_DATA, result.getFieldErrors());
			return map;
		}

		bolsaRepository.save(bolsa);

		map.put(MESSAGE_STATUS_RESPONSE, "OK");
		map.put(RESPONSE_DATA, MESSAGE_CADASTRO_SUCESSO);
		return map;
	}
	
	@RequestMapping(value = "/buscarBolsas/{idAcao}", method = RequestMethod.GET)
	public String showGuestList(@PathVariable("idAcao") Integer id, Model model,Authentication auth) {
	    model.addAttribute("bolsas", bolsaRepository.findByAcaoExtensao_id(id));
	    model.addAttribute(CPF_COORDENADOR,acaoExtensaoRepository.findCoordenadorById(id));
	    return FRAGMENTS_TABLE_BOLSAS;
	}
	
	@RequestMapping(value="/excluir/{id}")
	public @ResponseBody void deleteBolsa(@PathVariable("id") Integer id){
		bolsaRepository.delete(id);
	}	
	
	@RequestMapping(value="/encerrar/{id}", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> encerrarBolsa(@PathVariable("id") Integer id, @RequestParam("dataTermino") String data) throws ParseException{
		Map<String, Object> map = new HashMap<String, Object>();
		
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		Date dataTermino = df.parse(data);
		Bolsa bolsa = bolsaRepository.findOne(id);
		
		if(bolsa.getInicio().before(dataTermino)) {
			bolsa.setAtivo(false);
			bolsa.setTermino(dataTermino);
			bolsaRepository.save(bolsa);
		} else {
			map.put(MESSAGE_STATUS_RESPONSE, ERROR_UPPERCASE);
			map.put(RESPONSE_DATA, MESSAGE_DATA_ANTERIOR);
		}
		
		return map;
	}	

	@RequestMapping("/buscarAlunos")
	public @ResponseBody List<Aluno> buscarAlunos() {
		return alunoRepository.findAll();
	}
	
	@RequestMapping(value="/detalhes/{id}",method=RequestMethod.GET)
	public String detalhesBolsista(@PathVariable("id") Integer idAluno, Model model){
		model.addAttribute(ALUNO, alunoRepository.findOne(idAluno));
		model.addAttribute(BOLSAS, bolsaRepository.findByBolsista_id(idAluno));
		return PAGINA_DETALHES_BOLSISTA;
	}
}
