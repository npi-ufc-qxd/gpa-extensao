package ufc.quixada.npi.gpa.controller;

import static ufc.quixada.npi.gpa.util.Constants.ACOES;
import static ufc.quixada.npi.gpa.util.Constants.ANO;
import static ufc.quixada.npi.gpa.util.Constants.BUSCAR;
import static ufc.quixada.npi.gpa.util.Constants.COORDENADORES;
import static ufc.quixada.npi.gpa.util.Constants.CURSO;
import static ufc.quixada.npi.gpa.util.Constants.CURSOS;
import static ufc.quixada.npi.gpa.util.Constants.ESTADO;
import static ufc.quixada.npi.gpa.util.Constants.MODALIDADE;
import static ufc.quixada.npi.gpa.util.Constants.MODALIDADES;
import static ufc.quixada.npi.gpa.util.Constants.PAGINA_ACAO_EXTENSAO;
import static ufc.quixada.npi.gpa.util.Constants.PAGINA_BUSCAR_ACAO_EXTENSAO;
import static ufc.quixada.npi.gpa.util.Constants.PAGINA_DETALHES_SERVIDOR;
import static ufc.quixada.npi.gpa.util.Constants.PARTICIPACOES;
import static ufc.quixada.npi.gpa.util.Constants.PESSOA_LOGADA;
import static ufc.quixada.npi.gpa.util.Constants.REDIRECT_PAGINA_BUSCAR_ACAO_EXTENSAO;
import static ufc.quixada.npi.gpa.util.Constants.SERVIDOR;
import static ufc.quixada.npi.gpa.util.Constants.SERVIDORES;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ufc.quixada.npi.gpa.model.AcaoExtensao;
import ufc.quixada.npi.gpa.model.AcaoExtensao.Modalidade;
import ufc.quixada.npi.gpa.model.AcaoExtensao.Status;
import ufc.quixada.npi.gpa.model.Aluno.Curso;
import ufc.quixada.npi.gpa.model.Pessoa;
import ufc.quixada.npi.gpa.model.Servidor;
import ufc.quixada.npi.gpa.repository.AcaoExtensaoRepository;
import ufc.quixada.npi.gpa.repository.ParticipacaoRepository;
import ufc.quixada.npi.gpa.repository.PessoaRepository;
import ufc.quixada.npi.gpa.repository.ServidorRepository;
import ufc.quixada.npi.gpa.specification.AcaoExtensaoEspecification;

@Controller
@RequestMapping(BUSCAR)
@Transactional
public class BuscarController {

	@Autowired
	private ServidorRepository servidorRespository;

	@Autowired
	private AcaoExtensaoRepository acaoExtensaoRepository;

	@Autowired
	private ParticipacaoRepository participacaoRepository;

	@Autowired
	private PessoaRepository pessoaRepository;

	@ModelAttribute(PESSOA_LOGADA)
	public String pessoaLogada(Authentication authentication) {
		return pessoaRepository.findByCpf(authentication.getName()).getNome();
	}

	@RequestMapping(PAGINA_ACAO_EXTENSAO)
	public String buscarAcaoForm(Model model) {
		model.addAttribute(COORDENADORES, servidorRespository.findAll());
		model.addAttribute(ACOES, acaoExtensaoRepository.findByStatusAndAtivoIn(Status.APROVADO, true));
		model.addAttribute(MODALIDADES, Modalidade.values());
		model.addAttribute(CURSOS, Curso.values());

		return PAGINA_BUSCAR_ACAO_EXTENSAO;
	}

	@RequestMapping(value = PAGINA_ACAO_EXTENSAO, params = { SERVIDOR, MODALIDADE, ESTADO,
			ANO }, method = RequestMethod.GET)
	public String buscarAcao(@RequestParam(SERVIDOR) Integer idServidor,
			@RequestParam(MODALIDADE) Modalidade modalidade, @RequestParam(ESTADO) String estado,
			@RequestParam(ANO) Integer ano, Model model, RedirectAttributes attr) {

		if (idServidor == null && modalidade == null && ano == null && estado.isEmpty()) {
			return REDIRECT_PAGINA_BUSCAR_ACAO_EXTENSAO;
		}
		
		Pessoa servidor = null;
		
		if(idServidor != null) {
			servidor = pessoaRepository.findOne(idServidor);
		}
		
		if (!estado.isEmpty()) {
			if ("true".equals(estado)) {
				model.addAttribute(ESTADO, "Ativo");
			} else if ("false".equals(estado)) {
				model.addAttribute(ESTADO, "Inativo");
			}
		}

		Specification<AcaoExtensao> specification = AcaoExtensaoEspecification.buscar(servidor, modalidade, estado, ano);
		
		model.addAttribute(ACOES, acaoExtensaoRepository.findAll(specification));
		model.addAttribute(COORDENADORES, servidorRespository.findAll());
		model.addAttribute(MODALIDADES, Modalidade.values());
		model.addAttribute(CURSOS, Curso.values());
		
		model.addAttribute(SERVIDOR, servidor);
		model.addAttribute(MODALIDADE, modalidade);
		model.addAttribute(ANO, ano);

		return PAGINA_BUSCAR_ACAO_EXTENSAO;
	}

	@RequestMapping(value = PAGINA_ACAO_EXTENSAO, params = { CURSO, ANO }, method = RequestMethod.GET)
	public String buscarAcaoCurso(@RequestParam(CURSO) Curso curso, @RequestParam(ANO) Integer ano, Model model,
			RedirectAttributes attr) {

		if (curso == null && ano == null) {
			return REDIRECT_PAGINA_BUSCAR_ACAO_EXTENSAO;
		}

		Specification<AcaoExtensao> specification = AcaoExtensaoEspecification.buscarCurso(ano, curso.getDescricao());
		
		model.addAttribute(ACOES, acaoExtensaoRepository.findAll(specification));
		model.addAttribute(COORDENADORES, servidorRespository.findAll());
		model.addAttribute(MODALIDADES, Modalidade.values());
		model.addAttribute(CURSOS, Curso.values());
		
		model.addAttribute(ANO, ano);
		model.addAttribute(CURSO, curso.getDescricao());

		return PAGINA_BUSCAR_ACAO_EXTENSAO;
	}

	@RequestMapping("/servidor")
	public String detalhesServidor(Model model) {

		model.addAttribute(SERVIDORES, servidorRespository.findAll());
		return PAGINA_DETALHES_SERVIDOR;
	}

	@RequestMapping(value = "/servidor", params = { "servidor" })
	public String detalhesServidor(@RequestParam("servidor") Servidor servidor, Model model) {

		model.addAttribute(SERVIDORES, servidorRespository.findAll());
		model.addAttribute(SERVIDOR, servidor);
		model.addAttribute(PARTICIPACOES,
				participacaoRepository.findByParticipanteAndAcaoExtensao_status(servidor.getPessoa(), Status.APROVADO));

		return PAGINA_DETALHES_SERVIDOR;
	}

	@RequestMapping("/servidor/{id}")
	public String detalhesServidorPessoa(@PathVariable("id") Integer id, Model model) {
		Servidor servidor = servidorRespository.findByPessoa_id(id);
		return detalhesServidor(servidor, model);
	}

}
