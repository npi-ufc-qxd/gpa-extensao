package ufc.quixada.npi.gpa.controller;


import static ufc.quixada.npi.gpa.util.Constants.ACAO_EXTENSAO;
import static ufc.quixada.npi.gpa.util.Constants.ACAO_EXTENSAO_ID;
import static ufc.quixada.npi.gpa.util.Constants.ACOES_COORDENADOR_SIZE;
import static ufc.quixada.npi.gpa.util.Constants.ACOES_DIRECAO_SIZE;
import static ufc.quixada.npi.gpa.util.Constants.ACOES_HOMOLOGADAS;
import static ufc.quixada.npi.gpa.util.Constants.ACOES_NOVAS;
import static ufc.quixada.npi.gpa.util.Constants.ACOES_PARECER_RELATOR;
import static ufc.quixada.npi.gpa.util.Constants.ACOES_PARECER_TECNICO;
import static ufc.quixada.npi.gpa.util.Constants.ACOES_PARTICIPACAO;
import static ufc.quixada.npi.gpa.util.Constants.ACOES_TRAMITACAO;
import static ufc.quixada.npi.gpa.util.Constants.ACOES_VINCULO;
import static ufc.quixada.npi.gpa.util.Constants.ACTION;
import static ufc.quixada.npi.gpa.util.Constants.ALERTA_PARECER;
import static ufc.quixada.npi.gpa.util.Constants.ALERTA_RELATO;
import static ufc.quixada.npi.gpa.util.Constants.DEDICACAO;
import static ufc.quixada.npi.gpa.util.Constants.ERRO;
import static ufc.quixada.npi.gpa.util.Constants.FUNCOES;
import static ufc.quixada.npi.gpa.util.Constants.INSTITUICOES;
import static ufc.quixada.npi.gpa.util.Constants.MENSAGEM_ACAO_EXTENSAO_INEXISTENTE;
import static ufc.quixada.npi.gpa.util.Constants.MESSAGE;
import static ufc.quixada.npi.gpa.util.Constants.MESSAGE_ANEXO;
import static ufc.quixada.npi.gpa.util.Constants.MESSAGE_CADASTRO_SUCESSO;
import static ufc.quixada.npi.gpa.util.Constants.MESSAGE_EDITADO_SUCESSO;
import static ufc.quixada.npi.gpa.util.Constants.MESSAGE_PARECERISTA_NAO_ATRIBUIDO;
import static ufc.quixada.npi.gpa.util.Constants.MESSAGE_RELATOR_NAO_ATRIBUIDO;
import static ufc.quixada.npi.gpa.util.Constants.MESSAGE_STATUS_RESPONSE;
import static ufc.quixada.npi.gpa.util.Constants.MESSAGE_SUBMISSAO;
import static ufc.quixada.npi.gpa.util.Constants.MODALIDADES;
import static ufc.quixada.npi.gpa.util.Constants.NOVA_BOLSA;
import static ufc.quixada.npi.gpa.util.Constants.NOVA_PARTICIPACAO;
import static ufc.quixada.npi.gpa.util.Constants.PAGINA_DETALHES_ACAO_EXTENSAO;
import static ufc.quixada.npi.gpa.util.Constants.PAGINA_INICIAL;
import static ufc.quixada.npi.gpa.util.Constants.PAGINA_SUBMETER_ACAO_EXTENSAO;
import static ufc.quixada.npi.gpa.util.Constants.PARCEIRO;
import static ufc.quixada.npi.gpa.util.Constants.PARCEIROS;
import static ufc.quixada.npi.gpa.util.Constants.PARCERIA_EXTERNA;
import static ufc.quixada.npi.gpa.util.Constants.PARECERISTAS;
import static ufc.quixada.npi.gpa.util.Constants.PENDENCIA;
import static ufc.quixada.npi.gpa.util.Constants.PENDENCIAS;
import static ufc.quixada.npi.gpa.util.Constants.PENDENTE;
import static ufc.quixada.npi.gpa.util.Constants.PESSOA_LOGADA;
import static ufc.quixada.npi.gpa.util.Constants.REDIRECT_PAGINA_DETALHES_ACAO;
import static ufc.quixada.npi.gpa.util.Constants.REDIRECT_PAGINA_INICIAL_ADMINISTRACAO;
import static ufc.quixada.npi.gpa.util.Constants.REDIRECT_PAGINA_INICIAL_COORDENACAO;
import static ufc.quixada.npi.gpa.util.Constants.REDIRECT_PAGINA_INICIAL_DIRECAO;
import static ufc.quixada.npi.gpa.util.Constants.RESPONSE_DATA;
import static ufc.quixada.npi.gpa.util.Constants.SUBMETER;
import static ufc.quixada.npi.gpa.util.Constants.SUCESSO;
import static ufc.quixada.npi.gpa.util.Constants.TIPOS;
import static ufc.quixada.npi.gpa.util.Constants.VALOR_INVALIDO;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ufc.quixada.npi.gpa.exception.GpaExtensaoException;
import ufc.quixada.npi.gpa.model.AcaoExtensao;
import ufc.quixada.npi.gpa.model.AcaoExtensao.Modalidade;
import ufc.quixada.npi.gpa.model.AcaoExtensao.Status;
import ufc.quixada.npi.gpa.model.Bolsa;
import ufc.quixada.npi.gpa.model.Bolsa.TipoBolsa;
import ufc.quixada.npi.gpa.model.Papel;
import ufc.quixada.npi.gpa.model.Papel.Tipo;
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

