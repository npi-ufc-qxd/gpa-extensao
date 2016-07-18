package ufc.quixada.npi.gpa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ufc.quixada.npi.gpa.repository.AlunoRepository;

@Controller
@RequestMapping("bolsa")
@Transactional
public class BolsaController {
	@Autowired
	private AlunoRepository alunoRepository;
	
	@RequestMapping(value="/detalhes/{id}",method=RequestMethod.GET)
	public String detalhesBolsista(@PathVariable("id") Integer idAluno, Model model){
		model.addAttribute("aluno", alunoRepository.findOne(idAluno));
		return "/detalhes/bolsista/detalhes-bolsista";
	}
}
