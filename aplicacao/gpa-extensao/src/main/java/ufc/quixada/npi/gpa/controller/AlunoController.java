package ufc.quixada.npi.gpa.controller;

import static ufc.quixada.npi.gpa.util.Constants.*;
import static ufc.quixada.npi.gpa.util.PageConstants.VISUALIZAR_ALUNO;
import static ufc.quixada.npi.gpa.util.PageConstants.LISTAR_ALUNOS;
import static ufc.quixada.npi.gpa.util.RedirectConstants.R_ALUNOS;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.ufc.quixada.npi.ldap.model.Usuario;
import ufc.quixada.npi.gpa.exception.GpaExtensaoException;
import ufc.quixada.npi.gpa.model.Aluno;
import ufc.quixada.npi.gpa.service.AcaoExtensaoService;
import ufc.quixada.npi.gpa.service.AlunoService;

@Controller
@RequestMapping("/alunos")
public class AlunoController {

	@Autowired
	private AlunoService alunoService;

	@Autowired
	private AcaoExtensaoService acaoExtensaoService;

	/**
	 * Busca todos os alunos cadastrados
	 */
	@GetMapping({"", "/"})
	@PreAuthorize(PERMISSAO_ADMIN_COORDENADORIA)
	public String listarAlunos(Model model) {
		model.addAttribute("alunos", alunoService.findAllAlunos());
		return LISTAR_ALUNOS;
	}

	/**
	 * Busca um aluno específico
	 */
	@GetMapping("{aluno}")
	public String visualizarAluno(@PathVariable Aluno aluno, Model model) {
		model.addAttribute("aluno", aluno);
		model.addAttribute("acoes", acaoExtensaoService.findAcoesByPessoa(aluno.getPessoa()));
		return VISUALIZAR_ALUNO;
	}

	/**
	 * Atualiza lista de alunos a partir do LDAP
	 */
	@PreAuthorize(PERMISSAO_ADMIN)
	@GetMapping("atualizar")
	public String atualizarAlunos() {
		//TODO: buscar os alunos no LDAP e cadastrar no banco de dados os que ainda não tiverem sido cadastrados
		return R_ALUNOS;
	}



	/*@RequestMapping(method = RequestMethod.GET)
	public String paginaCadastroAluno() {
		return PAGINA_CADASTRO_ALUNO;
	}*/

	@RequestMapping(value = "/busca", method = RequestMethod.GET)
	public String buscarUsuario(String cpf, Model model) {

		try {
			model.addAttribute("usuario", alunoService.find(cpf));

		} catch (GpaExtensaoException e) {
			model.addAttribute(MESSAGE, e.getMessage());
			return FRAGMENTS_INFO_ALUNO;
		}

		return FRAGMENTS_INFO_ALUNO;
	}

	@RequestMapping(value = "/adicionar", method = RequestMethod.POST)
	public String adicionarAluno(Usuario usuario, RedirectAttributes redirect) {
		Integer idAluno = null;

		try {
			idAluno = alunoService.adicionar(usuario);

		} catch (GpaExtensaoException e) {
			redirect.addFlashAttribute(MESSAGE, e.getMessage());
			return REDIRECT_PAGINA_DETALHES_BOLSISTA;
		}

		return REDIRECT_PAGINA_DETALHES_BOLSISTA + idAluno;

	}
}