@Controller
@Transactional
public class ExtensaoController {
	

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
	private AcaoExtensaoService acaoExtensaoService;
	
	@Autowired
	private ParecerRepository parecerRepository;

	@Autowired
	private DirecaoService direcaoService;
	
	@Autowired
	private ParticipacaoService participacaoService;
	
	@ModelAttribute(ACOES_DIRECAO_SIZE)
	public Integer acoesDirecaoSize(Authentication authentication){
		return acaoExtensaoRepository.countAcoesTramitacao(Status.NOVO);
	}
	
	@ModelAttribute(ACOES_COORDENADOR_SIZE)
	public Integer acoesCoordenadorSize(Authentication authentication){
		return acaoExtensaoRepository.countAcoesCoordenador(authentication.getName());
	}
	
	@ModelAttribute(PESSOA_LOGADA)
	public String pessoaLogada(Authentication authentication){
		return pessoaRepository.findByCpf(authentication.getName()).getNome();
	}
	
	@RequestMapping(value = "/")
	public String index(Authentication authentication){
		Collection<Papel> papeis = (Collection<Papel>) authentication.getAuthorities();
		
		List<Tipo> tipos = new ArrayList<Tipo>();
		for(Papel p : papeis){
			tipos.add(p.getNome());
		}
		
		if(tipos.contains(Tipo.ADMINISTRACAO)){
			return REDIRECT_PAGINA_INICIAL_ADMINISTRACAO;
			
		}else if(tipos.contains(Tipo.DIRECAO)){
			return REDIRECT_PAGINA_INICIAL_DIRECAO;
			
		}else if(tipos.contains(Tipo.SERVIDOR)){
			return REDIRECT_PAGINA_INICIAL_COORDENACAO;
		}
		
		return null;
	}
	
	@RequestMapping("/listagem")
	public String listagem(Model model, Authentication authentication) {
		Pessoa pessoa = pessoaRepository.findByCpf(authentication.getName());
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
	public String deletar(@PathVariable("id") Integer id, RedirectAttributes attr, Authentication auth){
		try {
			acaoExtensaoService.deletarAcaoExtensao(id, auth.getName());
		} catch (GpaExtensaoException e) {
			attr.addFlashAttribute(ERRO, e.getMessage());
		}
		
		return REDIRECT_PAGINA_INICIAL_COORDENACAO;
	}
	
	@Transactional(readOnly = true)
	@RequestMapping(value = "/detalhes/{id}", method = RequestMethod.GET)
	public String verDetalhes(@PathVariable("id") Integer id, Model model,
			RedirectAttributes redirectAttributes){
		
		AcaoExtensao acao = acaoExtensaoRepository.findOne(id);
			
		if(acao == null){
			redirectAttributes.addFlashAttribute(ERRO, MENSAGEM_ACAO_EXTENSAO_INEXISTENTE);
			return REDIRECT_PAGINA_INICIAL_COORDENACAO;
		}

		model.addAttribute(PARCEIRO, new Parceiro());
		model.addAttribute(PARCERIA_EXTERNA, new ParceriaExterna());
		model.addAttribute(PARCEIROS, parceiroRepository.findAll());
		
		model.addAttribute(NOVA_PARTICIPACAO, new Participacao());
		model.addAttribute(FUNCOES, Funcao.values());
		model.addAttribute(NOVA_BOLSA, new Bolsa());
		model.addAttribute(TIPOS, TipoBolsa.values());
		model.addAttribute(INSTITUICOES, Instituicao.values());
		
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
			model.addAttribute(PENDENTE, true);
			
		}else if(acao.getStatus().equals(Status.RESOLVENDO_PENDENCIAS_RELATO)){
			model.addAttribute(PENDENCIAS, acao.getParecerRelator().getPendencias());
			model.addAttribute(PENDENTE, true);
		}

		return PAGINA_DETALHES_ACAO_EXTENSAO;	
	}
	
