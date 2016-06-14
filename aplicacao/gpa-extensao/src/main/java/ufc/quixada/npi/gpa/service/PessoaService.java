package ufc.quixada.npi.gpa.service;

import java.util.List;

import ufc.quixada.npi.gpa.model.Papel;
import ufc.quixada.npi.gpa.model.Pessoa;

public interface PessoaService {
	
	List<Papel> getPapeisByCpf(String cpf);
	
	Pessoa getByCpf(String cpf);
}