package ufc.quixada.npi.gpa.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ufc.quixada.npi.gpa.model.AcaoExtensao;
import ufc.quixada.npi.gpa.model.Pessoa;
import ufc.quixada.npi.gpa.service.AcaoExtensaoService;
import ufc.quixada.npi.gpa.service.PessoaService;
import ufc.quixada.npi.gpa.model.AcaoExtensao.Status;
import ufc.quixada.npi.gpa.model.Participacao;
import static ufc.quixada.npi.gpa.util.Constants.MENSAGEM_ACAO_EXTENSAO_INEXISTENTE;
import static ufc.quixada.npi.gpa.util.Constants.REDIRECT_PAGINA_LISTAR_ACAO_EXTENSAO;
import static ufc.quixada.npi.gpa.util.Constants.ACAO_EXTENSAO;
import static ufc.quixada.npi.gpa.util.Constants.PAGINA_DETALHES_ACAO_EXTENSAO;
import static ufc.quixada.npi.gpa.util.Constants.ERRO;
import static ufc.quixada.npi.gpa.util.Constants.MENSAGEM_PERMISSAO_NEGADA;



@Controller
@RequestMapping("detalhe")
public class AcaoExtensaoController {
	

	@Autowired
	private AcaoExtensaoService acaoService;
	@Autowired
	private PessoaService pessoaService;
	
	@RequestMapping(value = "/acao/{id}", method = RequestMethod.GET)
	public String verDetalhes(@PathVariable("id") int id, Model model, HttpSession session,
			RedirectAttributes redirectAttributes, Authentication authentication){
		AcaoExtensao acao = acaoService.getById(id);
		if(acao == null){
			redirectAttributes.addFlashAttribute(ERRO, MENSAGEM_ACAO_EXTENSAO_INEXISTENTE);
			return REDIRECT_PAGINA_LISTAR_ACAO_EXTENSAO;
		}
		
		Pessoa pessoa = pessoaService.getByCpf(authentication.getName());
		
		if(acao.getCoordenador().equals(pessoa)){
			model.addAttribute(ACAO_EXTENSAO,acaoService.getById(id));
			return PAGINA_DETALHES_ACAO_EXTENSAO;	
		}
		
		if (pessoa.isDirecao()) {
			model.addAttribute(ACAO_EXTENSAO, acaoService.getById(id));
			return PAGINA_DETALHES_ACAO_EXTENSAO;
		}
		
		if (acao.getParecerTecnico().getResponsavel().equals(pessoa)
				&& acao.getStatus().equals(Status.AGUARDANDO_PARECER_TECNICO)) {
			model.addAttribute(ACAO_EXTENSAO, acaoService.getById(id));
			return PAGINA_DETALHES_ACAO_EXTENSAO;
		}
		if(acao.getParecerRelator().getResponsavel().equals(pessoa)
				&& acao.getStatus().equals(Status.AGUARDANDO_PARECER_RELATOR)){
				model.addAttribute(ACAO_EXTENSAO, acaoService.getById(id));
				return PAGINA_DETALHES_ACAO_EXTENSAO;
		}
		if (acao.getStatus().equals(Status.APROVADO)) {
			for (Participacao participacao : acao.getEquipeDeTrabalho()) {
				if (pessoa.equals(participacao.getParticipante())) {
					model.addAttribute(ACAO_EXTENSAO, acaoService.getById(id));
					return PAGINA_DETALHES_ACAO_EXTENSAO;
				}
			}
		}
		
		redirectAttributes.addFlashAttribute(ERRO, MENSAGEM_PERMISSAO_NEGADA);
		return REDIRECT_PAGINA_LISTAR_ACAO_EXTENSAO;
		
	}
	
	
}
