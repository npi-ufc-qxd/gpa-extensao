package ufc.quixada.npi.gpa.controller;

import static ufc.quixada.npi.gpa.util.Constants.ACAO_EXTENSAO;
import static ufc.quixada.npi.gpa.util.Constants.ACOES_COORDENADOR_SIZE;
import static ufc.quixada.npi.gpa.util.Constants.ACOES_DIRECAO_SIZE;
import static ufc.quixada.npi.gpa.util.Constants.ACOES_VINCULO;
import static ufc.quixada.npi.gpa.util.Constants.ANO_ATUAL;
import static ufc.quixada.npi.gpa.util.Constants.ANOS;
import static ufc.quixada.npi.gpa.util.Constants.FRAGMENTS_TABLE_LISTAGEM_BOLSISTAS;
import static ufc.quixada.npi.gpa.util.Constants.FREQUENCIAS;
import static ufc.quixada.npi.gpa.util.Constants.MESSAGE;
import static ufc.quixada.npi.gpa.util.Constants.MESSAGE_ACAO_ENCERRADA;
import static ufc.quixada.npi.gpa.util.Constants.MODALIDADES;
import static ufc.quixada.npi.gpa.util.Constants.PAGINA_CADASTRO_RETROATIVO_ACAO;
import static ufc.quixada.npi.gpa.util.Constants.PAGINA_LISTAGEM_BOLSISTAS;
import static ufc.quixada.npi.gpa.util.Constants.PESSOA_LOGADA;
import static ufc.quixada.npi.gpa.util.Constants.POSSIVEIS_COORDENADORES;
import static ufc.quixada.npi.gpa.util.Constants.REDIRECT_PAGINA_DETALHES_ACAO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ufc.quixada.npi.gpa.exception.GpaExtensaoException;
import ufc.quixada.npi.gpa.model.AcaoExtensao;
import ufc.quixada.npi.gpa.model.AcaoExtensao.Modalidade;
import ufc.quixada.npi.gpa.model.AcaoExtensao.Status;
import ufc.quixada.npi.gpa.model.FrequenciaView;
import ufc.quixada.npi.gpa.repository.AcaoExtensaoRepository;
import ufc.quixada.npi.gpa.repository.BolsaRepository;
import ufc.quixada.npi.gpa.repository.PessoaRepository;
import ufc.quixada.npi.gpa.repository.ServidorRepository;
import ufc.quixada.npi.gpa.service.AcaoExtensaoService;
import ufc.quixada.npi.gpa.service.BolsaService;

@Controller
@RequestMapping("admin")
@Transactional
public class AdministracaoController {

	@Autowired
	private AcaoExtensaoRepository acaoExtensaoRepository;

	@Autowired
	private PessoaRepository pessoaRepository;

	@Autowired
	private ServidorRepository servidorRepository;

	@Autowired
	private AcaoExtensaoService acaoExtensaoService;

	@Autowired
	private BolsaRepository bolsaRepository;

	@Autowired
	private BolsaService bolsaService;

	@ModelAttribute(ACOES_DIRECAO_SIZE)
	public Integer acoesDirecaoSize(Authentication authentication) {
		return acaoExtensaoRepository.countAcoesTramitacao(Status.NOVO);
	}

	@ModelAttribute(ACOES_COORDENADOR_SIZE)
	public Integer acoesCoordenadorSize(Authentication authentication) {
		return acaoExtensaoRepository.countAcoesCoordenador(authentication.getName());
	}

	@ModelAttribute(PESSOA_LOGADA)
	public String pessoaLogada(Authentication authentication) {
		return pessoaRepository.findByCpf(authentication.getName()).getNome();
	}

	@RequestMapping(value = "/cadastrar", method = RequestMethod.GET)
	public String cadastrarForm(Model model) {
		model.addAttribute(POSSIVEIS_COORDENADORES, servidorRepository.findAll());
		model.addAttribute(ACAO_EXTENSAO, new AcaoExtensao());
		model.addAttribute(MODALIDADES, Modalidade.values());
		model.addAttribute(ACOES_VINCULO, acaoExtensaoRepository.findByModalidadeAndStatus(Modalidade.PROGRAMA, Status.APROVADO));
		return PAGINA_CADASTRO_RETROATIVO_ACAO;
	}

	@RequestMapping(value = "/cadastrar", method = RequestMethod.POST)
	public String cadastroRetroativoAcao(@RequestParam("anexoAcao") MultipartFile arquivo,
			@RequestParam("cargaHoraria") Integer cargaHoraria, @ModelAttribute AcaoExtensao acaoExtensao,
			Model model) {
		try {
			acaoExtensaoService.salvarAcaoRetroativa(acaoExtensao, arquivo, cargaHoraria);
		} catch (GpaExtensaoException e) {
			model.addAttribute(MESSAGE, e.getMessage());
			return PAGINA_CADASTRO_RETROATIVO_ACAO;
		}

		return REDIRECT_PAGINA_DETALHES_ACAO + acaoExtensao.getId();
	}

	@RequestMapping(value = "/bolsistas", method = RequestMethod.GET)
	public String paginaListagemBolsistas(Model model) {
		List<Integer> anos = bolsaRepository.findAnosInicio();
		
		model.addAttribute(ANOS, anos);
		
		if(anos != null && !anos.isEmpty()){
			Integer ano = 0;
			for(Integer aux : anos){
				if(aux > ano){
					ano = aux;
				}
			}
			
			model.addAttribute(ANO_ATUAL, ano);
		}
		
		return PAGINA_LISTAGEM_BOLSISTAS;
	}

	@RequestMapping(value = "/bolsistas", method = RequestMethod.POST)
	public String listaDeBolsistas(Integer ano, Model model) {
		List<FrequenciaView> frequenciasView = bolsaService.getBolsas(ano);

		model.addAttribute(FREQUENCIAS, frequenciasView);
		model.addAttribute(ANO_ATUAL, ano);

		return FRAGMENTS_TABLE_LISTAGEM_BOLSISTAS;
	}

	@RequestMapping(value = "/frequencia", method = RequestMethod.POST)
	public ResponseEntity<String> cadastrarFrequencia(Integer bolsaId, Integer mes, Integer ano, String acao) {
		try {
			bolsaService.setarFrequencia(bolsaId, mes, ano, acao);
		} catch (GpaExtensaoException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
		}
		return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
	}
	
	@RequestMapping(value="/encerrarAcao/{idAcao}")
	public String encerrarAcaoExtensao(@PathVariable("idAcao") Integer idAcao, RedirectAttributes redirect) throws GpaExtensaoException{
		acaoExtensaoService.encerrarAcao(idAcao);
		redirect.addFlashAttribute(MESSAGE,MESSAGE_ACAO_ENCERRADA);
		return REDIRECT_PAGINA_DETALHES_ACAO +idAcao;
	}

}
