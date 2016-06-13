package ufc.quixada.npi.gpa.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import ufc.quixada.npi.gpa.model.AcaoExtensao;
import ufc.quixada.npi.gpa.model.AcaoExtensao.Modalidade;
import ufc.quixada.npi.gpa.model.AcaoExtensao.Status;
import ufc.quixada.npi.gpa.model.Documento;
import ufc.quixada.npi.gpa.model.Pessoa;
import ufc.quixada.npi.gpa.repository.AcaoExtensaoRepository;
import ufc.quixada.npi.gpa.repository.DocumentoRepository;
import ufc.quixada.npi.gpa.repository.PessoaRepository;
import ufc.quixada.npi.gpa.service.AcaoExtensaoService;
import ufc.quixada.npi.gpa.service.PessoaService;

@Controller
@RequestMapping("extensao")
public class AcaoExtensaoController {


	@Autowired
	private AcaoExtensaoRepository acaoExtensaoRepository;
	
	@Autowired
	private DocumentoRepository documentoRepository;
	
	@Autowired
	private PessoaService pessoaService;
	
	@Autowired
	private AcaoExtensaoService acaoExtensaoService;
	
	@RequestMapping(value = "/cadastrar", method = RequestMethod.POST)
	public String cadastrar(@RequestParam("anexoAcao") MultipartFile arquivo,@ModelAttribute("acaoExtensao") AcaoExtensao acaoExtensao,
			Authentication authentication, Model model) throws IOException {
		
		if(!(arquivo.getOriginalFilename().toString().equals(""))){
			Documento documento = new Documento();
			documento.setArquivo(arquivo.getBytes());
			documento.setNome(arquivo.getOriginalFilename().toString());
			documentoRepository.save(documento);
			acaoExtensao.setAnexo(documento);
		}
		
		Pessoa coordenador = pessoaService.getByCpf(authentication.getName());
		acaoExtensao.setCoordenador(coordenador);
		acaoExtensao.setBolsasRecebidas(1);
		acaoExtensao.setStatus(Status.NOVO);
		acaoExtensaoRepository.save(acaoExtensao);
		
		String idAcao = acaoExtensaoService.getAcaoExtensao(acaoExtensao).getId().toString();
		idAcao = completeToLeft(idAcao, '0', 4);
		acaoExtensao.setIdentificador("EXT-".concat(idAcao));
		acaoExtensaoRepository.save(acaoExtensao);
		
		
		
		return "redirect:/";		
	}
	
	public static String completeToLeft(String value, char c, int size) {
		String result = value;
		while (result.length() < size) {
			result = c + result;
		}
		return result;
	}
}
