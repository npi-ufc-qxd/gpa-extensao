package ufc.quixada.npi.gpa.controller;

import static ufc.quixada.npi.gpa.util.Constants.ALERTA_PARECER;
import static ufc.quixada.npi.gpa.util.Constants.ALERTA_RELATO;
import static ufc.quixada.npi.gpa.util.Constants.CONTEUDO_MESSAGE_ACAO_SUBMETIDA;
import static ufc.quixada.npi.gpa.util.Constants.CONTEUDO_MESSAGE_ACAO_SUBMETIDA_ERROR;
import static ufc.quixada.npi.gpa.util.Constants.CONTEUDO_MESSAGE_CADASTRAR_CODIGO;
import static ufc.quixada.npi.gpa.util.Constants.CONTEUDO_MESSAGE_TRANSFERIR_COORDENACAO;
import static ufc.quixada.npi.gpa.util.Constants.CONTEUDO_MESSAGE_TRANSFERIR_COORDENACAO_DATA_MAIOR_IGUAL_ERROR;
import static ufc.quixada.npi.gpa.util.Constants.CONTEUDO_MESSAGE_TRANSFERIR_COORDENACAO_DATA_MENOR_ERROR;
import static ufc.quixada.npi.gpa.util.Constants.ERRO;
import static ufc.quixada.npi.gpa.util.Constants.MENSAGEM_ACAO_EXTENSAO_INEXISTENTE;
import static ufc.quixada.npi.gpa.util.Constants.MENSAGEM_DATA_IGUAL_MAIOR;
import static ufc.quixada.npi.gpa.util.Constants.MENSAGEM_DATA_MENOR;
import static ufc.quixada.npi.gpa.util.Constants.MESSAGE;
import static ufc.quixada.npi.gpa.util.Constants.MESSAGE_CADASTRO_SUCESSO;
import static ufc.quixada.npi.gpa.util.Constants.MESSAGE_EDITADO_SUCESSO;
import static ufc.quixada.npi.gpa.util.Constants.MESSAGE_PARECERISTA_NAO_ATRIBUIDO;
import static ufc.quixada.npi.gpa.util.Constants.MESSAGE_RELATOR_NAO_ATRIBUIDO;
import static ufc.quixada.npi.gpa.util.Constants.MESSAGE_SALVAR_ARQUIVO_ERROR;
import static ufc.quixada.npi.gpa.util.Constants.PAGINA_DETALHES_ACAO_EXTENSAO;
import static ufc.quixada.npi.gpa.util.Constants.PARCEIROS;
import static ufc.quixada.npi.gpa.util.Constants.PERMISSAO_SERVIDOR;
import static ufc.quixada.npi.gpa.util.Constants.REDIRECT_PAGINA_DETALHES_ACAO;
import static ufc.quixada.npi.gpa.util.Constants.REDIRECT_PAGINA_INICIAL_COORDENACAO;
import static ufc.quixada.npi.gpa.util.Constants.STATUS_MESSAGE_ERROR;
import static ufc.quixada.npi.gpa.util.Constants.STATUS_MESSAGE_SUCCESS;
import static ufc.quixada.npi.gpa.util.Constants.TITULO_MESSAGE_ACAO_SUBMETIDA;
import static ufc.quixada.npi.gpa.util.Constants.TITULO_MESSAGE_ACAO_SUBMETIDA_ERROR;
import static ufc.quixada.npi.gpa.util.Constants.TITULO_MESSAGE_CADASTRAR_CODIGO;
import static ufc.quixada.npi.gpa.util.Constants.TITULO_MESSAGE_TRANSFERIR_COORDENACAO;
import static ufc.quixada.npi.gpa.util.Constants.TITULO_MESSAGE_TRANSFERIR_COORDENACAO_ERROR;
import static ufc.quixada.npi.gpa.util.PageConstants.CADASTRAR_ACAO;
import static ufc.quixada.npi.gpa.util.PageConstants.LISTAR_ACOES;
import static ufc.quixada.npi.gpa.util.PageConstants.LISTAR_MINHAS_ACOES;
import static ufc.quixada.npi.gpa.util.PageConstants.VISUALIZAR_ACAO;
import static ufc.quixada.npi.gpa.util.RedirectConstants.R_ACOES;
import static ufc.quixada.npi.gpa.util.RedirectConstants.R_INDEX;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ufc.quixada.npi.gpa.exception.GpaExtensaoException;
import ufc.quixada.npi.gpa.model.AcaoExtensao;
import ufc.quixada.npi.gpa.model.AcaoExtensao.Modalidade;
import ufc.quixada.npi.gpa.model.AcaoExtensao.Status;
import ufc.quixada.npi.gpa.model.Bolsa;
import ufc.quixada.npi.gpa.model.Bolsa.TipoBolsa;
import ufc.quixada.npi.gpa.model.Parceiro;
import ufc.quixada.npi.gpa.model.Parceiro.Tipo;
import ufc.quixada.npi.gpa.model.ParceriaExterna;
import ufc.quixada.npi.gpa.model.Parecer;
import ufc.quixada.npi.gpa.model.Participacao;
import ufc.quixada.npi.gpa.model.Participacao.Funcao;
import ufc.quixada.npi.gpa.model.Participacao.Instituicao;
import ufc.quixada.npi.gpa.model.Pendencia;
import ufc.quixada.npi.gpa.model.Pessoa;
import ufc.quixada.npi.gpa.model.Servidor;
import ufc.quixada.npi.gpa.repository.AcaoExtensaoRepository;
import ufc.quixada.npi.gpa.repository.ParecerRepository;
import ufc.quixada.npi.gpa.service.AcaoExtensaoService;
import ufc.quixada.npi.gpa.service.AlunoService;
import ufc.quixada.npi.gpa.service.DirecaoService;
import ufc.quixada.npi.gpa.service.ParceiroService;
import ufc.quixada.npi.gpa.service.ParticipacaoService;
import ufc.quixada.npi.gpa.service.PessoaService;
import ufc.quixada.npi.gpa.service.ServidorService;

