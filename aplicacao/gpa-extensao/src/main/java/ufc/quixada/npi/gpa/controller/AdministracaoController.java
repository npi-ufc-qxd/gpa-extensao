package ufc.quixada.npi.gpa.controller;

import static ufc.quixada.npi.gpa.util.Constants.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ufc.quixada.npi.gpa.exception.GpaExtensaoException;
import ufc.quixada.npi.gpa.model.AcaoExtensao;
import ufc.quixada.npi.gpa.model.AcaoExtensao.Modalidade;
import ufc.quixada.npi.gpa.model.AcaoExtensao.Status;
import ufc.quixada.npi.gpa.model.FrequenciaView;
import ufc.quixada.npi.gpa.model.Papel;
import ufc.quixada.npi.gpa.model.Papel.Tipo;
import ufc.quixada.npi.gpa.model.Pessoa;
import ufc.quixada.npi.gpa.repository.AcaoExtensaoRepository;
import ufc.quixada.npi.gpa.repository.BolsaRepository;
import ufc.quixada.npi.gpa.repository.PapelRepository;
import ufc.quixada.npi.gpa.repository.PessoaRepository;
import ufc.quixada.npi.gpa.repository.ServidorRepository;
import ufc.quixada.npi.gpa.service.AcaoExtensaoService;
import ufc.quixada.npi.gpa.service.BolsaService;
import ufc.quixada.npi.gpa.service.ServidorService;

@Controller
@RequestMapping("admin")
@Transactional
public class AdministracaoController {

	@Autowired
	private ServidorService servidorService;

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

	@Autowired
	private PapelRepository papelRepository;

	@RequestMapping(value = "/cadastrar", method = RequestMethod.GET)
	public String cadastrarForm(Model model) {
		model.addAttribute("possiveisCoordenadores", servidorRepository.findAll());
		model.addAttribute("acaoExtensao", new AcaoExtensao());
		model.addAttribute("modalidades", Modalidade.values());
		model.addAttribute("acoesParaVinculo",
				acaoExtensaoRepository.findByModalidadeAndStatus(Modalidade.PROGRAMA, Status.APROVADO));
		model.addAttribute("action", "cadastrar");
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

		model.addAttribute("anos", anos);

		if (anos != null && !anos.isEmpty()) {
			Integer ano = 0;
			for (Integer aux : anos) {
				if (aux > ano) {
					ano = aux;
				}
			}

			model.addAttribute("anoAtual", ano);
		}

		return PAGINA_LISTAGEM_BOLSISTAS;
	}

