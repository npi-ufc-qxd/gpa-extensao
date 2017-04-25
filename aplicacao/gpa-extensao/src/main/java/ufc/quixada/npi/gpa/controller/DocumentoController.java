package ufc.quixada.npi.gpa.controller;

import static ufc.quixada.npi.gpa.util.Constants.ERRO;
import static ufc.quixada.npi.gpa.util.Constants.REDIRECT_PAGINA_DETALHES_ACAO;
import static ufc.quixada.npi.gpa.util.PageConstants.CADASTRAR_ACAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ufc.quixada.npi.gpa.exception.GpaExtensaoException;
import ufc.quixada.npi.gpa.model.AcaoExtensao;
import ufc.quixada.npi.gpa.model.AcaoExtensao.Modalidade;
import ufc.quixada.npi.gpa.model.Documento;
import ufc.quixada.npi.gpa.model.DownloadDocumento;
import ufc.quixada.npi.gpa.service.AcaoExtensaoService;
import ufc.quixada.npi.gpa.service.DocumentoService;

@Controller
@Transactional
@RequestMapping("documento")
public class DocumentoController {
	
	@Autowired
	private DocumentoService documentoService;
	
	@Autowired
	private AcaoExtensaoService acaoExtensaoService;
	
	@RequestMapping(value="/download/{id-arquivo}", method = RequestMethod.GET)
	public HttpEntity<?> downloadArquivo(@PathVariable("id-arquivo") Integer idArquivo){
		
		Documento documento = documentoService.getDocumento(idArquivo);
		byte[] arquivo = null;
		try {
			arquivo = documentoService.getArquivo(documento);
		} catch (GpaExtensaoException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new DownloadDocumento(arquivo, documento.getNome());
	}
	
	@RequestMapping(value = "/excluir/{id}")
	public String deletarArquivo(@PathVariable("id") AcaoExtensao acaoExtensao, Model model,
			RedirectAttributes redirect) {
		AcaoExtensao novaAcao = null;
		try {
			novaAcao = documentoService.deletarDocumento(acaoExtensao);
			acaoExtensaoService.editarAcaoExtensao(novaAcao, null);
			model.addAttribute("acao", novaAcao);
			model.addAttribute("modalidades", Modalidade.values());
			model.addAttribute("acoesParaVinculo", acaoExtensaoService.findProgramasAprovados());
			model.addAttribute("action", "editar");
			model.addAttribute("cargaHoraria", 4);
			return CADASTRAR_ACAO;
		} catch (GpaExtensaoException e) {
			redirect.addFlashAttribute(ERRO, e.getMessage());
			return REDIRECT_PAGINA_DETALHES_ACAO + acaoExtensao.getId();
		}
	}
}