@Controller
@RequestMapping("/acoes")
public class AcaoExtensaoController {

	@Autowired
	private AcaoExtensaoService acaoExtensaoService;

	@Autowired
	private PessoaService pessoaService;

	@Autowired
	private AcaoExtensaoRepository acaoExtensaoRepository;

	@Autowired
	private ParecerRepository parecerRepository;

	@Autowired
	private DirecaoService direcaoService;

	@Autowired
	private ParticipacaoService participacaoService;

	@Autowired
	private ServidorService servidorService;

	@Autowired
	private AlunoService alunoService;

	@Autowired
	private ParceiroService parceiroService;

	/**
	 * Busca todas as ações que estão em tramitação e ainda não foram aprovadas
	 */
	@GetMapping({ "", "/tramitacao" })
	public String listarAcoesEmTramitacao(Model model) {
		model.addAttribute("acoes", acaoExtensaoService.findAcoesEmTramitacao());
		model.addAttribute("tramitacao", acaoExtensaoService.countAcoesEmTramitacao());
		model.addAttribute("andamento", acaoExtensaoService.countAcoesEmAndamento());
		model.addAttribute("encerrada", acaoExtensaoService.countAcoesEncerradas());
		model.addAttribute("listaAtual", "tramitacao");

		return LISTAR_ACOES;
	}

	/**
	 * Busca todas as ações que já foram aprovadas e estão em andamento
	 */
	@GetMapping("/andamento")
	public String listarAcoesEmAndamento(Model model) {
		model.addAttribute("acoes", acaoExtensaoService.findAcoesEmAndamento());
		model.addAttribute("tramitacao", acaoExtensaoService.countAcoesEmTramitacao());
		model.addAttribute("andamento", acaoExtensaoService.countAcoesEmAndamento());
		model.addAttribute("encerrada", acaoExtensaoService.countAcoesEncerradas());
		model.addAttribute("listaAtual", "andamento");
		return LISTAR_ACOES;
	}

