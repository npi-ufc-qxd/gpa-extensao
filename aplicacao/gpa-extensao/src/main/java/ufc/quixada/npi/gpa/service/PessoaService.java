package ufc.quixada.npi.gpa.service;

import ufc.quixada.npi.gpa.model.Pessoa;
import ufc.quixada.npi.gpa.model.Servidor;

public interface PessoaService {

	Servidor findServidor(String cpf);
	
	Pessoa findByCpf(String cpf);
}