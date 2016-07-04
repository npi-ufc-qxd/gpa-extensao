package ufc.quixada.npi.gpa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ufc.quixada.npi.gpa.model.Documento;
import ufc.quixada.npi.gpa.model.DownloadDocumento;
import ufc.quixada.npi.gpa.repository.DocumentoRepository;
import ufc.quixada.npi.gpa.service.impl.DocumentoServiceImpl;

@Controller
@RequestMapping("documento")
public class DocumentoController {
	
	@Autowired
	private DocumentoServiceImpl documentoServiceImpl;
	
	@RequestMapping(value="/download/{id-arquivo}", method = RequestMethod.GET)
	public HttpEntity<?> downloadArquivo(@PathVariable("id-arquivo") Integer idArquivo){
		
		Documento documento = documentoServiceImpl.getDocumento(idArquivo);
		byte[] arquivo = documentoServiceImpl.getArquivo(documento);
		return new DownloadDocumento(arquivo, documento.getNome());
	}
}