	@RequestMapping(value = "/salvarCodigo/{idAcao}", method=RequestMethod.GET)
	public @ResponseBody Map<String, Object> salvarCodigo(@RequestParam("codigoAcao") String codigo, @PathVariable("idAcao") Integer idAcao){
		Map<String, Object> map = new HashMap<String, Object>();
		if(codigo.isEmpty() || ("").equals(codigo)){
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
	
	@RequestMapping(value = "/salvarNovoCoordenador/{id}", method=RequestMethod.POST)
	public String salvarNovoCoordenador(@PathVariable("id") Integer id, @RequestParam("idNovoCoordenador") Integer idNovoCoordenador, 
			@RequestParam("dataInicio") String dataInicio, @RequestParam("chNovoCoordenador") Integer cargaHoraria,
			RedirectAttributes redirectAttributes, Authentication authentication) throws ParseException{
		
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		Date dataI = df.parse(dataInicio);
		
		AcaoExtensao acao = acaoExtensaoRepository.findOne(id);
		
		if(acao == null) {
			redirectAttributes.addFlashAttribute(ERRO, MENSAGEM_ACAO_EXTENSAO_INEXISTENTE);
			return REDIRECT_PAGINA_INICIAL_COORDENACAO;
		}
		
		Pessoa velhoCoordenador = acao.getCoordenador();
		
		List<Participacao> pVelhoCoordenador = participacaoRepository.findByAcaoExtensaoAndParticipante(acao, velhoCoordenador);
		
		if(pVelhoCoordenador != null) {
			for(Participacao p : pVelhoCoordenador) {
				if(p.isCoordenador()) {
					p.setDataTermino(dataI);
					p.setCoordenador(false);
					participacaoRepository.save(p);
				}
			}
		}
		
		Pessoa novoCoordenador = pessoaRepository.findOne(idNovoCoordenador);
		
		Participacao pVelhaNovoCoordenador = participacaoRepository.findByParticipanteAndAcaoExtensao(novoCoordenador, acao);
		
		if(pVelhaNovoCoordenador != null) {
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

	@RequestMapping("/cadastrar")
	public String cadastrar(Model model, AcaoExtensao acaoExtensao, Authentication authentication) {
		Servidor servidor = servirdorRepository.findByPessoa_cpf(authentication.getName());
		model.addAttribute(DEDICACAO, servidor.getDedicacao());
		model.addAttribute(MODALIDADES, Modalidade.values());
		model.addAttribute(ACOES_VINCULO, acaoExtensaoRepository.findByModalidadeAndStatus(Modalidade.PROGRAMA, Status.APROVADO));
		model.addAttribute(ACTION, "cadastrar");
		
		return PAGINA_SUBMETER_ACAO_EXTENSAO;
	}

	@RequestMapping(value = "/cadastrar", method = RequestMethod.POST)
	public String cadastrar(@RequestParam(value="anexoAcao", required = false) MultipartFile arquivo,
			@RequestParam("cargaHoraria") Integer cargaHoraria, @Valid @ModelAttribute("acaoExtensao") AcaoExtensao acaoExtensao,
			Authentication authentication, Model model, RedirectAttributes redirect) {
		Pessoa coordenador = pessoaRepository.findByCpf(authentication.getName());
		
		try {
			acaoExtensao.setCoordenador(coordenador);
			acaoExtensao.setAtivo(true);
			acaoExtensaoService.salvarAcaoExtensao(acaoExtensao,arquivo);
			participacaoService.participacaoCoordenador(acaoExtensao, cargaHoraria);
		} catch (GpaExtensaoException e) {
			model.addAttribute(ERRO, e.getMessage());
			return PAGINA_SUBMETER_ACAO_EXTENSAO;
		}
		
		redirect.addFlashAttribute(MESSAGE, MESSAGE_CADASTRO_SUCESSO);
		return REDIRECT_PAGINA_DETALHES_ACAO + acaoExtensao.getId();		

	}
	
	@RequestMapping("/submeter/{idAcao}")
	public String submeterForm(@PathVariable("idAcao") Integer idAcao, Model model){
		model.addAttribute(ACTION, SUBMETER);
		model.addAttribute(ACAO_EXTENSAO, acaoExtensaoRepository.findOne(idAcao));
		model.addAttribute(MODALIDADES, Modalidade.values());
		model.addAttribute(ACAO_EXTENSAO_ID, idAcao);
		return PAGINA_SUBMETER_ACAO_EXTENSAO;
	}
	
	@RequestMapping(value="/submeter",method=RequestMethod.POST)
	public String submeterAcaoExtensao(@RequestParam(value="anexoAcao", required = false) MultipartFile arquivo,
			@Valid @ModelAttribute AcaoExtensao acao, Model model,BindingResult bind,
			RedirectAttributes redirectAttribute){
		
		if(bind.hasErrors() || (acao.getAnexo() == null && arquivo.isEmpty())){
				model.addAttribute(MESSAGE,MESSAGE_ANEXO);
				model.addAttribute(MODALIDADES, Modalidade.values());
				model.addAttribute(ACAO_EXTENSAO_ID, acao.getId());
				model.addAttribute(ACTION, SUBMETER);
				return PAGINA_SUBMETER_ACAO_EXTENSAO;
		}
		
		try {
			acaoExtensaoService.submeterAcaoExtensao(acao, arquivo);
		} catch (GpaExtensaoException e) {
			model.addAttribute(ERRO, e.getMessage());
			return REDIRECT_PAGINA_DETALHES_ACAO + acao.getId();
		}
		
		redirectAttribute.addFlashAttribute(MESSAGE, MESSAGE_SUBMISSAO);
		return REDIRECT_PAGINA_DETALHES_ACAO + acao.getId();
	}
	
	@RequestMapping("/editar/{id}")
	public String editar(@PathVariable("id") Integer idAcao, Model model, Authentication authentication) {
		AcaoExtensao acaoExtensao = acaoExtensaoRepository.findOne(idAcao);
		
		if(acaoExtensao == null) {
			return PAGINA_INICIAL;
		}
		
		if(!(acaoExtensao.getStatus().equals(Status.NOVO) || acaoExtensao.getStatus().equals(Status.RESOLVENDO_PENDENCIAS_PARECER)
				|| acaoExtensao.getStatus().equals(Status.RESOLVENDO_PENDENCIAS_RELATO))) {
			return REDIRECT_PAGINA_DETALHES_ACAO + acaoExtensao.getId();
		}
		
		if(!(acaoExtensao.getCoordenador().getCpf().equals(authentication.getName()))) {
			return REDIRECT_PAGINA_DETALHES_ACAO + acaoExtensao.getId();
		}
		
		model.addAttribute(ACTION, "editar");
		model.addAttribute(ACAO_EXTENSAO, acaoExtensao);
		model.addAttribute(MODALIDADES, Modalidade.values());
		model.addAttribute(ACOES_VINCULO, acaoExtensaoRepository.findByModalidadeAndStatus(Modalidade.PROGRAMA, Status.APROVADO));

		return PAGINA_SUBMETER_ACAO_EXTENSAO;
	}
	
	@RequestMapping(value = "/editar", method = RequestMethod.POST)
	public String editarAcao(@Valid @ModelAttribute("acaoExtensao") AcaoExtensao acaoExtensao,
			@RequestParam(value="anexoAcao", required = false) MultipartFile arquivo,
			Authentication authentication, Model model, RedirectAttributes redirect) {
		
		if(!acaoExtensao.getModalidade().equals(Modalidade.CURSO) && !acaoExtensao.getModalidade().equals(Modalidade.EVENTO)) {
			acaoExtensao.setHorasPraticas(null);
			acaoExtensao.setHorasTeoricas(null);
		}
		if(!acaoExtensao.getModalidade().equals(Modalidade.EVENTO)) {
			acaoExtensao.setProgramacao("");
		}
		if(!acaoExtensao.getModalidade().equals(Modalidade.CURSO)) {
			acaoExtensao.setEmenta("");
		}
		
		try {
			acaoExtensaoService.editarAcaoExtensao(acaoExtensao, arquivo);
		} catch (GpaExtensaoException e) {
			model.addAttribute(ERRO, e.getMessage());
			return REDIRECT_PAGINA_DETALHES_ACAO + acaoExtensao.getId();
		}
		
		redirect.addFlashAttribute(MESSAGE, MESSAGE_EDITADO_SUCESSO);
		return REDIRECT_PAGINA_DETALHES_ACAO + acaoExtensao.getId();		

	}
	
	@RequestMapping("/buscarCoordenadores/{id}")
	public @ResponseBody List<Servidor> buscarCoordenadores(@PathVariable("id") Integer idCoordenadorAtual) {
		return servirdorRepository.findByPessoa_idNotIn(idCoordenadorAtual);
	}
}
