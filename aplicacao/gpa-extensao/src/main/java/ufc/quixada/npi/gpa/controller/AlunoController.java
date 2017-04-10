package ufc.quixada.npi.gpa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ufc.quixada.npi.gpa.model.Aluno;
import ufc.quixada.npi.gpa.service.AcaoExtensaoService;
import ufc.quixada.npi.gpa.service.AlunoService;

import static ufc.quixada.npi.gpa.util.Constants.PERMISSAO_ADMIN;
import static ufc.quixada.npi.gpa.util.Constants.PERMISSAO_ADMIN_COORDENADORIA;
import static ufc.quixada.npi.gpa.util.PageConstants.LISTAR_ALUNOS;
import static ufc.quixada.npi.gpa.util.PageConstants.VISUALIZAR_ALUNO;
import static ufc.quixada.npi.gpa.util.RedirectConstants.R_ALUNOS;

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
	 * Busca um aluno específico pelo id
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
		alunoService.cadastrarAlunos();
		return R_ALUNOS;
	}

}
