package ufc.quixada.npi.gpa.controller;

import static ufc.quixada.npi.gpa.util.Constants.BUSCAR;
import static ufc.quixada.npi.gpa.util.Constants.ACOES;
import static ufc.quixada.npi.gpa.util.Constants.COORDENADORES;
import static ufc.quixada.npi.gpa.util.Constants.PAGINA_BUSCAR_ACAO_EXTENSAO;
import static ufc.quixada.npi.gpa.util.Constants.PAGINA_ACAO_EXTENSAO;
import static ufc.quixada.npi.gpa.util.Constants.REDIRECT_PAGINA_BUSCAR_ACAO_EXTENSAO;
import static ufc.quixada.npi.gpa.util.Constants.MODALIDADES;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ufc.quixada.npi.gpa.model.AcaoExtensao;
import ufc.quixada.npi.gpa.model.AcaoExtensao.Modalidade;
import ufc.quixada.npi.gpa.model.AcaoExtensao.Status;
import ufc.quixada.npi.gpa.model.Pessoa;
import ufc.quixada.npi.gpa.repository.AcaoExtensaoRepository;
import ufc.quixada.npi.gpa.repository.PessoaRepository;
import ufc.quixada.npi.gpa.repository.ServidorRepository;
import ufc.quixada.npi.gpa.specification.AcaoExtensaoEspecification;

@Controller
@RequestMapping(BUSCAR)
@Transactional
public class BuscarController {
	
	@Autowired
	private ServidorRepository servidorRespository;
	
	@Autowired
	private AcaoExtensaoRepository acaoExtensaoRepository;
	
	@Autowired
	private PessoaRepository pessoaRepository;

	@RequestMapping(PAGINA_ACAO_EXTENSAO)
	public String buscarAcaoForm(Model model) {
		model.addAttribute(COORDENADORES, servidorRespository.findAll());
		model.addAttribute(ACOES, acaoExtensaoRepository.findByStatus(Status.APROVADO));
		model.addAttribute(MODALIDADES, Modalidade.values());
		
		return PAGINA_BUSCAR_ACAO_EXTENSAO;
	}
	
	@RequestMapping(value=PAGINA_ACAO_EXTENSAO, method = RequestMethod.POST)
	public String buscarAcao(@RequestParam("coordenador") Integer idCoordenador, @RequestParam("modalidade") Modalidade modalidade,
			@RequestParam("ano") Integer ano, Model model)  {
		
		if(idCoordenador == null && modalidade == null && ano == null) {
			return REDIRECT_PAGINA_BUSCAR_ACAO_EXTENSAO;
		}
		
		Pessoa coordenador = null;
		
		if(idCoordenador != null) {
			coordenador = pessoaRepository.findOne(idCoordenador);
		}
		Specification<AcaoExtensao> specification = AcaoExtensaoEspecification.buscar(coordenador, modalidade, ano);
		
		model.addAttribute(ACOES, acaoExtensaoRepository.findAll(specification));
		model.addAttribute(COORDENADORES, servidorRespository.findAll());
		model.addAttribute(MODALIDADES, Modalidade.values());
		
		
		return PAGINA_BUSCAR_ACAO_EXTENSAO;
	}
}