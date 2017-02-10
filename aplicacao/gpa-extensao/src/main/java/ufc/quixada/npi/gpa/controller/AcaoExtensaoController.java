package ufc.quixada.npi.gpa.controller;

import static ufc.quixada.npi.gpa.util.Constants.*;
import static ufc.quixada.npi.gpa.util.PageConstants.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ufc.quixada.npi.gpa.exception.GpaExtensaoException;
import ufc.quixada.npi.gpa.model.AcaoExtensao;
import ufc.quixada.npi.gpa.model.AcaoExtensao.Modalidade;
import ufc.quixada.npi.gpa.model.AcaoExtensao.Status;
import ufc.quixada.npi.gpa.model.Bolsa;
import ufc.quixada.npi.gpa.model.Bolsa.TipoBolsa;
import ufc.quixada.npi.gpa.model.Parceiro;
import ufc.quixada.npi.gpa.model.ParceriaExterna;
import ufc.quixada.npi.gpa.model.Parecer;
import ufc.quixada.npi.gpa.model.Participacao;
import ufc.quixada.npi.gpa.model.Participacao.Funcao;
import ufc.quixada.npi.gpa.model.Participacao.Instituicao;
import ufc.quixada.npi.gpa.model.Pendencia;
import ufc.quixada.npi.gpa.model.Pessoa;
import ufc.quixada.npi.gpa.model.Servidor;
import ufc.quixada.npi.gpa.repository.AcaoExtensaoRepository;
import ufc.quixada.npi.gpa.repository.ParceiroRepository;
import ufc.quixada.npi.gpa.repository.ParecerRepository;
import ufc.quixada.npi.gpa.repository.ParticipacaoRepository;
import ufc.quixada.npi.gpa.repository.PessoaRepository;
import ufc.quixada.npi.gpa.repository.ServidorRepository;
import ufc.quixada.npi.gpa.service.AcaoExtensaoService;
import ufc.quixada.npi.gpa.service.DirecaoService;
import ufc.quixada.npi.gpa.service.ParticipacaoService;
import ufc.quixada.npi.gpa.service.PessoaService;

@Controller
@RequestMapping("/acoes")
public class AcaoExtensaoController {

    @Autowired
    private AcaoExtensaoService acaoExtensaoService;

    @Autowired
    private PessoaService pessoaService;

    @Autowired
	private ParceiroRepository parceiroRepository;

	@Autowired
	private ServidorRepository servirdorRepository;

	@Autowired
	private PessoaRepository pessoaRepository;

	@Autowired
	private ParticipacaoRepository participacaoRepository;

	@Autowired
	private AcaoExtensaoRepository acaoExtensaoRepository;

	@Autowired
	private ParecerRepository parecerRepository;

	@Autowired
	private DirecaoService direcaoService;

	@Autowired
	private ParticipacaoService participacaoService;

	/**
	 * Busca todas as ações relacionadas ao usuários logado: que coordena, participa, parecerista ou relator.
	 */
	@GetMapping({"", "/"})
	public String listarMinhasAcoes(Model model, Authentication authentication) {
		Pessoa pessoa = pessoaRepository.findByCpf(authentication.getName());
		model.addAttribute("minhasAcoes", acaoExtensaoService.findAll(pessoa));
		model.addAttribute("meusPareceres", acaoExtensaoService.findByParecer(pessoa));
		return LISTAR_MINHAS_ACOES;
	}

	/**
	 * Busca todas as ações que tenham sido homologadas.
	 */
	@GetMapping("/listar")
	public String listarAcoes(Model model) {
		model.addAttribute("acoes", acaoExtensaoService.findAcoesHomologadas());
		return LISTAR_ACOES;
	}

	/**
	 * Busca uma ação específica
	 */
	@GetMapping("/{acao}")
	public String visualizarAcao(@PathVariable AcaoExtensao acao, Model model) {
		model.addAttribute("acao", acao);
		return VISUALIZAR_ACAO;
	}

    /**
     * Formulário para cadastro de nova ação de extensão
     */
    @PreAuthorize(PERMISSAO_SERVIDOR)
    @RequestMapping("/cadastrar")
	public String cadastrar(Model model, Authentication authentication) {
		model.addAttribute("acao", new AcaoExtensao());
		/*model.addAttribute("dedicacao", pessoaService.findServidor(authentication.getName()).getDedicacao());*/
		model.addAttribute("modalidades", Modalidade.values());
		model.addAttribute("acoesParaVinculo", acaoExtensaoService.findProgramasAprovados());
		model.addAttribute("action", "cadastrar");

		return CADASTRAR_ACAO;
	}