	/**
	 * Busca todas as ações que já foram encerradas
	 */
	@GetMapping("/encerrada")
	public String listarAcoesEncerradas(Model model) {
		model.addAttribute("acoes", acaoExtensaoService.findAcoesEncerradas());
		model.addAttribute("tramitacao", acaoExtensaoService.countAcoesEmTramitacao());
		model.addAttribute("andamento", acaoExtensaoService.countAcoesEmAndamento());
		model.addAttribute("encerrada", acaoExtensaoService.countAcoesEncerradas());
		model.addAttribute("listaAtual", "encerrada");
		return LISTAR_ACOES;
	}

	/**
	 * Busca todas as ações relacionadas ao usuários logado: que
	 * coordena,participa, parecerista ou relator.
	 */
	@GetMapping({ "/minhas", "/minhas/minhas-acoes" })
	public String listarMinhasAcoes(Model model, Authentication authentication) {
		Pessoa pessoa = pessoaService.buscarPorCpf(authentication.getName());
		model.addAttribute("countmeusTudo", acaoExtensaoService.countMinhasAcoes(pessoa));
		model.addAttribute("countmeusPareceres", acaoExtensaoService.countMinhasAcoesAguardandoParecer(pessoa));
		model.addAttribute("countmeusPareceresEmitidos", acaoExtensaoService.countMinhasAcoesPareceresEmitidos(pessoa));
		model.addAttribute("minhaLista", "countmeusTudo");
		model.addAttribute("minhaListaAtual", acaoExtensaoService.findAll(pessoa));

		return LISTAR_MINHAS_ACOES;
	}

	@GetMapping("/minhas/aguardando-parecer")
	public String listarMinhasAcoesEmAndamento(Model model, Authentication authentication) {
		Pessoa pessoa = pessoaService.buscarPorCpf(authentication.getName());
		model.addAttribute("countmeusTudo", acaoExtensaoService.countMinhasAcoes(pessoa));
		model.addAttribute("countmeusPareceres", acaoExtensaoService.countMinhasAcoesAguardandoParecer(pessoa));
		model.addAttribute("countmeusPareceresEmitidos", acaoExtensaoService.countMinhasAcoesPareceresEmitidos(pessoa));
		model.addAttribute("minhaLista", "countmeusPareceres");
		model.addAttribute("minhaListaAtual", acaoExtensaoService.findAcoesAguardandoParecer(pessoa));

		return LISTAR_MINHAS_ACOES;
	}

	@GetMapping("/minhas/pareceres-emitidos")
	public String listarMinhasAcoesEncerrada(Model model, Authentication authentication) {
		Pessoa pessoa = pessoaService.buscarPorCpf(authentication.getName());
		model.addAttribute("countmeusTudo", acaoExtensaoService.countMinhasAcoes(pessoa));
		model.addAttribute("countmeusPareceres", acaoExtensaoService.countMinhasAcoesAguardandoParecer(pessoa));
		model.addAttribute("countmeusPareceresEmitidos", acaoExtensaoService.countMinhasAcoesPareceresEmitidos(pessoa));
		model.addAttribute("minhaLista", "countmeusPareceresEmitidos");
		model.addAttribute("minhaListaAtual", acaoExtensaoService.findAcoesParecerEmitido(pessoa));

		return LISTAR_MINHAS_ACOES;
	}

	/**
	 * Busca uma ação específica pelo id
	 * 
	 */
	@GetMapping("/{acao}")
	public String visualizarAcao(@PathVariable AcaoExtensao acao, Model model) {

		model.addAttribute("pendencia", new Pendencia()); // Se tirar essa
															// linha, gera erro.
		model.addAttribute("pareceristas", servidorService.findAllServidores());
		model.addAttribute("acaoExtensao", acao);
		model.addAttribute("participacao", new Participacao());
		model.addAttribute("funcoes", Funcao.values());
		model.addAttribute("instituicoes", Instituicao.values());
		model.addAttribute("servidores", servidorService.findAllServidores());
		model.addAttribute("alunos", alunoService.findAllAlunos());
		model.addAttribute("tipoBolsa", TipoBolsa.values());
		model.addAttribute("bolsa", new Bolsa());
		model.addAttribute("parceriaExterna", new ParceriaExterna());
		model.addAttribute(PARCEIROS, parceiroService.listarParceiros());
		model.addAttribute("tipoParceria", Tipo.values());

		return VISUALIZAR_ACAO;
	}

