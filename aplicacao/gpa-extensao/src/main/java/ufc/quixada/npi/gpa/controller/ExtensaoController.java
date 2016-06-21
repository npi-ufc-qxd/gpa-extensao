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
import static ufc.quixada.npi.gpa.util.Constants.ERRO;
import static ufc.quixada.npi.gpa.util.Constants.MENSAGEM_ACAO_EXTENSAO_INEXISTENTE;
import static ufc.quixada.npi.gpa.util.Constants.MESSAGE_CADASTRO_SUCESSO;
import static ufc.quixada.npi.gpa.util.Constants.MESSAGE_PARECERISTA_NAO_ATRIBUIDO;
import static ufc.quixada.npi.gpa.util.Constants.MESSAGE_RELATOR_NAO_ATRIBUIDO;
import static ufc.quixada.npi.gpa.util.Constants.MESSAGE_STATUS_RESPONSE;
import static ufc.quixada.npi.gpa.util.Constants.PAGINA_ADICIONAR_PARTICIPACAO;
import static ufc.quixada.npi.gpa.util.Constants.PAGINA_CADASTRAR_ACAO_EXTENSAO;
import static ufc.quixada.npi.gpa.util.Constants.PAGINA_CRIAR_PARCERIA_EXTERNA;
import static ufc.quixada.npi.gpa.util.Constants.PAGINA_DETALHES_ACAO_EXTENSAO;
import static ufc.quixada.npi.gpa.util.Constants.PAGINA_INICIAL;
import static ufc.quixada.npi.gpa.util.Constants.PAGINA_LISTAR_ACOES_COORDENACAO;
import static ufc.quixada.npi.gpa.util.Constants.PAGINA_LISTAR_PARTICIPACOES;
import static ufc.quixada.npi.gpa.util.Constants.PARCEIROS;
import static ufc.quixada.npi.gpa.util.Constants.PARECERISTAS;
import static ufc.quixada.npi.gpa.util.Constants.REDIRECT_PAGINA_ADICIONAR_PARTICIPACAO;
import static ufc.quixada.npi.gpa.util.Constants.REDIRECT_PAGINA_DETALHES_ACAO;
import static ufc.quixada.npi.gpa.util.Constants.REDIRECT_PAGINA_LISTAR_ACAO_EXTENSAO;
import static ufc.quixada.npi.gpa.util.Constants.RELATORES;
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
	
	
	@RequestMapping("/")
	public String index() {
		return PAGINA_INICIAL;
	}
	
	@RequestMapping(value = "/deletar/{id}", method=RequestMethod.GET)
	public String deletar(@PathVariable("id") Integer id){
		AcaoExtensao acao = acaoExtensaoRepository.findOne(id);
		acaoExtensaoRepository.delete(acao);
		return REDIRECT_PAGINA_LISTAR_ACAO_EXTENSAO;
	}
	
	@RequestMapping("/coordenacao/listagem")
	public String listagem(Model model, Authentication authentication) {
		
		Pessoa pessoa = pessoaRepository.getByCpf(authentication.getName());
		List<Status> statusTramitacao = Arrays.asList(Status.AGUARDANDO_PARECERISTA, Status.AGUARDANDO_PARECER_TECNICO, Status.AGUARDANDO_RELATOR, Status.AGUARDANDO_PARECER_RELATOR, Status.AGUARDANDO_HOMOLOGACAO, Status.RESOLVENDO_PENDENCIAS_PARECER, Status.RESOLVENDO_PENDENCIAS_RELATO);
		List<Status> statusNovo = Arrays.asList(Status.NOVO);
		List<Status> statusHomologado = Arrays.asList(Status.APROVADO, Status.REPROVADO);

		model.addAttribute(ACOES_TRAMITACAO, acaoExtensaoRepository.findByCoordenadorAndStatusIn(pessoa, statusTramitacao));
		model.addAttribute(ACOES_NOVAS, acaoExtensaoRepository.findByCoordenadorAndStatusIn(pessoa, statusNovo));
		model.addAttribute(ACOES_HOMOLOGADAS, acaoExtensaoRepository.findByCoordenadorAndStatusIn(pessoa, statusHomologado));
		model.addAttribute(ACOES_PARECER_RELATOR, acaoExtensaoRepository.getParecerRelator(pessoa.getId()));
		model.addAttribute(ACOES_PARECER_TECNICO, acaoExtensaoRepository.getParecerTecnico(pessoa.getId()));
		model.addAttribute(ACOES_PARTICIPACAO, acaoExtensaoRepository.getParticipacao(pessoa.getId()));
		
		return PAGINA_LISTAR_ACOES_COORDENACAO;
	}

	@RequestMapping(value = "/detalhes/{id}", method = RequestMethod.GET)
	public String verDetalhes(@PathVariable("id") Integer id, Model model,
			RedirectAttributes redirectAttributes){
		AcaoExtensao acao = acaoExtensaoRepository.findOne(id);
		if(acao == null){
			redirectAttributes.addFlashAttribute(ERRO, MENSAGEM_ACAO_EXTENSAO_INEXISTENTE);
			return REDIRECT_PAGINA_LISTAR_ACAO_EXTENSAO;
		}
		model.addAttribute(ACAO_EXTENSAO_ID, id);
		model.addAttribute("parceiro",new Parceiro());
		model.addAttribute("parceriaExterna",new ParceriaExterna());
		model.addAttribute(PARCEIROS,parceiroRepository.findAll());
		
		if(acao.getStatus().equals(Status.AGUARDANDO_PARECERISTA)){
			model.addAttribute(PARECERISTAS, parecerRepository.getPossiveisPareceristas(id));
			model.addAttribute(ALERTA_PARECER, MESSAGE_PARECERISTA_NAO_ATRIBUIDO);
			acao.setParecerTecnico(new Parecer());
			
		} else if(acao.getStatus().equals(Status.AGUARDANDO_RELATOR)){
			model.addAttribute(RELATORES, direcaoService.getPossiveisPareceristas(id));
			model.addAttribute(ALERTA_RELATO, MESSAGE_RELATOR_NAO_ATRIBUIDO);
			acao.setParecerRelator(new Parecer());
			
		} else if(acao.getStatus().equals(Status.AGUARDANDO_PARECER_TECNICO)){
			model.addAttribute(PARECERISTAS, parecerRepository.getPossiveisPareceristas(id));
			
		} else if(acao.getStatus().equals(Status.AGUARDANDO_PARECER_RELATOR)){
			model.addAttribute(RELATORES, direcaoService.getPossiveisPareceristas(id));
		}
		
		model.addAttribute(ACAO_EXTENSAO, acao);

		return PAGINA_DETALHES_ACAO_EXTENSAO;	
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
		acaoExtensaoRepository.save(acao);
		return REDIRECT_PAGINA_DETALHES_ACAO + id;
	}
	
	@RequestMapping(value="/participacoes/{id}", method=RequestMethod.GET)
	public String formAdicionarParticipacao(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("idAcao", id);
		model.addAttribute("participacao", new Participacao());
		model.addAttribute("funcoes", listaDeFuncoes());
		model.addAttribute("instituicoes", Instituicao.values());
		
		return PAGINA_ADICIONAR_PARTICIPACAO;
	}
	
	@RequestMapping(value="/participacoes/{idAcao}", method=RequestMethod.POST)
	public String adicionarParticipacao(@ModelAttribute("participacao") Participacao participacao, @PathVariable("idAcao") Integer idAcao, 
			BindingResult result, Model model, RedirectAttributes redirectAttributes, Authentication authentication) {
		
		AcaoExtensao acao = acaoExtensaoRepository.findOne(idAcao);
		
		if(acao == null) {
			redirectAttributes.addFlashAttribute("erro", "Projeto inexistente");
			return "/";
		}
		
		participacao.setCpfParticipante(participacao.getCpfParticipante().replaceAll("[^0-9]", ""));
		participacao.setAcaoExtensao(acao);
		participacaoValidator.validate(participacao, result);
		
		if(result.hasErrors()) {
			model.addAttribute("idAcao", idAcao);
			model.addAttribute("participacao", participacao);
			model.addAttribute("funcoes", listaDeFuncoes());
			model.addAttribute("instituicoes", Instituicao.values());
			return PAGINA_ADICIONAR_PARTICIPACAO;
		}
		
		Pessoa usuario = pessoaRepository.getByCpf(authentication.getName());
		
		if(participacao.getParticipante() != null && participacao.getParticipante().getId() == usuario.getId()) {
			participacao.setCoordenador(true);
		}
		
		participacao.setDataInicio(acao.getInicio());
		participacao.setDataTermino(acao.getTermino());
		
		participacaoRepository.save(participacao);
		
		return REDIRECT_PAGINA_ADICIONAR_PARTICIPACAO+idAcao;
	}
	
	@RequestMapping(value="/listar-participacoes/{id}", method=RequestMethod.GET)
	public String listarParticipacoes(@PathVariable("id") Integer id, Model model) {
		AcaoExtensao acao = acaoExtensaoRepository.findOne(id);
		model.addAttribute("participacoes", participacaoRepository.findByAcaoExtensao(acao));
		
		return PAGINA_LISTAR_PARTICIPACOES;
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
		model.addAttribute("modalidades", Modalidade.values());
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
		return PAGINA_INICIAL;		
	}
	
	@RequestMapping(value="/excluir/{idAcao}/parceriaExterna/{idParceria}")
	public void deleteParceriaExterna(@PathVariable("idAcao") Integer idAcaoExtensao, @PathVariable("idParceria") Integer idParceriaExterna){
		AcaoExtensao acao = acaoExtensaoRepository.findOne(idAcaoExtensao);
		ParceriaExterna parceria = parceriaExternaRepository.findOne(idParceriaExterna);
		acao.getParceriasExternas().remove(parceria);
		parceriaExternaRepository.delete(idParceriaExterna);
		acaoExtensaoRepository.save(acao);
	}	
	
	@RequestMapping(value="/emitirParecer", method=RequestMethod.POST)
	public String emitirParecer(@RequestParam("arquivo-relator") MultipartFile arquivo, AcaoExtensao acaoExtensao, Model model){
		try {
			acaoExtensaoService.emitirParecer(acaoExtensao, arquivo);
		} catch (GpaExtensaoException e) {
			model.addAttribute(ERRO, e.getMessage());
		}
		return REDIRECT_PAGINA_DETALHES_ACAO + acaoExtensao.getId();
	}
	
	@ModelAttribute(ACOES_DIRECAO_SIZE)
	public Long acoesDirecaoSize(){
		return acaoExtensaoRepository.count();
	}
}
