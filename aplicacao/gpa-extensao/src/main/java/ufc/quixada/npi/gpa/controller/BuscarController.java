package ufc.quixada.npi.gpa.controller;

import static ufc.quixada.npi.gpa.util.Constants.PAGINA_BUSCAR_ACAO_EXTENSAO;
import static ufc.quixada.npi.gpa.util.Constants.PAGINA_DETALHES_SERVIDOR;
import static ufc.quixada.npi.gpa.util.Constants.REDIRECT_PAGINA_BUSCAR_ACAO_EXTENSAO;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
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
@RequestMapping("/buscar")
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

	@RequestMapping("/acao-extensao")
	public String buscarAcaoForm(Model model) {
		model.addAttribute("coordenadores", servidorRespository.findAll());
		model.addAttribute("acoes", acaoExtensaoRepository.findByStatusInOrderByInicioDesc(Arrays.asList(Status.APROVADO)));
		model.addAttribute("modalidades", Modalidade.values());
		model.addAttribute("cursos", Curso.values());

		return PAGINA_BUSCAR_ACAO_EXTENSAO;
	}

	@RequestMapping(value = "/acao-extensao", params = {"servidor", "modalidade", "estado", "ano"}, method = RequestMethod.GET)
	public String buscarAcao(@RequestParam("servidor") Integer idServidor,
			@RequestParam("modalidade") Modalidade modalidade, @RequestParam("estado") String estado,
			@RequestParam("ano") Integer ano, Model model, RedirectAttributes attr) {

		if (idServidor == null && modalidade == null && ano == null && estado.isEmpty()) {
			return REDIRECT_PAGINA_BUSCAR_ACAO_EXTENSAO;
		}

		Pessoa servidor = null;

		if (idServidor != null) {
			servidor = pessoaRepository.findOne(idServidor);
		}

		if (!estado.isEmpty()) {
			if ("true".equals(estado)) {
				model.addAttribute("estado", "Ativo");
			} else if ("false".equals(estado)) {
				model.addAttribute("estado", "Inativo");
			}
		}

		Specification<AcaoExtensao> specification = AcaoExtensaoEspecification.buscar(servidor, modalidade, estado,
				ano);

		model.addAttribute("acoes", acaoExtensaoRepository.findAll(specification));
		model.addAttribute("coordenadores", servidorRespository.findAll());
		model.addAttribute("modalidades", Modalidade.values());
		model.addAttribute("cursos", Curso.values());

		model.addAttribute("servidor", servidor);
		model.addAttribute("modalidade", modalidade);
		model.addAttribute("ano", ano);

		return PAGINA_BUSCAR_ACAO_EXTENSAO;
	}

	@RequestMapping(value = "/acao-extensao", params = { "curso", "ano"}, method = RequestMethod.GET)
	public String buscarAcaoCurso(@RequestParam("curso") Curso curso, @RequestParam("ano") Integer ano, Model model,
			RedirectAttributes attr) {

		if (curso == null && ano == null) {
			return REDIRECT_PAGINA_BUSCAR_ACAO_EXTENSAO;
		}

		Specification<AcaoExtensao> specification = AcaoExtensaoEspecification.buscarCurso(ano, curso.getDescricao());

		model.addAttribute("acoes", acaoExtensaoRepository.findAll(specification));
		model.addAttribute("coordenadores", servidorRespository.findAll());
		model.addAttribute("modalidades", Modalidade.values());
		model.addAttribute("cursos", Curso.values());

		model.addAttribute("ano", ano);
		model.addAttribute("curso", curso.getDescricao());

		return PAGINA_BUSCAR_ACAO_EXTENSAO;
	}

	@RequestMapping("/servidor")
	public String detalhesServidor(Model model) {

		model.addAttribute("servidores", servidorRespository.findAll());
		return PAGINA_DETALHES_SERVIDOR;
	}

	@RequestMapping(value = "/servidor", params = { "servidor" })
	public String detalhesServidor(@RequestParam("servidor") Servidor servidor, Model model) {

		model.addAttribute("servidores", servidorRespository.findAll());
		model.addAttribute("servidor", servidor);
		model.addAttribute("participacoes",
				participacaoRepository.findByParticipanteAndAcaoExtensao_status(servidor.getPessoa(), Status.APROVADO));

		return PAGINA_DETALHES_SERVIDOR;
	}

	@RequestMapping("/servidor/{id}")
	public String detalhesServidorPessoa(@PathVariable("id") Integer id, Model model) {
		Servidor servidor = servidorRespository.findByPessoa_id(id);
		return detalhesServidor(servidor, model);
	}

}