	/**
	 * Formulário para cadastro de nova ação de extensão
	 */
	@PreAuthorize(PERMISSAO_SERVIDOR)
	@GetMapping("/cadastrar")
	public String cadastrar(Model model) {
		model.addAttribute("acao", new AcaoExtensao());
		model.addAttribute("modalidades", Modalidade.values());
		model.addAttribute("acoesParaVinculo", acaoExtensaoService.findProgramasAprovados());
		model.addAttribute("servidores", servidorService.findAllServidores());
		model.addAttribute("action", "cadastrar");

		return CADASTRAR_ACAO;
	}

	/**
	 * Cadastra uma nova ação de extensão ou atualiza suas informações antes de
	 * ser submetida
	 */
	@PreAuthorize(PERMISSAO_SERVIDOR)
	@PostMapping("/cadastrar")
	public String cadastrar(@RequestParam(value = "anexoAcao") MultipartFile arquivo,
			@RequestParam("cargaHoraria") Integer cargaHoraria,
			@Valid @ModelAttribute("acaoExtensao") AcaoExtensao acaoExtensao, Authentication authentication,
			RedirectAttributes redirect) {
		try {

			Pessoa coordenador = pessoaService.buscarPorCpf(authentication.getName());

			if (acaoExtensao.getCoordenador() != null) {
				acaoExtensaoService.salvarAcaoRetroativa(acaoExtensao, arquivo, cargaHoraria);
			} else {
				acaoExtensaoService.cadastrar(acaoExtensao, arquivo, coordenador);
				participacaoService.participacaoCoordenador(acaoExtensao, cargaHoraria);
			}

		} catch (GpaExtensaoException e) {
			redirect.addFlashAttribute(ERRO, e.getMessage());
			return R_ACOES;
		}

		redirect.addFlashAttribute(MESSAGE, MESSAGE_CADASTRO_SUCESSO);
		return REDIRECT_PAGINA_DETALHES_ACAO + acaoExtensao.getId();
	}

	/**
	 * Formulário para editar uma ação de extensão
	 */
	@PreAuthorize(PERMISSAO_SERVIDOR)
	@GetMapping({"/editar/{id}", "/resolver-pendencia/{id}"})
	public String editar(@PathVariable("id") AcaoExtensao acaoExtensao, Model model, Authentication authentication, HttpServletRequest request) {
		// Verifica se é possível editar a ação
		// Só é possível editar quando o usuário for o coordenador e o status
		// for NOVO ou estiver resolvendo alguma pendência
		if (acaoExtensao == null
				|| !(acaoExtensao.getStatus().equals(Status.NOVO)
						|| acaoExtensao.getStatus().equals(Status.RESOLVENDO_PENDENCIAS_PARECER)
						|| acaoExtensao.getStatus().equals(Status.RESOLVENDO_PENDENCIAS_RELATO))
				|| !acaoExtensao.getCoordenador().getCpf().equals(authentication.getName())) {
			return R_INDEX;
		}
		
		if(request.getRequestURI().contains("editar")) {
			model.addAttribute("action", "editar");
		} else if(request.getRequestURI().contains("pendencia")) {
			model.addAttribute("action", "pendencia");
		} 
		
		model.addAttribute("acao", acaoExtensao);
		model.addAttribute("modalidades", Modalidade.values());
		model.addAttribute("acoesParaVinculo", acaoExtensaoService.findProgramasAprovados());
		model.addAttribute("cargaHoraria", 4);
		model.addAttribute("pendencia", new Pendencia());

		return CADASTRAR_ACAO;
	}