	@RequestMapping(value = "/cadastrar", method = RequestMethod.POST)
	public String cadastrar(@RequestParam(value = "anexoAcao", required = false) MultipartFile arquivo,
							@RequestParam("cargaHoraria") Integer cargaHoraria,
							@Valid @ModelAttribute("acaoExtensao") AcaoExtensao acaoExtensao, Authentication authentication,
							Model model, RedirectAttributes redirect) {
		Pessoa coordenador = pessoaRepository.findByCpf(authentication.getName());

		try {
			acaoExtensao.setCoordenador(coordenador);
			acaoExtensao.setAtivo(true);
			acaoExtensaoService.salvarAcaoExtensao(acaoExtensao, arquivo);
			participacaoService.participacaoCoordenador(acaoExtensao, cargaHoraria);
		} catch (GpaExtensaoException e) {
			redirect.addFlashAttribute(ERRO, e.getMessage());
			return REDIRECT_PAGINA_DETALHES_ACAO + acaoExtensao.getId();
		}

		redirect.addFlashAttribute(MESSAGE, MESSAGE_CADASTRO_SUCESSO);
		return REDIRECT_PAGINA_DETALHES_ACAO + acaoExtensao.getId();

	}

	@RequestMapping(value = "/deletar/{id}", method = RequestMethod.GET)
	public String deletar(@PathVariable("id") Integer id, RedirectAttributes attr, Authentication auth) {
		try {
			acaoExtensaoService.deletarAcaoExtensao(id, auth.getName());
		} catch (GpaExtensaoException e) {
			attr.addFlashAttribute(ERRO, e.getMessage());
		}

		return REDIRECT_PAGINA_INICIAL_COORDENACAO;
	}

