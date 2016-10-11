package ufc.quixada.npi.gpa.controller;

import static ufc.quixada.npi.gpa.util.Constants.FRAGMENTS_INFO_SERVIDOR;
import static ufc.quixada.npi.gpa.util.Constants.MESSAGE;
import static ufc.quixada.npi.gpa.util.Constants.PAGINA_CADASTRO_SERVIDOR;
import static ufc.quixada.npi.gpa.util.Constants.REDIRECT_PAGINA_CADASTRO_SERVIDORES;
import static ufc.quixada.npi.gpa.util.Constants.REDIRECT_PAGINA_DETALHES_SERVIDOR;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.ufc.quixada.npi.ldap.model.Usuario;
import ufc.quixada.npi.gpa.exception.GpaExtensaoException;
import ufc.quixada.npi.gpa.model.Servidor.Dedicacao;
import ufc.quixada.npi.gpa.service.ServidorService;

@Controller
@RequestMapping("/admin/servidores")
public class ServidorController {

	@Autowired
	private ServidorService servidorService;

	@RequestMapping(method = RequestMethod.GET)
	public String paginaCadastroAluno() {
		return PAGINA_CADASTRO_SERVIDOR;
	}

	@RequestMapping(value = "/busca", method = RequestMethod.GET)
	public String buscarUsuario(String cpf, Model model) {

		try {
			model.addAttribute("usuario", servidorService.find(cpf));

		} catch (GpaExtensaoException e) {
			model.addAttribute(MESSAGE, e.getMessage());
			return FRAGMENTS_INFO_SERVIDOR;
		}

		Collection<Dedicacao> dedicacao = new ArrayList<Dedicacao>();
		dedicacao.add(Dedicacao.EXCLUSIVA);
		dedicacao.add(Dedicacao.H20);
		dedicacao.add(Dedicacao.H40);
		model.addAttribute("dedicacoes", dedicacao);

		return FRAGMENTS_INFO_SERVIDOR;
	}

	@RequestMapping(value = "/adicionar", method = RequestMethod.POST)
	public String adicionarAluno(Usuario usuario, Dedicacao dedicacao, RedirectAttributes redirect) {
		Integer idServidor = null;

		try {
			idServidor = servidorService.adicionar(usuario, dedicacao);

		} catch (GpaExtensaoException e) {
			redirect.addFlashAttribute(MESSAGE, e.getMessage());
			return REDIRECT_PAGINA_CADASTRO_SERVIDORES;
		}

		return REDIRECT_PAGINA_DETALHES_SERVIDOR + idServidor;

	}

}
