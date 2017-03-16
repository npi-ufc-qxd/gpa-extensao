package ufc.quixada.npi.gpa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ufc.quixada.npi.gpa.model.Servidor;
import ufc.quixada.npi.gpa.service.AcaoExtensaoService;
import ufc.quixada.npi.gpa.service.ServidorService;

import static ufc.quixada.npi.gpa.util.Constants.PERMISSAO_ADMIN;
import static ufc.quixada.npi.gpa.util.PageConstants.LISTAR_SERVIDORES;
import static ufc.quixada.npi.gpa.util.PageConstants.VISUALIZAR_SERVIDOR;
import static ufc.quixada.npi.gpa.util.RedirectConstants.R_SERVIDORES;

@Controller
@RequestMapping("/servidores")
public class ServidorController {

	@Autowired
	private ServidorService servidorService;

	@Autowired
	private AcaoExtensaoService acaoExtensaoService;

	/**
	 * Busca todos os servidores cadastrados
	 */
	@GetMapping({"", "/"})
	public String listarServidores(Model model) {
		model.addAttribute("servidores", servidorService.findAllServidores());
		return LISTAR_SERVIDORES;
	}

	/**
	 * Busca um servidore específico
	 */
	@GetMapping("{servidor}")
	public String visualizarServidor(@PathVariable Servidor servidor, Model model) {
		model.addAttribute("servidor", servidor);
		model.addAttribute("acoes", acaoExtensaoService.findAcoesByPessoa(servidor.getPessoa()));
		return VISUALIZAR_SERVIDOR;
	}

	/**
	 * Atualiza lista de servidores a partir do LDAP
	 */
	@PreAuthorize(PERMISSAO_ADMIN)
	@GetMapping("atualizar")
	public String atualizarServidores() {
		servidorService.cadastrarServidores();
		return R_SERVIDORES;
	}

}
