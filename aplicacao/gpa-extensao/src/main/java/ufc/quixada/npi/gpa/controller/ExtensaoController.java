package ufc.quixada.npi.gpa.controller;


import static ufc.quixada.npi.gpa.util.Constants.ACAO_EXTENSAO;
import static ufc.quixada.npi.gpa.util.Constants.ACAO_EXTENSAO_ID;
import static ufc.quixada.npi.gpa.util.Constants.ACOES_DIRECAO_SIZE;
import static ufc.quixada.npi.gpa.util.Constants.ACOES_HOMOLOGADAS;
import static ufc.quixada.npi.gpa.util.Constants.ACOES_NOVAS;
import static ufc.quixada.npi.gpa.util.Constants.ACOES_PARECER_RELATOR;
import static ufc.quixada.npi.gpa.util.Constants.ACOES_PARECER_TECNICO;
import static ufc.quixada.npi.gpa.util.Constants.ACOES_PARTICIPACAO;
import static ufc.quixada.npi.gpa.util.Constants.ACOES_TRAMITACAO;
import static ufc.quixada.npi.gpa.util.Constants.ALERTA_PARECER;
import static ufc.quixada.npi.gpa.util.Constants.ALERTA_RELATO;
import static ufc.quixada.npi.gpa.util.Constants.A_DEFINIR;
import static ufc.quixada.npi.gpa.util.Constants.ERRO;
import static ufc.quixada.npi.gpa.util.Constants.FRAGMENTS_TABLE_PARTICIPACOES;
import static ufc.quixada.npi.gpa.util.Constants.MENSAGEM_ACAO_EXTENSAO_INEXISTENTE;
import static ufc.quixada.npi.gpa.util.Constants.MESSAGE;
import static ufc.quixada.npi.gpa.util.Constants.MESSAGE_ANEXO;
import static ufc.quixada.npi.gpa.util.Constants.MESSAGE_CADASTRO_SUCESSO;
import static ufc.quixada.npi.gpa.util.Constants.MESSAGE_PARECERISTA_NAO_ATRIBUIDO;
import static ufc.quixada.npi.gpa.util.Constants.MESSAGE_RELATOR_NAO_ATRIBUIDO;
import static ufc.quixada.npi.gpa.util.Constants.MESSAGE_STATUS_RESPONSE;
import static ufc.quixada.npi.gpa.util.Constants.MESSAGE_SUBMISSAO;
import static ufc.quixada.npi.gpa.util.Constants.MODALIDADES;
import static ufc.quixada.npi.gpa.util.Constants.PAGINA_CADASTRAR_ACAO_EXTENSAO;
import static ufc.quixada.npi.gpa.util.Constants.PAGINA_CRIAR_PARCERIA_EXTERNA;
import static ufc.quixada.npi.gpa.util.Constants.PAGINA_DETALHES_ACAO_EXTENSAO;
import static ufc.quixada.npi.gpa.util.Constants.PAGINA_INICIAL;
import static ufc.quixada.npi.gpa.util.Constants.PAGINA_SUBMETER_ACAO_EXTENSAO;
import static ufc.quixada.npi.gpa.util.Constants.PARCEIROS;
import static ufc.quixada.npi.gpa.util.Constants.PARECERISTAS;
import static ufc.quixada.npi.gpa.util.Constants.PENDENCIA;
import static ufc.quixada.npi.gpa.util.Constants.PENDENCIAS;
import static ufc.quixada.npi.gpa.util.Constants.REDIRECT_PAGINA_DETALHES_ACAO;
import static ufc.quixada.npi.gpa.util.Constants.REDIRECT_PAGINA_INICIAL;
import static ufc.quixada.npi.gpa.util.Constants.RESPONSE_DATA;

