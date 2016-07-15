package ufc.quixada.npi.gpa.controller;

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
@RequestMapping("buscar")
@Transactional
public class BuscarController {
	
	@Autowired
	ServidorRepository servidorRespository;
	
	@Autowired
	AcaoExtensaoRepository acaoExtensaoRepository;
	
	@Autowired
	PessoaRepository pessoaRepository;

	@RequestMapping("/acao-extensao")
	public String buscarAcaoForm(Model model) {
		model.addAttribute("coordenadores", servidorRespository.findAll());
		model.addAttribute("acoes", acaoExtensaoRepository.findByStatus(Status.APROVADO));
		model.addAttribute("modalidades", Modalidade.values());
		
		return "buscar/acao-extensao";
	}
	
	@RequestMapping(value="/acao-extensao", method = RequestMethod.POST)
	public String buscarAcao(@RequestParam("coordenador") Integer idCoordenador, @RequestParam("modalidade") Modalidade modalidade,
			@RequestParam("ano") Integer ano, Model model)  {
		
		if(idCoordenador == null && modalidade == null && ano == null) {
			return "redirect:/buscar/acao-extensao";
		}
		
		Pessoa coordenador = null;
		
		if(idCoordenador != null) {
			coordenador = pessoaRepository.findOne(idCoordenador);
		}
		Specification<AcaoExtensao> specification = AcaoExtensaoEspecification.buscar(coordenador, modalidade, ano);
		
		model.addAttribute("acoes", acaoExtensaoRepository.findAll(specification));
		model.addAttribute("coordenadores", servidorRespository.findAll());
		model.addAttribute("modalidades", Modalidade.values());
		
		
		return "buscar/acao-extensao";
	}
}
