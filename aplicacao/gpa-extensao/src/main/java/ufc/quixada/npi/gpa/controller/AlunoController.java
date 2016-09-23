package ufc.quixada.npi.gpa.controller;

import static ufc.quixada.npi.gpa.util.Constants.FRAGMENTS_INFO_ALUNO;
import static ufc.quixada.npi.gpa.util.Constants.MESSAGE;
import static ufc.quixada.npi.gpa.util.Constants.PAGINA_CADASTRO_ALUNO;
import static ufc.quixada.npi.gpa.util.Constants.REDIRECT_PAGINA_DETALHES_BOLSISTA;
import static ufc.quixada.npi.gpa.util.Constants.USUARIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.ufc.quixada.npi.ldap.model.Usuario;
import ufc.quixada.npi.gpa.exception.GpaExtensaoException;
import ufc.quixada.npi.gpa.service.AlunoService;

@Controller
@RequestMapping("/admin/alunos")
public class AlunoController {

	@Autowired
	private AlunoService alunoService;

	@RequestMapping(method = RequestMethod.GET)
	public String paginaCadastroAluno() {
		return PAGINA_CADASTRO_ALUNO;
	}

	@RequestMapping(value = "/busca", method = RequestMethod.GET)
	public String buscarUsuario(String cpf, Model model) {

		try {
			model.addAttribute(USUARIO, alunoService.find(cpf));

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