	@RequestMapping(value = "/bolsistas", method = RequestMethod.POST)
	public String listaDeBolsistas(Integer ano, Model model) {
		List<FrequenciaView> frequenciasView = bolsaService.getBolsas(ano);

		model.addAttribute("frequencias", frequenciasView);
		model.addAttribute("anoAtual", ano);

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

	@RequestMapping("/editar/{id}")
	public String editarAcaoExtensao(@PathVariable("id") Integer idAcao, Model model, Authentication authentication) {
		AcaoExtensao acaoExtensao = acaoExtensaoRepository.findOne(idAcao);

		if (acaoExtensao == null) {
			return REDIRECT_PAGINA_INICIAL;
		}

		if (!(acaoExtensao.getStatus().equals(Status.APROVADO) && acaoExtensao.isAtivo())) {
			return REDIRECT_PAGINA_DETALHES_ACAO + acaoExtensao.getId();
		}

		Pessoa pessoa = pessoaRepository.findByCpf(authentication.getName());
		if (!pessoa.isAdministracao()) {
			return REDIRECT_PAGINA_DETALHES_ACAO + acaoExtensao.getId();
		}

		model.addAttribute("acaoExtensao", acaoExtensao);
		model.addAttribute("modalidades", Modalidade.values());
		model.addAttribute("acoesParaVinculo",
				acaoExtensaoRepository.findByModalidadeAndStatus(Modalidade.PROGRAMA, Status.APROVADO));
		model.addAttribute("action", "editar");

		return PAGINA_CADASTRO_RETROATIVO_ACAO;
	}

	@RequestMapping(value = "/editar", method = RequestMethod.POST)
	public String formEditarAcaoExtensao(@RequestParam(value = "anexoAcao", required = false) MultipartFile arquivo,
			@ModelAttribute AcaoExtensao acaoExtensao, Model model) {

		AcaoExtensao oldAcao = acaoExtensaoRepository.findOne(acaoExtensao.getId());

		oldAcao.setTitulo(acaoExtensao.getTitulo());
		oldAcao.setResumo(acaoExtensao.getResumo());
		oldAcao.setModalidade(acaoExtensao.getModalidade());
		oldAcao.setBolsasSolicitadas(acaoExtensao.getBolsasSolicitadas());
		oldAcao.setInicio(acaoExtensao.getInicio());
		oldAcao.setTermino(acaoExtensao.getTermino());
		oldAcao.setProrrogavel(acaoExtensao.isProrrogavel());
		oldAcao.setVinculo(acaoExtensao.getVinculo());

		if (!acaoExtensao.getModalidade().equals(Modalidade.CURSO)
				&& !acaoExtensao.getModalidade().equals(Modalidade.EVENTO)) {
			acaoExtensao.setHorasPraticas(null);
			acaoExtensao.setHorasTeoricas(null);
		} else {
			oldAcao.setHorasPraticas(acaoExtensao.getHorasPraticas());
			oldAcao.setHorasTeoricas(acaoExtensao.getHorasTeoricas());
		}

		if (!acaoExtensao.getModalidade().equals(Modalidade.EVENTO)) {
			acaoExtensao.setProgramacao("");
		} else {
			oldAcao.setProgramacao(acaoExtensao.getProgramacao());
		}

		try {
			acaoExtensaoService.editarAcaoExtensao(oldAcao, arquivo);
		} catch (GpaExtensaoException e) {
			model.addAttribute(ERRO, e.getMessage());
			return REDIRECT_PAGINA_DETALHES_ACAO + acaoExtensao.getId();
		}

		return REDIRECT_PAGINA_DETALHES_ACAO + acaoExtensao.getId();
	}

	@RequestMapping(value = "/encerrarAcao/{idAcao}")
	public String encerrarAcaoExtensao(@PathVariable("idAcao") Integer idAcao, RedirectAttributes redirect)
			throws GpaExtensaoException {
		acaoExtensaoService.encerrarAcao(idAcao);
		redirect.addFlashAttribute(MESSAGE, MESSAGE_ACAO_ENCERRADA);
		return REDIRECT_PAGINA_DETALHES_ACAO + idAcao;
	}

	@RequestMapping(value = "/salvarNumeroProcesso/{idAcao}", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> salvarNumeroProcesso(@RequestParam("numeroProcesso") String numeroProcesso,
			@PathVariable("idAcao") Integer idAcao) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (numeroProcesso.isEmpty() || ("").equals(numeroProcesso)) {
			map.put(ERRO, VALOR_INVALIDO);
			return map;
		}
		AcaoExtensao acao = acaoExtensaoRepository.findOne(idAcao);
		acao.setNumeroProcesso(numeroProcesso);
		acaoExtensaoRepository.save(acao);
		map.put(MESSAGE_STATUS_RESPONSE, SUCESSO);
		map.put(RESPONSE_DATA, numeroProcesso);
		return map;
	}

	@RequestMapping("/gerenciarPapeis")
	public String gerenciarPapeisForm(Model model) {
		model.addAttribute("pessoas", pessoaRepository.findAll());
		return PAGINA_VINCULAR_PAPEIS;
	}

	@RequestMapping(value = "/gerenciarPapeis", params = { "pessoa" })
	public String gerenciaPapeis(@RequestParam("pessoa") Pessoa pessoa, Model model) {

		List<Tipo> papeisPessoa = new ArrayList<Tipo>();
		for (Papel papel : pessoa.getPapeis()) {
			papeisPessoa.add(papel.getNome());
		}

		model.addAttribute("pessoa", pessoa);
		model.addAttribute("pessoas", pessoaRepository.findAll());
		model.addAttribute("papeis", Papel.Tipo.values());
		model.addAttribute("papeisPessoa", papeisPessoa);
		return PAGINA_VINCULAR_PAPEIS;
	}

	@RequestMapping(value = "/salvarPapeis/{idPessoa}", method = RequestMethod.POST)
	public String salvarPapeis(@RequestParam("novosPapeis") List<Tipo> papeis, @PathVariable("idPessoa") Pessoa pessoa,
			Model model) {
		List<Papel> novosPapeis = new ArrayList<Papel>();

		for (Tipo papel : papeis) {
			novosPapeis.add(papelRepository.findByNome(papel));
		}

		pessoa.setPapeis(novosPapeis);
		pessoaRepository.save(pessoa);

		return gerenciaPapeis(pessoa, model);
	}
}
