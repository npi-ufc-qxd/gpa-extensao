package ufc.quixada.npi.gpa.service.impl;

import java.util.List;

import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;

import ufc.quixada.npi.gpa.model.Papel;
import ufc.quixada.npi.gpa.model.Pessoa;
import ufc.quixada.npi.gpa.repository.PessoaRepository;
import ufc.quixada.npi.gpa.service.PessoaService;

@Named
public class PessoaServiceImpl implements PessoaService {

	@Autowired
	private PessoaRepository pessoaRepository;

	@Override
	public List<Papel> getPapeisByCpf(String cpf) {
		return pessoaRepository.findPapeisByCpf(cpf);
	}

	@Override
	public Pessoa getByCpf(String cpf) {
		return pessoaRepository.getByCpf(cpf);
	}

}
