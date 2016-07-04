package ufc.quixada.npi.gpa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ufc.quixada.npi.gpa.model.AcaoExtensao;
import ufc.quixada.npi.gpa.model.Documento;
import ufc.quixada.npi.gpa.repository.AcaoExtensaoRepository;
import ufc.quixada.npi.gpa.repository.DocumentoRepository;


@Controller
@Transactional
@RequestMapping("/documento")
public class DocumentoController {
	
	@Autowired
	private AcaoExtensaoRepository acaoExtensaoRepository;
	
	@Autowired
	private DocumentoRepository documentoRepository;

	@RequestMapping(value = "/download/{id}", method = RequestMethod.GET)
	public HttpEntity<byte[]> getArquivo(@PathVariable("id") Integer idAcao) {

		AcaoExtensao acao = acaoExtensaoRepository.findOne(idAcao);
		Documento documento = acao.getAnexo();
		byte[] arquivo = documento.getArquivo();
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Disposition", "attachment; filename=" + documento.getNome().replace(" ", "_"));
		headers.setContentLength(arquivo.length);

		return new HttpEntity<byte[]>(arquivo, headers);
	}
	
	@RequestMapping(value = "/excluir/{id}", method = RequestMethod.POST)
	@ResponseBody public  ModelMap excluir(@PathVariable("id") Integer idAcao, @ModelAttribute ModelMap model) {
		AcaoExtensao acao = acaoExtensaoRepository.findOne(idAcao);
		
		documentoRepository.delete(acao.getAnexo());

		acao.setAnexo(null);
		acaoExtensaoRepository.save(acao);
		return model;
	}
}