import java.util.ArrayList;
import java.util.Arrays;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ufc.quixada.npi.gpa.exception.GpaExtensaoException;
import ufc.quixada.npi.gpa.model.AcaoExtensao;
import ufc.quixada.npi.gpa.model.AcaoExtensao.Modalidade;
import ufc.quixada.npi.gpa.model.AcaoExtensao.Status;
import ufc.quixada.npi.gpa.model.Aluno;
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
import ufc.quixada.npi.gpa.repository.AlunoRepository;
import ufc.quixada.npi.gpa.repository.ParceiroRepository;
import ufc.quixada.npi.gpa.repository.ParceriaExternaRepository;
import ufc.quixada.npi.gpa.repository.ParecerRepository;
import ufc.quixada.npi.gpa.repository.ParticipacaoRepository;
import ufc.quixada.npi.gpa.repository.PessoaRepository;
import ufc.quixada.npi.gpa.repository.ServidorRepository;
import ufc.quixada.npi.gpa.service.AcaoExtensaoService;
import ufc.quixada.npi.gpa.service.DirecaoService;
import ufc.quixada.npi.gpa.validator.ParticipacaoValidator;

@Controller
@Transactional
public class ExtensaoController {
	

	@Autowired
	private ParceiroRepository parceiroRepository;
	
	@Autowired
	private ParceriaExternaRepository parceriaExternaRepository;
	
	@Autowired
	private ServidorRepository servirdorRepository;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private ParticipacaoRepository participacaoRepository;
	
	@Autowired
	private AlunoRepository alunoRepository;
	
	@Autowired
	private AcaoExtensaoRepository acaoExtensaoRepository;
	
	@Autowired
	private AcaoExtensaoService acaoExtensaoService;
	
	@Autowired
	private ParticipacaoValidator participacaoValidator;
	
	@Autowired
	private ParecerRepository parecerRepository;

	@Autowired
	private DirecaoService direcaoService;
	
	@ModelAttribute(ACOES_DIRECAO_SIZE)
	public Long acoesDirecaoSize(){
		return acaoExtensaoRepository.count();
	}
	
	@RequestMapping("/")
	public String listagem(Model model, Authentication authentication) {
		
		Pessoa pessoa = pessoaRepository.getByCpf(authentication.getName());
		List<Status> statusTramitacao = Arrays.asList(Status.AGUARDANDO_PARECERISTA, Status.AGUARDANDO_PARECER_TECNICO, Status.AGUARDANDO_RELATOR, 
				Status.AGUARDANDO_PARECER_RELATOR, Status.AGUARDANDO_HOMOLOGACAO, Status.RESOLVENDO_PENDENCIAS_PARECER, Status.RESOLVENDO_PENDENCIAS_RELATO);
		List<Status> statusNovo = Arrays.asList(Status.NOVO);
		List<Status> statusHomologado = Arrays.asList(Status.APROVADO, Status.REPROVADO);

		model.addAttribute(ACOES_TRAMITACAO, acaoExtensaoRepository.findByCoordenadorAndStatusIn(pessoa, statusTramitacao));
		model.addAttribute(ACOES_NOVAS, acaoExtensaoRepository.findByCoordenadorAndStatusIn(pessoa, statusNovo));
		model.addAttribute(ACOES_HOMOLOGADAS, acaoExtensaoRepository.findByCoordenadorAndStatusIn(pessoa, statusHomologado));
		model.addAttribute(ACOES_PARECER_RELATOR, acaoExtensaoRepository.getParecerRelator(pessoa.getId()));
		model.addAttribute(ACOES_PARECER_TECNICO, acaoExtensaoRepository.getParecerTecnico(pessoa.getId()));
		model.addAttribute(ACOES_PARTICIPACAO, acaoExtensaoRepository.getParticipacao(pessoa.getId()));
		
		return PAGINA_INICIAL;
	}
	
	@RequestMapping(value = "/deletar/{id}", method=RequestMethod.GET)
	public String deletar(@PathVariable("id") Integer id){
		AcaoExtensao acao = acaoExtensaoRepository.findOne(id);
		acaoExtensaoRepository.delete(acao);
		return REDIRECT_PAGINA_INICIAL;
	}

