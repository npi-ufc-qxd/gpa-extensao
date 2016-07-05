package ufc.quixada.npi.gpa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;

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
import ufc.quixada.npi.gpa.model.DownloadDocumento;
import ufc.quixada.npi.gpa.repository.AcaoExtensaoRepository;
import ufc.quixada.npi.gpa.repository.DocumentoRepository;
import ufc.quixada.npi.gpa.service.impl.DocumentoServiceImpl;

@Controller
@Transactional
@RequestMapping("documento")
public class DocumentoController {
	
	@Autowired
	private DocumentoServiceImpl documentoServiceImpl;
	
	@Autowired
	private AcaoExtensaoRepository acaoExtensaoRepository;
	
	@Autowired
	private DocumentoRepository documentoRepository;
	
	@RequestMapping(value="/download/{id-arquivo}", method = RequestMethod.GET)
	public HttpEntity<?> downloadArquivo(@PathVariable("id-arquivo") Integer idArquivo){
		
		Documento documento = documentoServiceImpl.getDocumento(idArquivo);
		byte[] arquivo = documentoServiceImpl.getArquivo(documento);
		return new DownloadDocumento(arquivo, documento.getNome());
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
