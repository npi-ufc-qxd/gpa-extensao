package ufc.quixada.npi.gpa.controller;

import static ufc.quixada.npi.gpa.util.Constants.ERRO;
import static ufc.quixada.npi.gpa.util.Constants.FRAGMENTS_TABLE_BOLSAS;
import static ufc.quixada.npi.gpa.util.Constants.MESSAGE_DATA_ANTERIOR;
import static ufc.quixada.npi.gpa.util.Constants.MESSAGE_STATUS_RESPONSE;
import static ufc.quixada.npi.gpa.util.Constants.PAGINA_DETALHES_BOLSISTA;
import static ufc.quixada.npi.gpa.util.Constants.RESPONSE_DATA;
import static ufc.quixada.npi.gpa.util.PageConstants.VISUALIZAR_ACAO;
import static ufc.quixada.npi.gpa.util.RedirectConstants.R_ACAO;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ufc.quixada.npi.gpa.exception.GpaExtensaoException;
import ufc.quixada.npi.gpa.model.AcaoExtensao;
import ufc.quixada.npi.gpa.model.Aluno;
import ufc.quixada.npi.gpa.model.Bolsa;
import ufc.quixada.npi.gpa.service.AcaoExtensaoService;
import ufc.quixada.npi.gpa.service.AlunoService;
import ufc.quixada.npi.gpa.service.BolsaService;

@Controller
@Transactional
@RequestMapping("/bolsa")
public class BolsaController {

	@Autowired
	private AcaoExtensaoService acaoExtensaoService;

	@Autowired
	private AlunoService alunoService;

	
	@Autowired 
	private BolsaService bolsaService;

	@RequestMapping(value = "/salvarBolsas/{idAcao}", method = RequestMethod.POST)
	public String salvarBolsas(@RequestParam("bolsasRecebidas") Integer numeroBolsas,
			@PathVariable("idAcao") Integer idAcao, Model model) {
		
		AcaoExtensao acao = acaoExtensaoService.findById(idAcao);
		boolean message = acaoExtensaoService.salvarAcaoBolsasRecebidas(acao, numeroBolsas);
		
		model.addAttribute("message", message);
		model.addAttribute("acao", acao);
		
		return VISUALIZAR_ACAO;
	}

	@RequestMapping(value = "/cadastrar/{acao}", method = RequestMethod.POST)
	public String adicionarBolsista(Bolsa bolsa, @PathVariable("acao") AcaoExtensao acao,
			RedirectAttributes redirectAttributes) {
		
		try {
			bolsaService.adicionarBolsista(acao, bolsa);
		} catch (GpaExtensaoException e) {
			redirectAttributes.addAttribute(ERRO, e.getMessage());
		}

		return R_ACAO + acao.getId();

	}

	@RequestMapping(value = "/buscarBolsas/{idAcao}", method = RequestMethod.GET)
	public String showGuestList(@PathVariable("idAcao") Integer id, Model model, Authentication auth) {
		model.addAttribute("bolsas", bolsaService.listarBolsasAcao(id));
		model.addAttribute("cpfCoordenador", acaoExtensaoService.buscarCpfCoordenador(id));
		return FRAGMENTS_TABLE_BOLSAS;
	}

	@RequestMapping(value = "/excluir/{id}")
	public @ResponseBody void deleteBolsa(@PathVariable("id") Integer id) {
		bolsaService.deletarBolsa(id);
	}

	@RequestMapping(value = "/encerrar/{id}", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> encerrarBolsa(@PathVariable("id") Integer id,
			@RequestParam("dataTermino") String data) throws ParseException {
		Map<String, Object> map = new HashMap<String, Object>();

		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		Date dataTermino = df.parse(data);
		
		Bolsa bolsa = bolsaService.buscarBolsa(id);

		if (bolsa.getInicio().before(dataTermino)) {
			bolsaService.encerrarBolsa(bolsa, dataTermino);
		} else {
			map.put(MESSAGE_STATUS_RESPONSE, "ERROR");
			map.put(RESPONSE_DATA, MESSAGE_DATA_ANTERIOR);
		}

		return map;
	}

	@RequestMapping("/buscarAlunos")
	public @ResponseBody List<Aluno> buscarAlunos() {
		return alunoService.findAllAlunos();
	}

	@RequestMapping(value = "/detalhes/{id}", method = RequestMethod.GET)
	public String detalhesBolsista(@PathVariable("id") Integer idAluno, Model model) {
		model.addAttribute("aluno", alunoService.buscarAluno(idAluno));
		model.addAttribute("bolsas", bolsaService.listarBolsasAluno(idAluno));
		return PAGINA_DETALHES_BOLSISTA;
	}
}