	@Transactional(readOnly = true)
	@RequestMapping(value = "/detalhes/{id}", method = RequestMethod.GET)
	public String verDetalhes(@PathVariable("id") Integer id, Model model,
			RedirectAttributes redirectAttributes){
		
		AcaoExtensao acao = acaoExtensaoRepository.findOne(id);
		if(acao == null){
			redirectAttributes.addFlashAttribute(ERRO, MENSAGEM_ACAO_EXTENSAO_INEXISTENTE);
			return REDIRECT_PAGINA_INICIAL;
		}
		
		model.addAttribute("parceiro",new Parceiro());
		model.addAttribute("parceriaExterna",new ParceriaExterna());
		model.addAttribute(PARCEIROS,parceiroRepository.findAll());
		
		model.addAttribute("novaParticipacao", new Participacao());
		model.addAttribute("funcoes", listaDeFuncoes());
		model.addAttribute("instituicoes", Instituicao.values());
		
		model.addAttribute(ACAO_EXTENSAO, acao);

		if(acao.getStatus().equals(Status.AGUARDANDO_PARECERISTA)){
			model.addAttribute(PARECERISTAS, parecerRepository.getPossiveisPareceristas(id));
			model.addAttribute(ALERTA_PARECER, MESSAGE_PARECERISTA_NAO_ATRIBUIDO);
			acao.setParecerTecnico(new Parecer());
			
		} else if(acao.getStatus().equals(Status.AGUARDANDO_RELATOR)){
			model.addAttribute(PARECERISTAS, direcaoService.getPossiveisPareceristas(id));
			model.addAttribute(ALERTA_RELATO, MESSAGE_RELATOR_NAO_ATRIBUIDO);
			acao.setParecerRelator(new Parecer());
			
		} else if(acao.getStatus().equals(Status.AGUARDANDO_PARECER_TECNICO) || acao.getStatus().equals(Status.AGUARDANDO_PARECER_RELATOR)){
			model.addAttribute(PARECERISTAS, parecerRepository.getPossiveisPareceristas(id));
			model.addAttribute(PENDENCIA, new Pendencia());
			
		}else if(acao.getStatus().equals(Status.RESOLVENDO_PENDENCIAS_PARECER)){
			model.addAttribute(PENDENCIAS, acao.getParecerTecnico().getPendencias());
			model.addAttribute("pendente", true);
			
		}else if(acao.getStatus().equals(Status.RESOLVENDO_PENDENCIAS_RELATO)){
			model.addAttribute(PENDENCIAS, acao.getParecerRelator().getPendencias());
			model.addAttribute("pendente", true);
		}

		return PAGINA_DETALHES_ACAO_EXTENSAO;	
	}
	@RequestMapping("/pendencias-resolvidas/{id}")
	public String pendeciasResolvidas(@PathVariable("id") Integer id, Model model, Authentication authentication) {
		AcaoExtensao acao = acaoExtensaoRepository.findOne(id);
		if(acao.getStatus().equals(Status.RESOLVENDO_PENDENCIAS_RELATO)){
			acao.setStatus(Status.AGUARDANDO_PARECER_RELATOR);
			
		}
		if(acao.getStatus().equals(Status.RESOLVENDO_PENDENCIAS_PARECER)){
			acao.setStatus(Status.AGUARDANDO_PARECER_TECNICO);
		}
		acaoExtensaoRepository.save(acao);
		return REDIRECT_PAGINA_DETALHES_ACAO + id;
	}
	
	@RequestMapping(value = "/salvarcodigo/{id}", method=RequestMethod.POST)
	public String salvarCodigo(@RequestParam("codigoAcao") String codigo, @PathVariable("id") Integer id){
		AcaoExtensao acao = acaoExtensaoRepository.findOne(id);
		acao.setCodigo(codigo);
		acaoExtensaoRepository.save(acao);
		return REDIRECT_PAGINA_DETALHES_ACAO + id;
	}
	
	@RequestMapping(value = "/salvarbolsas/{id}", method=RequestMethod.POST)
	public String salvarBolsas(@RequestParam("bolsasRecebidas") Integer numeroBolsas, @PathVariable("id") Integer id){
		AcaoExtensao acao = acaoExtensaoRepository.findOne(id);
		acao.setBolsasRecebidas(numeroBolsas);
		incluirAlunosBolsistas(acao, numeroBolsas);
		acaoExtensaoRepository.save(acao);
		return REDIRECT_PAGINA_DETALHES_ACAO + id;
	}
	