	@Transactional(readOnly = true)
	@RequestMapping(value = "/detalhes/{id}", method = RequestMethod.GET)
	public String verDetalhes(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {

		AcaoExtensao acao = acaoExtensaoRepository.findOne(id);

		if (acao == null) {
			redirectAttributes.addFlashAttribute(ERRO, MENSAGEM_ACAO_EXTENSAO_INEXISTENTE);
			return REDIRECT_PAGINA_INICIAL_COORDENACAO;
		}

		model.addAttribute("parceiro", new Parceiro());
		model.addAttribute("parceriaExterna", new ParceriaExterna());
		model.addAttribute(PARCEIROS, parceiroRepository.findAll());

		model.addAttribute("novaParticipacao", new Participacao());
		model.addAttribute("funcoes", Funcao.values());
		model.addAttribute("novaBolsa", new Bolsa());
		model.addAttribute("tipos", TipoBolsa.values());
		model.addAttribute("instituicoes", Instituicao.values());

		model.addAttribute("acaoExtensao", acao);

		if (acao.getStatus().equals(Status.AGUARDANDO_PARECERISTA)) {
			model.addAttribute("pareceristas", parecerRepository.getPossiveisPareceristas(id));
			model.addAttribute(ALERTA_PARECER, MESSAGE_PARECERISTA_NAO_ATRIBUIDO);
			acao.setParecerTecnico(new Parecer());

		} else if (acao.getStatus().equals(Status.AGUARDANDO_RELATOR)) {
			model.addAttribute("pareceristas", direcaoService.getPossiveisPareceristas(id));
			model.addAttribute(ALERTA_RELATO, MESSAGE_RELATOR_NAO_ATRIBUIDO);
			acao.setParecerRelator(new Parecer());

		} else if (acao.getStatus().equals(Status.AGUARDANDO_PARECER_TECNICO)
				|| acao.getStatus().equals(Status.AGUARDANDO_PARECER_RELATOR)) {
			model.addAttribute("pareceristas", parecerRepository.getPossiveisPareceristas(id));
			model.addAttribute("pendencia", new Pendencia());

		} else if (acao.getStatus().equals(Status.RESOLVENDO_PENDENCIAS_PARECER)) {
			model.addAttribute("pendencias", acao.getParecerTecnico().getPendencias());
			model.addAttribute("pendente", true);

		} else if (acao.getStatus().equals(Status.RESOLVENDO_PENDENCIAS_RELATO)) {
			model.addAttribute("pendencias", acao.getParecerRelator().getPendencias());
			model.addAttribute("pendente", true);
		}

		return PAGINA_DETALHES_ACAO_EXTENSAO;
	}

	@RequestMapping(value = "/salvarCodigo/{idAcao}", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> salvarCodigo(@RequestParam("codigoAcao") String codigo,
			@PathVariable("idAcao") Integer idAcao) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (codigo.isEmpty() || ("").equals(codigo)) {
			map.put(ERRO, VALOR_INVALIDO);
			return map;
		}
		AcaoExtensao acao = acaoExtensaoRepository.findOne(idAcao);
		acao.setCodigo(codigo);
		acaoExtensaoRepository.save(acao);
		map.put(MESSAGE_STATUS_RESPONSE, SUCESSO);
		map.put(RESPONSE_DATA, codigo);
		return map;
	}

	@RequestMapping(value = "/salvarNovoCoordenador/{id}", method = RequestMethod.POST)
	public String salvarNovoCoordenador(@PathVariable("id") Integer id,
			@RequestParam("idNovoCoordenador") Integer idNovoCoordenador, @RequestParam("dataInicio") String dataInicio,
			@RequestParam("chNovoCoordenador") Integer cargaHoraria, RedirectAttributes redirectAttributes,
			Authentication authentication) throws ParseException {

		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		Date dataI = df.parse(dataInicio);

		AcaoExtensao acao = acaoExtensaoRepository.findOne(id);

		if (acao == null) {
			redirectAttributes.addFlashAttribute(ERRO, MENSAGEM_ACAO_EXTENSAO_INEXISTENTE);
			return REDIRECT_PAGINA_INICIAL_COORDENACAO;
		}

		Pessoa velhoCoordenador = acao.getCoordenador();

		List<Participacao> pVelhoCoordenador = participacaoRepository.findByAcaoExtensaoAndParticipante(acao,
				velhoCoordenador);

		if (pVelhoCoordenador != null) {
			for (Participacao p : pVelhoCoordenador) {
				if (p.isCoordenador()) {
					p.setDataTermino(dataI);
					p.setCoordenador(false);
					participacaoRepository.save(p);
				}
			}
		}

		Pessoa novoCoordenador = pessoaRepository.findOne(idNovoCoordenador);

		Participacao pVelhaNovoCoordenador = participacaoRepository.findByParticipanteAndAcaoExtensao(novoCoordenador,
				acao);

		if (pVelhaNovoCoordenador != null) {
			pVelhaNovoCoordenador.setDataTermino(dataI);
			participacaoRepository.save(pVelhaNovoCoordenador);
		}

		acao.setCoordenador(novoCoordenador);
		Participacao pNovaNovoCoordenador = participacaoService.participacaoCoordenador(acao, cargaHoraria);
		pNovaNovoCoordenador.setDataInicio(dataI);

		participacaoRepository.save(pNovaNovoCoordenador);
		acaoExtensaoRepository.save(acao);
		return REDIRECT_PAGINA_DETALHES_ACAO + id;
	}

	@RequestMapping("/submeter/{idAcao}")
	public String submeterForm(@PathVariable("idAcao") Integer idAcao, Model model) {
		model.addAttribute("action", "submeter");
		model.addAttribute("acaoExtensao", acaoExtensaoRepository.findOne(idAcao));
		model.addAttribute("modalidades", Modalidade.values());
		model.addAttribute(ACAO_EXTENSAO_ID, idAcao);
		return PAGINA_SUBMETER_ACAO_EXTENSAO;
	}

	@RequestMapping(value = "/submeter", method = RequestMethod.POST)
	public String submeterAcaoExtensao(@RequestParam(value = "anexoAcao", required = false) MultipartFile arquivo,
			@Valid @ModelAttribute AcaoExtensao acao, Model model, BindingResult bind,
			RedirectAttributes redirectAttribute) {

		if (bind.hasErrors() || (acao.getAnexo() == null && arquivo.isEmpty())) {
			model.addAttribute(MESSAGE, MESSAGE_ANEXO);
			model.addAttribute("modalidades", Modalidade.values());
			model.addAttribute(ACAO_EXTENSAO_ID, acao.getId());
			model.addAttribute("action", "submeter");
			return PAGINA_SUBMETER_ACAO_EXTENSAO;
		}

		try {
			acaoExtensaoService.submeterAcaoExtensao(acao, arquivo);
		} catch (GpaExtensaoException e) {
			redirectAttribute.addFlashAttribute(ERRO, e.getMessage());
			return REDIRECT_PAGINA_DETALHES_ACAO + acao.getId();
		}

		redirectAttribute.addFlashAttribute(MESSAGE, MESSAGE_SUBMISSAO);
		return REDIRECT_PAGINA_DETALHES_ACAO + acao.getId();
	}

	@RequestMapping("/editar/{id}")
	public String editar(@PathVariable("id") Integer idAcao, Model model, Authentication authentication) {
		AcaoExtensao acaoExtensao = acaoExtensaoRepository.findOne(idAcao);

		if (acaoExtensao == null) {
			return REDIRECT_PAGINA_INICIAL;
		}

		if (!(acaoExtensao.getStatus().equals(Status.NOVO)
				|| acaoExtensao.getStatus().equals(Status.RESOLVENDO_PENDENCIAS_PARECER)
				|| acaoExtensao.getStatus().equals(Status.RESOLVENDO_PENDENCIAS_RELATO))) {
			return REDIRECT_PAGINA_DETALHES_ACAO + acaoExtensao.getId();
		}

		if (!(acaoExtensao.getCoordenador().getCpf().equals(authentication.getName()))) {
			return REDIRECT_PAGINA_DETALHES_ACAO + acaoExtensao.getId();
		}

		model.addAttribute("action", "editar");
		model.addAttribute("acaoExtensao", acaoExtensao);
		model.addAttribute("modalidades", Modalidade.values());
		model.addAttribute("acoesParaVinculo",
				acaoExtensaoRepository.findByModalidadeAndStatus(Modalidade.PROGRAMA, Status.APROVADO));

		return PAGINA_SUBMETER_ACAO_EXTENSAO;
	}

	@RequestMapping(value = "/editar", method = RequestMethod.POST)
	public String editarAcao(@Valid @ModelAttribute("acaoExtensao") AcaoExtensao acaoExtensao,
			@RequestParam(value = "anexoAcao", required = false) MultipartFile arquivo, Authentication authentication,
			Model model, RedirectAttributes redirect) {

		if (!acaoExtensao.getModalidade().equals(Modalidade.CURSO)
				&& !acaoExtensao.getModalidade().equals(Modalidade.EVENTO)) {
			acaoExtensao.setHorasPraticas(null);
			acaoExtensao.setHorasTeoricas(null);
		}
		if (!acaoExtensao.getModalidade().equals(Modalidade.EVENTO)) {
			acaoExtensao.setProgramacao("");
		}
		try {
			acaoExtensaoService.editarAcaoExtensao(acaoExtensao, arquivo);
		} catch (GpaExtensaoException e) {
			redirect.addFlashAttribute(ERRO, e.getMessage());
			return REDIRECT_PAGINA_DETALHES_ACAO + acaoExtensao.getId();
		}

		redirect.addFlashAttribute(MESSAGE, MESSAGE_EDITADO_SUCESSO);
		return REDIRECT_PAGINA_DETALHES_ACAO + acaoExtensao.getId();

	}

	@RequestMapping("/buscarCoordenadores/{id}")
	public @ResponseBody List<Servidor> buscarCoordenadores(@PathVariable("id") Integer idCoordenadorAtual) {
		return servirdorRepository.findByPessoa_idNotIn(idCoordenadorAtual);
	}

	@RequestMapping(value = "/salvarRelatorioFinal/{id}", method = RequestMethod.POST)
	public String salvarRelatorioFinal(@RequestParam("relatorioFinal") MultipartFile arquivo,
			@PathVariable("id") Integer id, RedirectAttributes redirectAttribute) {

		if (arquivo.isEmpty() || ("").equals(arquivo) || id == null) {
			redirectAttribute.addFlashAttribute(ERRO, MESSAGE_SALVAR_ARQUIVO_ERROR);
			return REDIRECT_PAGINA_INICIAL_COORDENACAO;
		}
		try {
			acaoExtensaoService.salvarRelatorioFinal(id, arquivo);
		} catch (GpaExtensaoException e) {
			redirectAttribute.addFlashAttribute(ERRO, MESSAGE_SALVAR_ARQUIVO_ERROR);
			return REDIRECT_PAGINA_INICIAL_COORDENACAO;
		}
		return REDIRECT_PAGINA_DETALHES_ACAO + id;
	}
}