	@PostMapping({"/editar", "/pendencia"})
	public String editarAcao(@Valid @ModelAttribute("acaoExtensao") AcaoExtensao acaoExtensao,
			@RequestParam(value = "anexoAcao", required = false) MultipartFile arquivo, Model model,
			RedirectAttributes redirect, HttpServletRequest request) {

		if (!acaoExtensao.getModalidade().equals(Modalidade.CURSO)
				&& !acaoExtensao.getModalidade().equals(Modalidade.EVENTO)) {
			acaoExtensao.setHorasPraticas(null);
			acaoExtensao.setHorasTeoricas(null);
			acaoExtensao.setProgramacao("");
		}
		
		boolean pendencia = false;
		
		if(request.getRequestURI().contains("pendencia")) {
			pendencia = true;
		}
		
		try {
			acaoExtensaoService.editarAcaoExtensao(acaoExtensao, arquivo, pendencia);
			redirect.addFlashAttribute(MESSAGE, MESSAGE_EDITADO_SUCESSO);
		} catch (GpaExtensaoException e) {
			redirect.addFlashAttribute(ERRO, e.getMessage());
		}

		return REDIRECT_PAGINA_DETALHES_ACAO + acaoExtensao.getId();
	}

	@PostMapping(value = "/deletar/{id}")
	public @ResponseBody Map<String, Object> deletar(@PathVariable("id") Integer id, RedirectAttributes attr, Authentication auth) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			acaoExtensaoService.deletarAcaoExtensao(id, auth.getName());
		} catch (GpaExtensaoException e) {
			map.put(ERRO, e.getMessage());
		}

		return map;
	}

	@PostMapping(value = "/encerrar/{acao}")
	public @ResponseBody Map<String, Object> encerrar(@PathVariable("acao") AcaoExtensao acaoExtensao,
			Authentication authentication) {

		Pessoa pessoa = pessoaService.buscarPorCpf(authentication.getName());
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			acaoExtensaoService.encerrarAcao(acaoExtensao, pessoa);
		} catch (GpaExtensaoException e) {
			map.put(ERRO, e.getMessage());
		}
		return map;
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
		model.addAttribute(PARCEIROS, parceiroService.listarParceiros());

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

	@RequestMapping(value = "/salvarCodigo/{idAcao}", method = RequestMethod.POST)
	public ModelAndView salvarCodigo(@RequestParam("codigoAcao") String codigo, @PathVariable("idAcao") Integer idAcao,
			Model model, RedirectAttributes redirectAttribute) {

		AcaoExtensao acao = acaoExtensaoService.findById(idAcao);
		try {
			acaoExtensaoService.salvarCodigoAcao(acao, codigo);
			redirectAttribute.addFlashAttribute("status", STATUS_MESSAGE_SUCCESS);
			redirectAttribute.addFlashAttribute("titulo", TITULO_MESSAGE_CADASTRAR_CODIGO);
			redirectAttribute.addFlashAttribute("conteudo", CONTEUDO_MESSAGE_CADASTRAR_CODIGO);
		} catch (GpaExtensaoException e) {
			redirectAttribute.addFlashAttribute("status", STATUS_MESSAGE_ERROR);
		}
		
		model.addAttribute("acao", acao);
		
		return new ModelAndView(REDIRECT_PAGINA_DETALHES_ACAO + acao.getId());
	}

	@RequestMapping(value = "/salvarNovoCoordenador/{id}", method = RequestMethod.POST)
	public ModelAndView salvarNovoCoordenador(@PathVariable("id") Integer id,
			@RequestParam("idNovoCoordenador") Integer idNovoCoordenador, @RequestParam("dataInicio") String dataInicio,
			@RequestParam("cargaHoraria") Integer cargaHoraria, RedirectAttributes redirectAttribute,
			Authentication authentication) throws ParseException {

		AcaoExtensao acao = acaoExtensaoService.findById(id);

		try {
			acaoExtensaoService.transeferirCoordenacao(acao, idNovoCoordenador, dataInicio, cargaHoraria);
			redirectAttribute.addFlashAttribute("status", STATUS_MESSAGE_SUCCESS);
			redirectAttribute.addFlashAttribute("titulo", TITULO_MESSAGE_TRANSFERIR_COORDENACAO);
			redirectAttribute.addFlashAttribute("conteudo", CONTEUDO_MESSAGE_TRANSFERIR_COORDENACAO);
		} catch (GpaExtensaoException e) {
			if(e.getMessage().equals(MENSAGEM_DATA_IGUAL_MAIOR)) {
				redirectAttribute.addFlashAttribute("status", STATUS_MESSAGE_ERROR);
				redirectAttribute.addFlashAttribute("titulo", TITULO_MESSAGE_TRANSFERIR_COORDENACAO_ERROR);
				redirectAttribute.addFlashAttribute("conteudo", CONTEUDO_MESSAGE_TRANSFERIR_COORDENACAO_DATA_MAIOR_IGUAL_ERROR);
			} else if(e.getMessage().equals(MENSAGEM_DATA_MENOR)) {
				redirectAttribute.addFlashAttribute("status", STATUS_MESSAGE_ERROR);
				redirectAttribute.addFlashAttribute("titulo", TITULO_MESSAGE_TRANSFERIR_COORDENACAO_ERROR);
				redirectAttribute.addFlashAttribute("conteudo", CONTEUDO_MESSAGE_TRANSFERIR_COORDENACAO_DATA_MENOR_ERROR);
			}
		}
		
		return new ModelAndView(REDIRECT_PAGINA_DETALHES_ACAO + id);
	}

	@GetMapping("/submeter/{idAcao}")
	public ModelAndView submeterAcaoExtensao(@PathVariable("idAcao") AcaoExtensao acao, RedirectAttributes redirectAttribute,
			Authentication auth) {

		Pessoa pessoaLogada = (Pessoa) auth.getPrincipal();

		try {
			acaoExtensaoService.submeterAcaoExtensao(acao, pessoaLogada);
			redirectAttribute.addFlashAttribute("status", STATUS_MESSAGE_SUCCESS);
			redirectAttribute.addFlashAttribute("titulo", TITULO_MESSAGE_ACAO_SUBMETIDA);
			redirectAttribute.addFlashAttribute("conteudo", CONTEUDO_MESSAGE_ACAO_SUBMETIDA);
		} catch (GpaExtensaoException e) {
			redirectAttribute.addFlashAttribute("status", STATUS_MESSAGE_ERROR);
			redirectAttribute.addFlashAttribute("titulo", TITULO_MESSAGE_ACAO_SUBMETIDA_ERROR);
			redirectAttribute.addFlashAttribute("conteudo", CONTEUDO_MESSAGE_ACAO_SUBMETIDA_ERROR);
		}

		return new ModelAndView(REDIRECT_PAGINA_DETALHES_ACAO + acao.getId());
	}

	@RequestMapping("/buscarCoordenadores/{id}")
	public @ResponseBody List<Servidor> buscarCoordenadores(@PathVariable("id") Integer idCoordenadorAtual) {
		return servidorService.buscarServidorNaoCoordenador(idCoordenadorAtual);
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

	@PostMapping("/homologarAcao/{idAcao}")
	public String homologarAcao(@PathVariable("idAcao") Integer idAcao, @RequestParam("resultado") String resultado,
			@RequestParam("dataHomologacao") String dataHomologacao, @RequestParam("observacao") String observacao,
			RedirectAttributes redirectAttribute, Model model) throws GpaExtensaoException, ParseException {

		AcaoExtensao acao = acaoExtensaoService.findById(idAcao);

		try {
			acaoExtensaoService.homologarAcaoExtensao(acao, resultado, dataHomologacao, observacao);
		} catch (GpaExtensaoException e) {
			redirectAttribute.addAttribute(ERRO, e.getMessage());
		}

		return REDIRECT_PAGINA_DETALHES_ACAO + acao.getId();
	}

}