	private void incluirAlunosBolsistas(AcaoExtensao acao, Integer numeroBolsas) {
		List<Participacao> p = participacaoRepository.findByAcaoExtensaoAndFuncao(acao, Funcao.ALUNO_BOLSISTA);
		Integer restantes = numeroBolsas - p.size();
		
		if(restantes > 0) {
			for(int i = 0; i < restantes; i++) {
				Participacao participacao = new Participacao();
				participacao.setAcaoExtensao(acao);
				participacao.setNomeParticipante(A_DEFINIR);
				participacao.setFuncao(Funcao.ALUNO_BOLSISTA);
				participacao.setInstituicao(Instituicao.UFC);
				participacao.setCargaHoraria(12);
				participacaoRepository.save(participacao);
			}
		} 
	}
	
	@RequestMapping(value="/participacoes/{idAcao}", method=RequestMethod.POST)
	public @ResponseBody Map<String, Object> adicionarParticipacao(@Valid @ModelAttribute("participacao") Participacao participacao, @PathVariable("idAcao") Integer idAcao, 
			BindingResult result, Model model, RedirectAttributes redirectAttributes, Authentication authentication) {
		
		AcaoExtensao acao = acaoExtensaoRepository.findOne(idAcao);
		
		participacao.setAcaoExtensao(acao);
		participacaoValidator.validate(participacao, result);
		
		Map<String, Object> map = new HashMap<String, Object>();
		if(result.hasErrors()) {
			map.put(MESSAGE_STATUS_RESPONSE, "ERROR");
			map.put(RESPONSE_DATA, result.getFieldErrors());
			return map;
		}
		
		Pessoa usuario = pessoaRepository.getByCpf(authentication.getName());
		
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
	
	@RequestMapping(value="/excluir/participacao/{id}")
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
		Map<String, Object> map = new HashMap<String, Object>();
		
		participacaoRepository.save(participacao);
		
		map.put(MESSAGE_STATUS_RESPONSE, "OK");
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
	
	private List<Funcao> listaDeFuncoes() {
		List<Funcao> funcoes = new ArrayList<Funcao>();
		for(Funcao funcao: Funcao.values()) {
			if(funcao != Funcao.ALUNO_BOLSISTA) {
				funcoes.add(funcao);
			}
		}
		return funcoes;
	}
	
	@RequestMapping(value="/novaParceria/{id}",method=RequestMethod.GET)
	public String novaParceriaExternaForm(@PathVariable("id") Integer id, Model model){
		model.addAttribute(ACAO_EXTENSAO_ID, id);
		model.addAttribute("parceiro",new Parceiro());
		model.addAttribute("parceriaExterna",new ParceriaExterna());
		model.addAttribute(PARCEIROS,parceiroRepository.findAll());
		return PAGINA_CRIAR_PARCERIA_EXTERNA;
	}

	@RequestMapping("/cadastrar")
	public String cadastrar(Model model, AcaoExtensao acaoExtensao) {
		model.addAttribute(MODALIDADES, Modalidade.values());
		return PAGINA_CADASTRAR_ACAO_EXTENSAO;
	}
	
	@RequestMapping(value="/salvarParceria/{id}", method=RequestMethod.POST)
	public @ResponseBody Map<String, Object> novaParceriaExterna(@PathVariable("id") Integer id, @ModelAttribute @Valid ParceriaExterna parceria,
			Model model, Authentication auth, BindingResult binding){
		Map<String, Object> map = new HashMap<String, Object>();
		if(binding.hasErrors()){
			map.put(MESSAGE_STATUS_RESPONSE, "ERROR");
			map.put(RESPONSE_DATA, binding.getFieldErrors());
			return map;
		}
		AcaoExtensao acaoExtensao = acaoExtensaoRepository.findOne(id);
		parceria.setAcaoExtensao(acaoExtensao);
		parceriaExternaRepository.save(parceria);
		map.put(MESSAGE_STATUS_RESPONSE, "OK");
		map.put(RESPONSE_DATA, MESSAGE_CADASTRO_SUCESSO);
		return map;
	}

	@RequestMapping(value = "/cadastrarAcao", method = RequestMethod.POST)
	public String cadastrar(@RequestParam("anexoAcao") MultipartFile arquivo,@ModelAttribute("acaoExtensao") AcaoExtensao acaoExtensao,
			Authentication authentication, Model model) {
		try {
			acaoExtensaoService.salvarAcaoExtensao(acaoExtensao,arquivo,authentication.getName());
		} catch (GpaExtensaoException e) {
			model.addAttribute(ERRO,e.getMessage());
			return PAGINA_CADASTRAR_ACAO_EXTENSAO;
		}
		model.addAttribute(RESPONSE_DATA, MESSAGE_CADASTRO_SUCESSO);
		return REDIRECT_PAGINA_INICIAL;		
	}
	
	@RequestMapping(value="/excluir/{idAcao}/parceriaExterna/{idParceria}")
	public void deleteParceriaExterna(@PathVariable("idAcao") Integer idAcaoExtensao, @PathVariable("idParceria") Integer idParceriaExterna){
		AcaoExtensao acao = acaoExtensaoRepository.findOne(idAcaoExtensao);
		ParceriaExterna parceria = parceriaExternaRepository.findOne(idParceriaExterna);
		acao.getParceriasExternas().remove(parceria);
		parceriaExternaRepository.delete(idParceriaExterna);
		acaoExtensaoRepository.save(acao);
	}
	
	@RequestMapping(value = "/acoes/{idAcao}/pendencias", method = RequestMethod.POST)
	public String solicitarResolucaoPendenciasParecer (@PathVariable Integer idAcao, Pendencia pendencia) {
		acaoExtensaoService.solicitarResolucaoPendencias(idAcao, pendencia);
		
		return REDIRECT_PAGINA_DETALHES_ACAO + idAcao;
	}

	@RequestMapping(value="/emitirParecerRelator", method=RequestMethod.POST)
	public String emitirParecerRelator(@RequestParam("arquivo-relator") MultipartFile arquivo, AcaoExtensao acaoExtensao, Model model){
		try {
			acaoExtensaoService.emitirParecerRelator(acaoExtensao, arquivo);
		} catch (GpaExtensaoException e) {
			model.addAttribute(ERRO, e.getMessage());
		}
		return REDIRECT_PAGINA_DETALHES_ACAO + acaoExtensao.getId();
	}
	
	@RequestMapping(value="/emitirParecerTecnico", method=RequestMethod.POST)
	public String emitirParecerTecnico(@RequestParam("arquivo-parecerista") MultipartFile arquivo, AcaoExtensao acaoExtensao, Model model){
		try {
			acaoExtensaoService.emitirParecerTecnico(acaoExtensao, arquivo);
		} catch (GpaExtensaoException e) {
			model.addAttribute(ERRO, e.getMessage());
		}
		return REDIRECT_PAGINA_DETALHES_ACAO + acaoExtensao.getId();
	}
	
	@RequestMapping("/submeter/{idAcao}")
	public String submeterForm(@PathVariable("idAcao") Integer idAcao, Model model){
		model.addAttribute(ACAO_EXTENSAO, acaoExtensaoRepository.findOne(idAcao));
		model.addAttribute(MODALIDADES, Modalidade.values());
		return PAGINA_SUBMETER_ACAO_EXTENSAO;
	}
	@RequestMapping(value="/submeter/{idAcao}",method=RequestMethod.POST)
	public String submeterAcaoExtensao(@PathVariable("idAcao") Integer idAcao, @RequestParam("anexoAcao") MultipartFile arquivo,
			@Valid @ModelAttribute AcaoExtensao acao, Model model,BindingResult bind,
			RedirectAttributes redirectAttribute){
		
		if(bind.hasErrors() || arquivo==null || arquivo.isEmpty()){
			model.addAttribute("message",MESSAGE_ANEXO);
			return PAGINA_SUBMETER_ACAO_EXTENSAO;
		}
		
		acaoExtensaoService.submeterAcaoExtensao(idAcao, acao, arquivo);
		
		redirectAttribute.addFlashAttribute(MESSAGE, MESSAGE_SUBMISSAO);
		return REDIRECT_PAGINA_INICIAL;
	}
}
